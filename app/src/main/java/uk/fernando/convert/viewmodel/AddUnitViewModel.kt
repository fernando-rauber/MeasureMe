package uk.fernando.convert.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import uk.fernando.convert.database.entity.UnitEntity
import uk.fernando.convert.usecase.AddUnitUseCase
import uk.fernando.convert.util.Resource
import uk.fernando.util.ext.TAG


class AddUnitViewModel(private val useCase: AddUnitUseCase) : BaseViewModel() {

    val unitList = mutableStateListOf<UnitEntity>()
    val loading = mutableStateOf(false)

    fun fetchAvailableUnits(unitType: Int) {
        launchDefault {
            useCase.getAvailableUnitList(unitType).collect { result ->
                when (result) {
                    is Resource.Success -> unitList.addAll(result.data ?: emptyList())
                    is Resource.Error -> Log.e(TAG, result.message ?: "An unexpected error occurred")
                    is Resource.Loading -> loading.value = result.isLoading
                }
            }
        }
    }

    fun addUnit(unit: UnitEntity) {
        launchDefault {
            unitList.remove(unit)

            useCase.insertUnit(unit)
        }
    }
}



