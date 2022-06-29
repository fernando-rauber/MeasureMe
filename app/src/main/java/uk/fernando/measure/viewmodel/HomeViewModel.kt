package uk.fernando.measure.viewmodel

import androidx.compose.runtime.mutableStateOf
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.ext.roundOffDecimal
import uk.fernando.measure.repository.UnitRepository


class HomeViewModel(private val rep: UnitRepository, private val prefs: PrefsStore) : BaseViewModel() {

    val lengthUnit = mutableStateOf(emptyList<LengthUnitEntity>())

    fun fetchLengthUnits(){
        launchDefault {
            lengthUnit.value = rep.getUnitList()
        }
    }

    fun updateUnit(unit: LengthUnitEntity) {
        val km = unit.amount / unit.multiple
        updateAmount(km)
    }

    private fun updateAmount(baseUnit: Double) {
        launchIO {
            val backupList = lengthUnit.value

            backupList.forEach { unit ->
                unit.amount = (unit.multiple * baseUnit).roundOffDecimal()
            }

            // Store so can use when add a new unit
            prefs.storeLength(baseUnit)

            rep.updateAll(backupList)

            lengthUnit.value = emptyList()
            lengthUnit.value = backupList
        }
    }

    fun deleteUnit(unit: LengthUnitEntity) {
        launchIO { rep.deleteUnit(unit) }
    }

}



