package uk.fernando.convert.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uk.fernando.convert.database.entity.UnitEntity
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.enum.UnitMeasure
import uk.fernando.convert.enum.UnitType
import uk.fernando.convert.ext.getPatterDecimalFormat
import uk.fernando.convert.ext.roundOffDecimal
import uk.fernando.convert.repository.UnitRepository
import uk.fernando.convert.util.Resource
import uk.fernando.logger.MyLogger
import uk.fernando.util.ext.TAG

class GetUnitsUseCase(
    private val repository: UnitRepository,
    private val prefs: PrefsStore,
    private val logger: MyLogger
) {

    suspend fun getUnitsByType(type: UnitType): Flow<Resource<List<UnitEntity>>> = flow {
        runCatching {
            emit(Resource.Loading(true))

            repository.getUnitList(type)
        }.onSuccess { units ->
            emit(Resource.Loading(false))
            emit(Resource.Success(units))
        }.onFailure { e ->
            emit(Resource.Loading(false))

            logger.e(TAG, e.message.toString())
            logger.addExceptionToCrashlytics(e)
        }
    }

    suspend fun updateAmount(unit: UnitEntity, unitList: List<UnitEntity>): List<UnitEntity> {
        try {
            // Temperature acts different than others units
            val baseUnit = when (UnitMeasure.getByValue(unit.unit)) {
                UnitMeasure.FAHRENHEIT -> (unit.amount - 32.0) * 0.55555555
                UnitMeasure.KELVIN -> unit.amount - 273.15
                UnitMeasure.CELSIUS -> unit.amount
                else -> unit.amount / unit.multiple // any other unit
            }

            unitList.forEach { u ->
                val add = when (UnitMeasure.getByValue(u.unit)) {
                    UnitMeasure.FAHRENHEIT -> 32.0
                    UnitMeasure.KELVIN -> 273.15
                    else -> 0.0
                }
                val newAmount = ((u.multiple * baseUnit) + add)

                u.amount = newAmount.roundOffDecimal(u.type.getPatterDecimalFormat())
            }

            // Store so can use when add a new unit
            storeAmount(unit.type, baseUnit)

            repository.updateAll(unitList)

            return unitList
        } catch (e: Exception) {
            logger.e(TAG, e.message.toString())
            logger.addExceptionToCrashlytics(e)
            return emptyList()
        }
    }

    suspend fun deleteUnit(unit: UnitEntity) {
        repository.deleteUnit(unit)
    }

    private suspend fun storeAmount(unitType: Int, baseUnit: Double) {
        when (UnitType.getByValue(unitType)) {
            UnitType.LENGTH -> prefs.storeLength(baseUnit)
            UnitType.WEIGHT -> prefs.storeWeight(baseUnit)
            UnitType.TEMPERATURE -> prefs.storeTemperature(baseUnit)
            else -> prefs.storeVolume(baseUnit)
        }
    }
}