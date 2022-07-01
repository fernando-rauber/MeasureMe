package uk.fernando.measure.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.navigation.Directions.UNIT_TYPE
import uk.fernando.measure.page.AddUnitPage
import uk.fernando.measure.page.SettingsPage
import uk.fernando.measure.page.SplashPage
import uk.fernando.measure.page.UnitPage


@ExperimentalAnimationApi
fun NavGraphBuilder.buildGraph(navController: NavController) {
    composable(Directions.splash.name) {
        SplashPage(navController)
    }

    composableSlideAnim(
        leftDirection = null,
        direction = Directions.length.name,
        rightDirection = Directions.weight.name,
        content = { UnitPage(navController, UnitType.LENGTH) }
    )

    composableSlideAnim(
        leftDirection = Directions.length.name,
        direction = Directions.weight.name,
        rightDirection = Directions.temperature.name
    ) {
        UnitPage(navController, UnitType.WEIGHT)
    }

    composableSlideAnim(
        leftDirection = Directions.weight.name,
        direction = Directions.temperature.name,
        rightDirection = Directions.volume.name
    ) {
        UnitPage(navController, UnitType.TEMPERATURE)
    }

    composableSlideAnim(
        leftDirection = Directions.temperature.name,
        direction = Directions.volume.name,
        rightDirection = null
    ) {
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

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.composableSlideAnim(leftDirection: String?, direction: String, rightDirection: String?, content: @Composable () -> Unit) {
    composable(direction,
        enterTransition = {
            when (initialState.destination.route) {
                rightDirection -> slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                leftDirection -> slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                rightDirection -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                leftDirection -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                else -> null
            }
        }) {
        content()
    }
}


