package uk.fernando.measure.component.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
            .height(IntrinsicSize.Min)
            .border(2.dp, if (canEdit) MaterialTheme.colorScheme.primary else Color.Transparent, RoundedCornerShape(50))
            .fillMaxWidth(),
        shadowElevation = 5.dp,
        tonalElevation = 5.dp,
        shape = RoundedCornerShape(50)
    ) {

        Row(
            Modifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .background(MaterialTheme.colorScheme.onBackground.copy(0.1f), CircleShape)
                    .padding(7.dp)
                    .size(36.dp),
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
                    text = "${unit.amount}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
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

//            Divider(
//                Modifier
//                    .padding(horizontal = 5.dp)
//                    .fillMaxHeight()
//                    .width(1.dp)
//            )
//
//            Text(
//                text = "km",
//                color = MaterialTheme.colorScheme.onSurface,
//                style = MaterialTheme.typography.bodyMedium
//            )
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
    var textField by remember {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        )
    }
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
                if (textField.text.isNotEmpty()) {
                    onDone(textField.text.toDouble())
                    keyboardController?.hide()
                }
            }
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = TextStyle.Default.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
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