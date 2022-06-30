package uk.fernando.measure.viewmodel

import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.usecase.GetUnitsUseCase


class UnitViewModel(private val useCase: GetUnitsUseCase) : BaseUnitViewModel() {

    override fun fetchUnitsByType(type: UnitType) {
        launchDefault {
            unitList.value = useCase.getUnitsByType(type)
        }
    }

    override fun updateUnit(unit: LengthUnitEntity) {
        launchDefault {
            val updatedList = useCase.updateAmount(unit, unitList.value)

            unitList.value = emptyList()
            unitList.value = updatedList
        }
    }

    override fun deleteUnit(unit: LengthUnitEntity) {
        launchIO { useCase.deleteUnit(unit) }
    }
}



