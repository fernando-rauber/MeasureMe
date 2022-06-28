package uk.fernando.measure.component.unit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.fernando.measure.R
import uk.fernando.measure.model.MeasureUnitModel

@Composable
fun UnitCard(modifier: Modifier, unit: MeasureUnitModel) {
    Surface(
        modifier = modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        shadowElevation = 5.dp,
        tonalElevation = 5.dp,
        shape = MaterialTheme.shapes.small
    ) {

        Row(Modifier.padding(15.dp)) {

            Icon(painterResource(id = R.drawable.ic_settings), contentDescription = null )
            Text(
                modifier = Modifier.padding(horizontal = 10.dp).weight(1f),
                text = unit.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}