package uk.fernando.measure.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.fernando.measure.page.HomePage
import uk.fernando.measure.page.SplashPage


@ExperimentalAnimationApi
fun NavGraphBuilder.buildGraph(navController: NavController) {
    composable(Directions.splash.name) {
        SplashPage(navController)
    }
    composable(Directions.home.name) {
        HomePage(navController)
    }
    composable(Directions.settings.name) {
    }
    composable(Directions.measureList.name) {
    }
}


