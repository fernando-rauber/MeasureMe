package uk.fernando.measure.ext

import androidx.navigation.NavController
import uk.fernando.measure.R
import uk.fernando.measure.enum.UnitMeasure
import uk.fernando.measure.enum.UnitMeasure.*
import uk.fernando.measure.enum.UnitType
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

fun Int.getUnitTypeIcon(): Int {
    return when (UnitType.getByValue(this)) {
        UnitType.LENGTH -> R.drawable.ic_ruler
        UnitType.TEMPERATURE -> R.drawable.ic_temperature
        UnitType.WEIGHT -> R.drawable.ic_weight
        else -> R.drawable.ic_volume_liquid
    }
}

fun Int.getUnitName(): Int {
    return when (UnitMeasure.getByValue(this)) {
        KILOMETER -> R.string.kilometer
        METER -> R.string.meter
        CENTIMETER -> R.string.centimeter
        MILLIMETER -> R.string.millimeter
        MICROMETER -> R.string.micrometer
        NANOMETER -> R.string.nanometer
        MILE -> R.string.mile
        YARD -> R.string.yard
        FOOT -> R.string.foot
        INCH -> R.string.inch
        NAUTICAL_MILE -> R.string.nautical_mile

        CELSIUS -> R.string.celsius
        KELVIN -> R.string.kelvin
        FAHRENHEIT -> R.string.fahrenheit

        KILOGRAM -> R.string.kilogram
        GRAM -> R.string.gram
        MILLIGRAM -> R.string.milligram
        MICROGRAM -> R.string.microgram
        IMPERIAL_TON -> R.string.imperial_ton
        US_TON -> R.string.us_ton
        STONE -> R.string.stone
        POUND -> R.string.pound
        OUNCE -> R.string.ounce
        TONNE -> R.string.tonne
        CARAT -> R.string.carat

        else -> R.string.kilometer
    }
}