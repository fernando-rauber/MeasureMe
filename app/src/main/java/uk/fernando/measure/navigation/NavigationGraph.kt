package uk.fernando.measure.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.navigation.Directions.UNIT_TYPE
import uk.fernando.measure.page.*


@ExperimentalAnimationApi
fun NavGraphBuilder.buildGraph(navController: NavController) {
    composable(Directions.splash.name) {
        SplashPage(navController)
    }
    composable(Directions.length.name) {
        UnitPage(navController, UnitType.LENGTH)
    }
    composable(Directions.weight.name) {
        UnitPage(navController, UnitType.WEIGHT)
    }
    composable(Directions.temperature.name) {
        TemperaturePage(navController)
    }
    composable(Directions.volume.name) {
        UnitPage(navController, UnitType.VOLUME)
    }
    composable(Directions.settings.name) {
        SettingsPage(navController)
    }
    composable(Directions.addUnit.name.plus("/{$UNIT_TYPE}")) {
        val unitType = it.arguments?.getString(UNIT_TYPE)
        if (unitType == null)
            navController.popBackStack()
        else
            AddUnitPage(navController, unitType.toInt())
    }
}


