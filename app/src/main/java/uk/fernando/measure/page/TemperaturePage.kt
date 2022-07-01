package uk.fernando.measure.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.viewmodel.TemperatureViewModel
import uk.fernando.measure.viewmodel.UnitViewModel

@Composable
fun TemperaturePage(
    navController: NavController = NavController(LocalContext.current),
    viewModel: TemperatureViewModel = getViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUnitsByType(UnitType.TEMPERATURE)
    }

    Column {
        NavigationBar(navController, UnitType.TEMPERATURE)

        MeasureList(
            viewModel = viewModel,
            addUnitClick = {}
        )
    }
}