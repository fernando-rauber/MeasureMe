package uk.fernando.measure.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import uk.fernando.measure.component.MyBackground
import uk.fernando.measure.navigation.Directions
import uk.fernando.measure.navigation.buildGraph
import uk.fernando.measure.theme.MeasureMeTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val controller = rememberNavController()

            MeasureMeTheme {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background
                ) { padding ->

                    MyBackground {
                        Column(modifier = Modifier.padding(padding)) {
                            NavHost(
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