package uk.fernando.measure.component.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uk.fernando.measure.R
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.ext.getUnitName
import uk.fernando.measure.ext.getUnitTypeIcon
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
                Modifier
                    .padding(4.dp)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(0.2f), CircleShape)
                        .padding(4.dp)
                        .size(36.dp),
                    painter = painterResource(unit.type.getUnitTypeIcon()),
                    contentDescription = null
                )

                Divider(
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .width(1.dp)
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(0.5f),
                    text = stringResource(unit.unit.getUnitName()),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(modifier = Modifier
                .fillMaxHeight()
                .background(green)
                .clickable { onClick() }) {

                Text(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .align(Alignment.Center),
                    text = "ADD",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}