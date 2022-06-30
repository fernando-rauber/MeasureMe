package uk.fernando.measure.viewmodel

import android.util.Log
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.enum.UnitMeasure
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.ext.roundOffDecimal
import uk.fernando.measure.repository.UnitRepository


class TemperatureViewModel(private val rep: UnitRepository, private val prefs: PrefsStore) : BaseUnitViewModel() {

    override fun fetchUnitsByType(type: UnitType) {
        launchDefault {
            unitList.value = rep.getUnitList(type)
        }
    }

    override fun updateUnit(unit: LengthUnitEntity) {
        val amount = when (UnitMeasure.getByValue(unit.unit)) {
            UnitMeasure.CELSIUS -> unit.amount
            UnitMeasure.KELVIN -> unit.amount - 273.15
            else -> (unit.amount - 32.0) * 0.55555555
        }

        updateAmount(amount)
    }

    private fun updateAmount(baseUnit: Double) {
        launchIO {
            val backupList = unitList.value

            backupList.forEach { unit ->
                val add = when (UnitMeasure.getByValue(unit.unit)) {
                    UnitMeasure.CELSIUS -> 0.0
                    UnitMeasure.KELVIN -> 273.15
                    else -> 32.0
                }

                unit.amount = ((unit.multiple * baseUnit) + add).roundOffDecimal()
            }

            // Store so can use when add a new unit
            prefs.storeTemperature(baseUnit)

            rep.updateAll(backupList)

            unitList.value = emptyList()
            unitList.value = backupList
        }
    }

    override fun deleteUnit(unit: LengthUnitEntity) {
        launchIO { rep.deleteUnit(unit) }
    }

}



