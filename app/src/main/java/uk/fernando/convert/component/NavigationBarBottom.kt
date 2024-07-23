package uk.fernando.convert.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import uk.fernando.convert.R
import uk.fernando.convert.navigation.Directions
import uk.fernando.uikit.ext.safeNav

@Composable
fun NavigationBarBottom(navController: NavController) {

    NavigationBar(
        modifier = Modifier
            .shadow(
                elevation = 14.dp,
                shape = MaterialTheme.shapes.large.copy(
                    bottomEnd = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp)
                ),
                ambientColor = Color.Black,
                spotColor = Color.Black
            ),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        NavigationItemCustom(currentRoute == Directions.length.name, R.drawable.ic_ruler, R.string.length_title) {
            if (currentRoute != Directions.length.name)
                navController.safeNav(Directions.length.name)
        }

        NavigationItemCustom(currentRoute == Directions.weight.name, R.drawable.ic_weight, R.string.weight_title) {
            if (currentRoute != Directions.weight.name)
                navController.safeNav(Directions.weight.name)
        }

        NavigationItemCustom(currentRoute == Directions.temperature.name, R.drawable.ic_temperature, R.string.temperature_title) {
            if (currentRoute != Directions.temperature.name)
                navController.safeNav(Directions.temperature.name)
        }

        NavigationItemCustom(currentRoute == Directions.volume.name, R.drawable.ic_volume_liquid, R.string.volume_title) {
            if (currentRoute != Directions.volume.name)
                navController.safeNav(Directions.volume.name)
        }
    }
}


@Composable
fun RowScope.NavigationItemCustom(
    isSelected: Boolean,
    @DrawableRes iconID: Int,
    @StringRes stringID: Int,
    onClick: () -> Unit
) {
    NavigationBarItem(
        icon = {
            Icon(
                painter = painterResource(id = iconID),
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
        },
        colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colorScheme.surface),
        selected = isSelected,
        label = {
            Text(
                text = stringResource(id = stringID),
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
        },
        onClick = onClick
    )
}