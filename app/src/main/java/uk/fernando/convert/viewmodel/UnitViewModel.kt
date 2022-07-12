package uk.fernando.convert.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import uk.fernando.convert.database.entity.UnitEntity
import uk.fernando.convert.enum.UnitType
import uk.fernando.convert.ext.TAG
import uk.fernando.convert.usecase.GetUnitsUseCase
import uk.fernando.convert.util.Resource


class UnitViewModel(private val useCase: GetUnitsUseCase) : BaseViewModel() {

    val unitList = mutableStateOf(emptyList<UnitEntity>())
    val loading = mutableStateOf(false)
    private var unitListCount = 0

    fun fetchUnitsByType(type: UnitType) {
        launchDefault {
            useCase.getUnitsByType(type).collect() { result ->
                when (result) {
                    is Resource.Success -> unitList.value = result.data ?: emptyList()
                    is Resource.Error -> Log.e(TAG, result.message ?: "An unexpected error occurred")
                    is Resource.Loading -> loading.value = result.isLoading
                }

                unitListCount = unitList.value.count()
            }
        }
    }

    fun updateUnit(unit: UnitEntity) {
        launchDefault {
            val updatedList = useCase.updateAmount(unit, unitList.value)

            unitList.value = emptyList()
            unitList.value = updatedList
        }
    }

    fun deleteUnit(unit: UnitEntity) {
        launchIO { useCase.deleteUnit(unit) }
        unitListCount--

        if (unitListCount <= 0)
            unitList.value = emptyList()
    }
}



