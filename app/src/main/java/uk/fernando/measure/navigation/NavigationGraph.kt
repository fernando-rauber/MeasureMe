package uk.fernando.measure.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.navigation.Directions.UNIT_TYPE
import uk.fernando.measure.page.AddUnitPage
import uk.fernando.measure.page.HomePage
import uk.fernando.measure.page.SettingsPage
import uk.fernando.measure.page.SplashPage


@ExperimentalAnimationApi
fun NavGraphBuilder.buildGraph(navController: NavController) {
    composable(Directions.splash.name) {
        SplashPage(navController)
    }
    composable(Directions.length.name) {
        HomePage(navController, UnitType.LENGTH)
    }
    composable(Directions.weight.name) {
        HomePage(navController, UnitType.WEIGHT)
    }
    composable(Directions.temperature.name) {
        HomePage(navController, UnitType.TEMPERATURE)
    }
    composable(Directions.volume.name) {
        HomePage(navController, UnitType.VOLUME)
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


