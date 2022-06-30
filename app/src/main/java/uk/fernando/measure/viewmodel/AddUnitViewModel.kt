package uk.fernando.measure.viewmodel

import androidx.compose.runtime.mutableStateListOf
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.repository.AddUnitRepository


class AddUnitViewModel(private val rep: AddUnitRepository) : BaseViewModel() {

    val unitList = mutableStateListOf<LengthUnitEntity>()

    fun fetchAvailableUnits(unitType: Int) {
        launchDefault {
            unitList.addAll(rep.getUnitList(unitType))
        }
    }

    fun addUnit(unit: LengthUnitEntity) {
        launchDefault {
            unitList.remove(unit)

            rep.insertUnit(unit)
        }
    }
}



