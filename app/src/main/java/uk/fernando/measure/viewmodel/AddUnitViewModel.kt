package uk.fernando.measure.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.repository.AddUnitRepository


class AddUnitViewModel(private val rep: AddUnitRepository) : BaseViewModel() {

    val unitList = mutableStateListOf<LengthUnitEntity>()

    fun fetchAvailableUnits(unitType: Int) {
        launchDefault {
            unitList.addAll(rep.getLengthUnitList(unitType))
        }
    }

    fun addUnit(unit: LengthUnitEntity) {
        launchDefault {
            unitList.remove(unit)

            rep.addUnitLength(unit)
        }
    }
}



