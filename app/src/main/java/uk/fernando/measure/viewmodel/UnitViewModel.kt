package uk.fernando.measure.viewmodel

import android.util.Log
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.ext.TAG
import uk.fernando.measure.usecase.GetUnitsUseCase
import uk.fernando.measure.util.Resource


class UnitViewModel(private val useCase: GetUnitsUseCase) : BaseUnitViewModel() {

    override fun fetchUnitsByType(type: UnitType) {
        launchDefault {
            useCase.getUnitsByType(type).collect() { result ->
                when (result) {
                    is Resource.Success -> unitList.value = result.data ?: emptyList()
                    is Resource.Error -> Log.e(TAG, result.message ?: "An unexpected error occured")
                    is Resource.Loading -> loading.value = result.isLoading
                }
            }
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



