package uk.fernando.convert.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    titleLarge = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    labelSmall = TextStyle(
        fontFamily = robotoFamily,
        fontSize = 10.sp
    ),
    labelMedium = TextStyle(
        fontFamily = robotoFamily,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = robotoFamily,
        fontSize = 14.sp
    ),
)