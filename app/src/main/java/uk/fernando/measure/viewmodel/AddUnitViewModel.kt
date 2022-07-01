package uk.fernando.measure.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.ext.TAG
import uk.fernando.measure.usecase.AddUnitUseCase
import uk.fernando.measure.util.Resource


class AddUnitViewModel(private val useCase: AddUnitUseCase) : BaseViewModel() {

    val unitList = mutableStateListOf<LengthUnitEntity>()
    val loading = mutableStateOf(false)

    fun fetchAvailableUnits(unitType: Int) {
        launchDefault {
            useCase.getAvailableUnitList(unitType).collect { result ->
                when (result) {
                    is Resource.Success -> unitList.addAll(result.data ?: emptyList())
                    is Resource.Error -> Log.e(TAG, result.message ?: "An unexpected error occured")
                    is Resource.Loading -> loading.value = result.isLoading
                }
            }
        }
    }

    fun addUnit(unit: LengthUnitEntity) {
        launchDefault {
            unitList.remove(unit)

            useCase.insertUnit(unit)
        }
    }
}



