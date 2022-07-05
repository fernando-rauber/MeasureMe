package uk.fernando.measure.component.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uk.fernando.measure.R
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.ext.getUnitName
import uk.fernando.measure.ext.getUnitTypeIcon
import uk.fernando.measure.theme.blue
import uk.fernando.measure.theme.green

@Composable
fun AddUnitCard(unit: LengthUnitEntity, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(top = 10.dp)
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        shadowElevation = 4.dp,
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Row(
                Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier
                        .padding(7.dp)
                        .background(MaterialTheme.colorScheme.secondary.copy(0.6f), RoundedCornerShape(8.dp))
                        .padding(5.dp)
                        .size(36.dp),
                    painter = painterResource(unit.type.getUnitTypeIcon()),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.weight(0.5f),
                    text = stringResource(unit.unit.getUnitName()),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary.copy(0.8f))
                .clickable { onClick() }) {

                Icon(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}