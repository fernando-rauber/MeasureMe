package uk.fernando.convert.page

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.inject
import uk.fernando.convert.BuildConfig
import uk.fernando.convert.R
import uk.fernando.convert.component.NavigationBarTop
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.viewmodel.SettingsViewModel
import uk.fernando.uikit.ext.clickableSingle

@Composable
fun SettingsPage(
    navController: NavController = NavController(LocalContext.current),
    viewModel: SettingsViewModel = getViewModel()
) {
    val context = LocalContext.current
    val prefs: PrefsStore by inject()
    val isDarkMode = prefs.isDarkMode().collectAsState(initial = false)
    val isDynamicColor = prefs.isDynamicColor().collectAsState(initial = false)

    Column(Modifier.fillMaxSize()) {

        NavigationBarTop(title = R.string.settings_title,
            leftIcon = R.drawable.ic_arrow_back,
            onLeftIconClick = { navController.popBackStack() }
        )

        Column(
            Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            CustomSettingsResourcesCard(
                text = R.string.dark_mode,
                isChecked = isDarkMode.value,
                onCheckedChange = viewModel::updateDarkMode
            )

            // Only available on Android 12+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                CustomSettingsResourcesCard(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = R.string.dynamic_color,
                    subText = R.string.dynamic_color_subtext,
                    isChecked = isDynamicColor.value,
                    onCheckedChange = viewModel::updateDynamicColor
                )
            } else
                Spacer(modifier = Modifier.height(10.dp))

            CustomSettingsResourcesCard(
                modifier = Modifier.padding(bottom = 10.dp),
                modifierRow = Modifier
                    .clickableSingle {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://app.websitepolicies.com/policies/view/7u94tia2"))
                        context.startActivity(browserIntent)
                    },
                text = R.string.privacy_policy,
                isChecked = false,
                onCheckedChange = {},
                showArrow = true
            )

            Spacer(Modifier.weight(0.9f))

            Text(
                text = stringResource(id = R.string.version, BuildConfig.VERSION_NAME),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                textAlign = TextAlign.Center
            )

            if (BuildConfig.BUILD_TYPE == "debug") {
                Text(
                    text = "Dev Build",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun CustomSettingsResourcesCard(
    modifier: Modifier = Modifier,
    modifierRow: Modifier = Modifier,
    @StringRes text: Int,
    @StringRes subText: Int? = null,
    isChecked: Boolean,
    isPremium: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    showArrow: Boolean? = null
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        tonalElevation = 2.dp,
        shadowElevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifierRow.padding(16.dp)
        ) {

            Column(
                Modifier
                    .padding(end = 20.dp)
                    .weight(1f),
            ) {

                Row {

                    Text(
                        text = stringResource(id = text),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(end = 5.dp)
                    )

                    if (isPremium)
                        Image(painter = painterResource(id = R.drawable.ic_crown), contentDescription = null)
                }

                subText?.let {
                    Text(
                        text = stringResource(id = subText),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (showArrow == null)
                Switch(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedBorderColor = Color.Transparent,
                        uncheckedThumbColor = Color.White,
                    )
                )
            else if (showArrow)
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
        }
    }
}