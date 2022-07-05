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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uk.fernando.measure.R
import uk.fernando.measure.component.MyAnimation
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.ext.getUnitName
import uk.fernando.measure.ext.getUnitTypeIcon
import uk.fernando.measure.ext.isInteger
import uk.fernando.measure.ext.noRippleClickable

@Composable
fun UnitCard(unit: LengthUnitEntity, onDone: (Double) -> Unit) {
    var isEditMode by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .padding(top = 10.dp)
            .height(IntrinsicSize.Min)
            .border(2.dp, if (isEditMode) MaterialTheme.colorScheme.primary else Color.Transparent, MaterialTheme.shapes.small)
            .fillMaxWidth(),
        shadowElevation = 4.dp,
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.small
    ) {

        Row(
            Modifier.padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(0.7f), CircleShape)
                    .padding(5.dp)
                    .size(36.dp),
                painter = painterResource(unit.type.getUnitTypeIcon()),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 7.dp)
                    .weight(0.5f),
                text = stringResource(unit.unit.getUnitName()),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )

            val amount = unit.amount.isInteger()

            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .noRippleClickable { if (!isEditMode) isEditMode = true },
                contentAlignment = Alignment.CenterEnd
            ) {

                MyAnimation(!isEditMode) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = amount,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )

                        Divider(
                            modifier = Modifier
                                .fillMaxHeight(0.8f)
                                .padding(start = 7.dp)
                                .width(1.dp)
                        )

                        Icon(
                            modifier = Modifier.padding(end = 3.dp),
                            painter = painterResource(R.drawable.ic_calculator),
                            contentDescription = null
                        )
                    }
                }

                MyAnimation(isEditMode) {
                    MyTextField(
                        value = amount,
                        onDone = {
                            onDone(it)
                            isEditMode = false
                        },
                        lostFocus = { isEditMode = false }
                    )
                }
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
    var textField by remember {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(0, value.length)
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
        .padding(end = 5.dp)
        .focusRequester(focusRequester)
        .onFocusChanged {
            if (it.isFocused)
                isFocusActive = true
            else if (isFocusActive && !it.isFocused)
                lostFocus()
        },
        value = textField,
        onValueChange = { textField = it },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
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