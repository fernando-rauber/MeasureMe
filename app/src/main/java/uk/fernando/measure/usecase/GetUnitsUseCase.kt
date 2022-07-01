package uk.fernando.measure.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.enum.UnitMeasure
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.ext.getPatterDecimalFormat
import uk.fernando.measure.ext.roundOffDecimal
import uk.fernando.measure.repository.UnitRepository
import uk.fernando.measure.util.Resource

class GetUnitsUseCase(private val repository: UnitRepository, private val prefs: PrefsStore) {

    suspend fun getUnitsByType(type: UnitType): Flow<Resource<List<LengthUnitEntity>>> = flow {
        try {
            emit(Resource.Loading(true))

            val units = repository.getUnitList(type)

            emit(Resource.Loading(false))
            emit(Resource.Success(units))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            emit(Resource.Loading(false))
        }
    }

    suspend fun updateAmount(unit: LengthUnitEntity, unitList: List<LengthUnitEntity>): List<LengthUnitEntity> {
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
    }

    suspend fun deleteUnit(unit: LengthUnitEntity) {
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