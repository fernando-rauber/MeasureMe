package uk.fernando.measure.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
        HomePage(navController)
    }
    composable(Directions.settings.name) {
        SettingsPage(navController)
    }
    composable(Directions.addUnit.name) {
        AddUnitPage(navController)
    }
}


