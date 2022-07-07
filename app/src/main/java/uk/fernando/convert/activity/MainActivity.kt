package uk.fernando.convert.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import org.koin.androidx.compose.inject
import uk.fernando.convert.component.MyBackground
import uk.fernando.convert.component.NavigationBarBottom
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.navigation.Directions
import uk.fernando.convert.navigation.buildGraph
import uk.fernando.convert.theme.MeasureMeTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val dataStore: PrefsStore by inject()
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
                        Column(modifier = Modifier.padding(padding)) {
                            AnimatedNavHost(
                                navController = controller,
                                startDestination = Directions.splash.name
                            ) {
                                buildGraph(controller)
                            }
                        }
                    }
                }
            }
        }
    }
}