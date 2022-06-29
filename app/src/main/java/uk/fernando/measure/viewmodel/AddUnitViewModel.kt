package uk.fernando.measure.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.repository.AddUnitRepository


class AddUnitViewModel(private val rep: AddUnitRepository) : BaseViewModel() {

    val unitList = mutableStateListOf<LengthUnitEntity>()

    init {
        launchDefault {
            unitList.addAll(rep.getLengthUnitList())
        }
    }

    fun addUnit(unit: LengthUnitEntity) {
        launchDefault {
            unitList.remove(unit)

            rep.addUnit(unit)
        }
    }
}



