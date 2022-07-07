package uk.fernando.convert.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel
import uk.fernando.convert.R
import uk.fernando.convert.navigation.Directions
import uk.fernando.convert.viewmodel.SplashViewModel

@Composable
fun SplashPage(navController: NavController = NavController(LocalContext.current), viewModel: SplashViewModel = getViewModel()) {
    Box(
        Modifier
            .fillMaxSize()
    ) {

        Column(
            Modifier
                .offset(y = (-50).dp)
                .fillMaxWidth(0.4f)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            CircularProgressIndicator(
                strokeWidth = 5.dp,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(top = 35.dp)
            )
        }
    }

    val isDarkMode = isSystemInDarkTheme()
    val currentOnTimeout by rememberUpdatedState {
        navController.navigate(Directions.length.name) {
            popUpTo(Directions.splash.name) { inclusive = true }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.firstSetUp(isDarkMode = isDarkMode)

        delay(1500L)

        currentOnTimeout()
    }
}
