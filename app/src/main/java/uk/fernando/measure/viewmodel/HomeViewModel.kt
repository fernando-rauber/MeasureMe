package uk.fernando.measure.viewmodel

import androidx.compose.runtime.mutableStateOf
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.repository.UnitRepository
import java.math.RoundingMode
import java.text.DecimalFormat


class HomeViewModel(private val rep: UnitRepository) : BaseViewModel() {

    val lengthUnit = mutableStateOf(emptyList<LengthUnitEntity>())

    init {
        launchDefault {
            rep.getUnitList().collect() { unitList ->
                lengthUnit.value = unitList
            }
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
                unit.amount = roundOffDecimal(unit.multiple * baseUnit)
            }

            rep.updateAll(backupList)

            lengthUnit.value = emptyList()
            lengthUnit.value = backupList
        }
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
}



