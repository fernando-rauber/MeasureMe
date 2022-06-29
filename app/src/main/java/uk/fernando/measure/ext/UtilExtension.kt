package uk.fernando.measure.ext

import androidx.navigation.NavController
import java.math.RoundingMode
import java.text.DecimalFormat

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return tag.take(23)
    }

fun NavController.safeNav(direction: String) {
    try {
        this.navigate(direction)
    } catch (e: Exception) {
    }
}

fun Double.roundOffDecimal(): Double {
    val df = DecimalFormat("#.####")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}