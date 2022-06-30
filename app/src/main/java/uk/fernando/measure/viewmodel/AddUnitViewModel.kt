package uk.fernando.measure.viewmodel

import androidx.compose.runtime.mutableStateListOf
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.usecase.AddUnitUseCase


class AddUnitViewModel(private val useCase: AddUnitUseCase) : BaseViewModel() {

    val unitList = mutableStateListOf<LengthUnitEntity>()

    fun fetchAvailableUnits(unitType: Int) {
        launchDefault {
            unitList.addAll(useCase.getAvailableUnitList(unitType))
        }
    }

    fun addUnit(unit: LengthUnitEntity) {
        launchDefault {
            unitList.remove(unit)

            useCase.insertUnit(unit)
        }
    }
}



