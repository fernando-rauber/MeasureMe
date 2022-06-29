package uk.fernando.measure.component.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uk.fernando.measure.R
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.theme.green
import uk.fernando.measure.theme.red

@Composable
fun AddUnitCard(unit: LengthUnitEntity, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(top = 10.dp)
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        shadowElevation = 5.dp,
        tonalElevation = 5.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Row(
                Modifier
                    .padding(10.dp)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier
                        .background(red, CircleShape)
                        .padding(7.dp),
                    painter = painterResource(id = R.drawable.ic_ruler),
                    contentDescription = null
                )


                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(0.5f),
                    text = unit.name,
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
                    text = "ADD"
                )
            }
        }
    }
}