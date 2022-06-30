package uk.fernando.measure.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import uk.fernando.measure.R
import uk.fernando.measure.component.MySwipeDelete
import uk.fernando.measure.component.NavigationBarTop
import uk.fernando.measure.component.unit.UnitCard
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.ext.safeNav
import uk.fernando.measure.navigation.Directions
import uk.fernando.measure.theme.red
import uk.fernando.measure.viewmodel.HomeViewModel

@Composable
fun HomePage(
    navController: NavController = NavController(LocalContext.current),
    unitType: UnitType,
    viewModel: HomeViewModel = getViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUnitsByType(unitType)
    }

    Column {
        NavigationBar(navController, unitType)

        MeasureList(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize(),
            viewModel = viewModel
        )
    }
}

@Composable
private fun NavigationBar(navController: NavController, unitType: UnitType) {
    NavigationBarTop(title = R.string.length_title,
        rightIcon = {
            Row {
                // add more measures units
                IconButton(onClick = { navController.safeNav(Directions.addUnit.name.plus("/${unitType.value}")) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                // Settings
                IconButton(onClick = { navController.safeNav(Directions.settings.name) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MeasureList(modifier: Modifier, viewModel: HomeViewModel) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
        modifier = modifier
    ) {

        items(viewModel.unitList.value) { unit ->
            key(unit.id, unit.date.time) {
                MySwipeDelete(
                    item = unit,
                    background = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 10.dp)
                                .background(
                                    color = red,
                                    shape = RoundedCornerShape(50)
                                )

                        ) {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 15.dp),
                                painter = painterResource(id = R.drawable.ic_trash_bin),
                                tint = Color.White,
                                contentDescription = null
                            )
                        }
                    },
                    content = {
                        UnitCard(unit = unit) { newAmount ->
                            unit.amount = newAmount
                            viewModel.updateUnit(unit)
                        }
                    },
                    onDismiss = { itemDeleted ->
                        viewModel.deleteUnit(itemDeleted)
                    }
                )
            }
        }
    }
}