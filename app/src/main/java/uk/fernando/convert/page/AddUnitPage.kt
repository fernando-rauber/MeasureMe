package uk.fernando.convert.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import uk.fernando.advertising.component.AdBanner
import uk.fernando.convert.R
import uk.fernando.convert.component.MyLoadingSpinner
import uk.fernando.convert.component.NavigationBarTop
import uk.fernando.convert.component.unit.AddUnitCard
import uk.fernando.convert.viewmodel.AddUnitViewModel
import uk.fernando.util.component.MyAnimatedVisibility

@Composable
fun AddUnitPage(
    navController: NavController = NavController(LocalContext.current),
    unitType: Int,
    viewModel: AddUnitViewModel = getViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.fetchAvailableUnits(unitType)
    }

    Column {
        NavigationBarTop(title = R.string.add_unit_title,
            leftIcon = R.drawable.ic_arrow_back,
            onLeftIconClick = { navController.popBackStack() }
        )

        UnitList(viewModel)
    }
}

@Composable
private fun UnitList(viewModel: AddUnitViewModel) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Loading
        MyAnimatedVisibility(viewModel.loading.value) {
            MyLoadingSpinner()
        }

        // Content
        MyAnimatedVisibility(!viewModel.loading.value) {
            if (viewModel.unitList.isEmpty())
                EmptyUnitListMessage()
            else
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
                    modifier = Modifier.fillMaxSize()
                ) {

                    items(viewModel.unitList) { unit ->
                        AddUnitCard(unit = unit) {
                            viewModel.addUnit(unit)
                        }
                    }
                }
        }

        AdBanner(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            unitId = stringResource(R.string.ad_banner_add_unit)
        )
    }
}

@Composable
private fun EmptyUnitListMessage() {
    Text(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .padding(bottom = 15.dp),
        text = stringResource(R.string.add_all_unit_message),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center
    )
}