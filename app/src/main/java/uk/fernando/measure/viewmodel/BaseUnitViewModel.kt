package uk.fernando.measure.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.enum.UnitType

abstract class BaseUnitViewModel : ViewModel() {

    val unitList = mutableStateOf(emptyList<LengthUnitEntity>())

    open fun fetchUnitsByType(type: UnitType) {}

    open fun updateUnit(unit: LengthUnitEntity) {}

    open fun deleteUnit(unit: LengthUnitEntity) {}

    fun launchDefault(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    fun launchIO(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }
}
