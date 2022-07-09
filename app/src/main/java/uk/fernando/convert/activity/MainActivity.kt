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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import uk.fernando.advertising.AdInterstitial
import uk.fernando.convert.R
import uk.fernando.convert.component.MyBackground
import uk.fernando.convert.component.NavigationBarBottom
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.navigation.Directions
import uk.fernando.convert.navigation.buildGraph
import uk.fernando.convert.theme.MeasureMeTheme
import uk.fernando.convert.usecase.AddVideoAdCounterUseCase

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    private val dataStore: PrefsStore by inject()
    private val addVideoAdCounterUserCase: AddVideoAdCounterUseCase by inject()
    private lateinit var fullScreenAd: AdInterstitial

    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val controller = rememberAnimatedNavController()
            val navBackStackEntry by controller.currentBackStackEntryAsState()

            val isDarkMode = dataStore.isDarkMode().collectAsState(true)
            val isDynamicColor = dataStore.isDynamicColor().collectAsState(initial = false)
            val isPremium = dataStore.isPremium().collectAsState(initial = false)

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

            if (!isPremium.value) {
                fullScreenAd = AdInterstitial(this@MainActivity, stringResource(id = R.string.ad_full_page))
                ShouldShowFullAd(dataStore, addVideoAdCounterUserCase)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        scope.launch {
            addVideoAdCounterUserCase(1)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    @Composable
    private fun ShouldShowFullAd(dataStore: PrefsStore, addVideoAdCounterUserCase: AddVideoAdCounterUseCase) {
        val adCounter = dataStore.counterVideoAd().collectAsState(initial = 0)

        if (adCounter.value >= 4) {
            rememberCoroutineScope().launch {

                addVideoAdCounterUserCase(-4)
                delay(2000)
                fullScreenAd.showAdvert()
            }
        }
    }
}