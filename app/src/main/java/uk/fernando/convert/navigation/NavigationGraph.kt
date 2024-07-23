package uk.fernando.convert.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.fernando.convert.enum.UnitType
import uk.fernando.convert.navigation.Directions.UNIT_TYPE
import uk.fernando.convert.page.AddUnitPage
import uk.fernando.convert.page.SettingsPage
import uk.fernando.convert.page.SplashPage
import uk.fernando.convert.page.UnitPage


@ExperimentalAnimationApi
fun NavGraphBuilder.buildGraph(navController: NavController) {
    composable(Directions.splash.name){
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

    composable(Directions.settings.name,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down, animationSpec = tween(700)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(700)) }
    ) {
        SettingsPage(navController)
    }

    composable(Directions.addUnit.name.plus("/{$UNIT_TYPE}"),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down, animationSpec = tween(700)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(700)) }
    ) {

        val unitType = it.arguments?.getString(UNIT_TYPE)
        if (unitType == null)
            navController.popBackStack()
        else
            AddUnitPage(navController, unitType.toInt())
    }
}

private fun NavGraphBuilder.composableSlideAnim(leftDirection: String?, direction: String, rightDirection: String?, content: @Composable () -> Unit) {
    composable(direction,
        enterTransition = {
            when (initialState.destination.route) {
                rightDirection -> slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                leftDirection -> slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                rightDirection -> slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                leftDirection -> slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                else -> null
            }
        }) {
        content()
    }
}


