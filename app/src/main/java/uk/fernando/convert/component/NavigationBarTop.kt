package uk.fernando.convert.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uk.fernando.util.component.MyIconButton

@Composable
fun NavigationBarTop(
    @StringRes title: Int,
    @DrawableRes leftIcon: Int? = null,
    onLeftIconClick: () -> Unit = {},
    rightIcon: (@Composable () -> Unit)? = null
) {

    Box(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        if (leftIcon != null)
            MyIconButton(
                icon = leftIcon,
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onLeftIconClick,
                tint = Color.White
            )

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(title),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )

        if (rightIcon != null) {
            Box(Modifier.align(Alignment.CenterEnd)) {
                rightIcon()
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}