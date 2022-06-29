package uk.fernando.measure.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import uk.fernando.measure.ext.safeNav
import uk.fernando.measure.navigation.Directions
import uk.fernando.measure.theme.red
import uk.fernando.measure.viewmodel.HomeViewModel

@Composable
fun HomePage(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeViewModel = getViewModel()
) {
    Column {
        NavigationBar(navController)

        MeasureList(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxSize(),
            viewModel = viewModel
        )
    }
}

@Composable
private fun NavigationBar(navController: NavController) {
    NavigationBarTop(title = R.string.home_title,
        rightIcon = {
            Row {
                // add more measures
                IconButton(onClick = { navController.safeNav(Directions.measureList.name) }) {
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

        items(viewModel.lengthUnit.value) { unit ->
            key(unit.id) {
                MySwipeDelete(
                    item = unit,
                    background = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 10.dp)
                                .background(
                                    color = red,
                                    shape = MaterialTheme.shapes.small
                                )

                        ) {

                            Text(
                                text = stringResource(id = R.string.remove_action),
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 15.dp)
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