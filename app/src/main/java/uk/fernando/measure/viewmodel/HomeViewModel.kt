package uk.fernando.measure.viewmodel

import androidx.compose.runtime.mutableStateOf
import uk.fernando.measure.R
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.ext.roundOffDecimal
import uk.fernando.measure.repository.UnitRepository


class HomeViewModel(private val rep: UnitRepository, private val prefs: PrefsStore) : BaseViewModel() {

    val unitList = mutableStateOf(emptyList<LengthUnitEntity>())

    fun fetchUnitsByType(type: UnitType) {
        launchDefault {
            unitList.value = rep.getUnitList(type)
        }
    }

    fun updateUnit(unit: LengthUnitEntity) {
        val km = unit.amount / unit.multiple
        updateAmount(km)
    }

    private fun updateAmount(baseUnit: Double) {
        launchIO {
            val backupList = unitList.value

            backupList.forEach { unit ->
                unit.amount = (unit.multiple * baseUnit).roundOffDecimal()
            }

            // Store so can use when add a new unit
            storeAmount(backupList.first().type, baseUnit)

            rep.updateAll(backupList)

            unitList.value = emptyList()
            unitList.value = backupList
        }
    }

    fun deleteUnit(unit: LengthUnitEntity) {
        launchIO { rep.deleteUnit(unit) }
    }

    private suspend fun storeAmount(unitType: Int, baseUnit: Double) {
        when (UnitType.getByValue(unitType)) {
            UnitType.LENGTH -> prefs.storeLength(baseUnit)
            UnitType.TEMPERATURE -> prefs.storeLength(baseUnit)
            UnitType.WEIGHT -> prefs.storeWeight(baseUnit)
            else -> prefs.storeVolume(baseUnit)
        }
    }
}



