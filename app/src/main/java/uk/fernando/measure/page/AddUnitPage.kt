package uk.fernando.measure.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import uk.fernando.measure.R
import uk.fernando.measure.component.NavigationBarTop
import uk.fernando.measure.component.unit.AddUnitCard
import uk.fernando.measure.component.unit.UnitCard
import uk.fernando.measure.viewmodel.AddUnitViewModel
import uk.fernando.measure.viewmodel.HomeViewModel

@Composable
fun AddUnitPage(
    navController: NavController = NavController(LocalContext.current),
    viewModel: AddUnitViewModel = getViewModel()
) {
    Column {
        NavigationBarTop(title = R.string.home_title,
            leftIcon = R.drawable.ic_arrow_back,
            onLeftIconClick = { navController.popBackStack() }
        )

        UnitList(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxSize(),
            viewModel = viewModel
        )
    }
}

@Composable
private fun UnitList(modifier: Modifier, viewModel: AddUnitViewModel) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
        modifier = modifier
    ) {

        items(viewModel.unitList) { unit ->
            AddUnitCard(unit = unit) {
                viewModel.addUnit(unit)
            }
        }
    }
}