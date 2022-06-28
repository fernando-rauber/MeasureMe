package uk.fernando.measure.viewmodel

import androidx.compose.runtime.mutableStateOf
import uk.fernando.measure.model.MeasureUnitModel


class HomeViewModel : BaseViewModel() {

    val measures = mutableStateOf(emptyList<MeasureUnitModel>())


    init {
        val list = mutableListOf<MeasureUnitModel>()

        list.add(MeasureUnitModel("Kilometer", "km", 1.0, 1, 1.0))
        list.add(MeasureUnitModel("Meter", "m", 1000.0, 2))
        list.add(MeasureUnitModel("Centimeter", "", 100000.0, 3))
        list.add(MeasureUnitModel("Millimeter", "", 10.0, 4))
        list.add(MeasureUnitModel("Micrometer", "", 10.0, 5))
        list.add(MeasureUnitModel("Nanometer", "", 10.0, 6))
        list.add(MeasureUnitModel("Mile", "", 1.609, 7))
        list.add(MeasureUnitModel("Yard", "", 1094.0, 8))
        list.add(MeasureUnitModel("Foot", "", 3281.0, 9))
        list.add(MeasureUnitModel("Inch", "", 39370.0, 10))
        list.add(MeasureUnitModel("Nautical mile", "", 1.852, 11))

        measures.value = list

    }


    fun updateUnit(unit: MeasureUnitModel) {
        measures.value.find { it.name == unit.name }?.apply {
            this.amount = unit.amount
        }

        val km = unit.amount / unit.multiple
        updateAmount(km)
    }

    private fun updateAmount(baseUnit: Double) {

        measures.value.forEach { unit ->
            unit.amount = unit.multiple * baseUnit
        }
    }
}



