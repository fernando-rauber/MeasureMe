package uk.fernando.convert.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import org.koin.android.ext.android.inject
import uk.fernando.advertising.component.AdBanner
import uk.fernando.convert.R
import uk.fernando.convert.component.MyBackground
import uk.fernando.convert.component.NavigationBarBottom
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.navigation.Directions
import uk.fernando.convert.navigation.buildGraph
import uk.fernando.convert.theme.MeasureMeTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    private val dataStore: PrefsStore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val controller = rememberAnimatedNavController()
            val navBackStackEntry by controller.currentBackStackEntryAsState()

            val isDarkMode = dataStore.isDarkMode().collectAsState(true)
            val isDynamicColor = dataStore.isDynamicColor().collectAsState(initial = false)

            MeasureMeTheme(darkTheme = isDarkMode.value, dynamicColor = isDynamicColor.value) {

                Scaffold(
                    bottomBar = {
                        when (navBackStackEntry?.destination?.route) {
                            Directions.length.name, Directions.weight.name, Directions.temperature.name, Directions.volume.name ->
                                NavigationBarBottom(controller)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) { padding ->

                    MyBackground {
                        Box(modifier = Modifier.padding(padding)) {
                            AnimatedNavHost(
                                navController = controller,
                                startDestination = Directions.splash.name
                            ) {
                                buildGraph(controller)
                            }

                            AdBanner(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 8.dp),
                                unitId = R.string.ad_banner_unit
                            )
                        }
                    }
                }
            }

        }
    }
}