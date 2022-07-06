package uk.fernando.convert.page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import uk.fernando.convert.component.*
import uk.fernando.convert.component.unit.UnitCard
import uk.fernando.convert.enum.UnitType
import uk.fernando.convert.ext.getTitle
import uk.fernando.convert.ext.safeNav
import uk.fernando.convert.navigation.Directions
import uk.fernando.convert.theme.red
import uk.fernando.convert.viewmodel.UnitViewModel
import uk.fernando.convert.R

@Composable
fun UnitPage(
    navController: NavController = NavController(LocalContext.current),
    unitType: UnitType,
    viewModel: UnitViewModel = getViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUnitsByType(unitType)
    }

    Column {
        NavigationBar(navController, unitType)

        MeasureList(
            viewModel = viewModel,
            addUnitClick = { navController.safeNav(Directions.addUnit.name.plus("/${unitType.value}")) }
        )
    }
}

@Composable
private fun NavigationBar(navController: NavController, unitType: UnitType) {
    NavigationBarTop(title = unitType.value.getTitle(),
        rightIcon = {
            Row {
                // add more measures units
                IconButton(onClick = { navController.safeNav(Directions.addUnit.name.plus("/${unitType.value}")) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                // Settings
                IconButton(onClick = { navController.safeNav(Directions.settings.name) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        })
}

@Composable
fun MeasureList(viewModel: UnitViewModel, addUnitClick: () -> Unit) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Loading
        MyAnimation(viewModel.loading.value) {
            MyLoadingSpinner()
        }

        // Content
        MyAnimation(!viewModel.loading.value) {
            if (viewModel.unitList.value.isEmpty())
                EmptyUnitListMessage(addUnitClick)
            else
                UnitList(viewModel, addUnitClick)
        }

        MyAdBanner(Modifier.align(Alignment.BottomCenter), unitId = R.string.ad_banner_unit)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UnitList(viewModel: UnitViewModel, addUnitClick: () -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
        modifier = Modifier.fillMaxSize()
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
                                    shape = MaterialTheme.shapes.small
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

        item {
            MyButton(
                modifier = Modifier.padding(top = 10.dp),
                borderStroke = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                color = MaterialTheme.colorScheme.surface,
                textColor = MaterialTheme.colorScheme.primary,
                onClick = addUnitClick,
                text = stringResource(R.string.add_unit_action)
            )
        }
    }
}

@Composable
private fun EmptyUnitListMessage(addUnitClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(bottom = 15.dp),
            text = stringResource(R.string.empty_list_message),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        MyButton(
            modifier = Modifier
                .padding(16.dp)
                .defaultMinSize(minHeight = 50.dp),
            onClick = addUnitClick,
            text = stringResource(R.string.add_unit_action)
        )
    }
}