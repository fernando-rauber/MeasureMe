package uk.fernando.measure.usecase

import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.ext.roundOffDecimal
import uk.fernando.measure.repository.UnitRepository

class GetUnitsUseCase(private val repository: UnitRepository, private val prefs: PrefsStore) {

    suspend fun getUnitsByType(type: UnitType): List<LengthUnitEntity> {
        return repository.getUnitList(type)
    }

    suspend fun updateAmount(unit: LengthUnitEntity, unitList: List<LengthUnitEntity>): List<LengthUnitEntity> {
        val baseUnit = unit.amount / unit.multiple

        unitList.forEach { u ->
            u.amount = (u.multiple * baseUnit).roundOffDecimal()
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