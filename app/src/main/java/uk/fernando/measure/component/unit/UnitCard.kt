package uk.fernando.measure.component.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.fernando.measure.R
import uk.fernando.measure.component.MyAnimation
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.ext.noRippleClickable
import uk.fernando.measure.theme.green
import uk.fernando.measure.theme.red

@Composable
fun UnitCard(unit: LengthUnitEntity, onDone: (Double) -> Unit) {
    var canEdit by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .padding(top = 10.dp)
            .border(2.dp, if (canEdit) green else Color.Transparent, MaterialTheme.shapes.small)
            .fillMaxWidth(),
        shadowElevation = 5.dp,
        tonalElevation = 5.dp,
        shape = MaterialTheme.shapes.small
    ) {

        Row(
            Modifier.padding(10.dp),
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

            MyAnimation(!canEdit) {
                Text(
                    modifier = Modifier.noRippleClickable { canEdit = true },
                    text = "${unit.amount}"
                )
            }

            MyAnimation(canEdit) {
                MyTextField(
                    value = unit.amount.toString(),
                    onDone = {
                        onDone(it)
                        canEdit = false
                    },
                    lostFocus = { canEdit = false }
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MyTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onDone: (Double) -> Unit,
    lostFocus: () -> Unit
) {
    var textField by remember { mutableStateOf(value) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var isFocusActive = false

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(modifier = modifier
        .focusRequester(focusRequester)
        .onFocusEvent {
            if (it.isFocused)
                isFocusActive = true
            else if (isFocusActive && !it.isFocused)
                lostFocus()
        },
        value = textField,
        onValueChange = { textField = it },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (textField.isNotEmpty()) {
                    onDone(textField.toDouble())
                    keyboardController?.hide()
                }
            }
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = TextStyle.Default.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            textAlign = TextAlign.End
        ),
        decorationBox = { innerTextField ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.padding(10.dp)) {
                    innerTextField()
                }
            }
        }
    )
}