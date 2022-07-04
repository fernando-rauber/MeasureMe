package uk.fernando.measure.ext

import uk.fernando.measure.R
import uk.fernando.measure.enum.UnitMeasure
import uk.fernando.measure.enum.UnitMeasure.*
import uk.fernando.measure.enum.UnitType
import java.math.RoundingMode
import java.text.DecimalFormat


fun Double.roundOffDecimal(pattern: String): Double {
    val df = DecimalFormat(pattern)
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}

fun Int.getPatterDecimalFormat(): String {
    return when (UnitType.getByValue(this)) {
        UnitType.TEMPERATURE -> "#.##"
        else -> "#.####"
    }
}

fun Int.getUnitTypeIcon(): Int {
    return when (UnitType.getByValue(this)) {
        UnitType.LENGTH -> R.drawable.ic_ruler
        UnitType.TEMPERATURE -> R.drawable.ic_temperature
        UnitType.WEIGHT -> R.drawable.ic_weight
        else -> R.drawable.ic_volume_liquid
    }
}

fun Int.getTitle(): Int {
    return when (UnitType.getByValue(this)) {
        UnitType.LENGTH -> R.string.length_title
        UnitType.TEMPERATURE -> R.string.temperature_title
        UnitType.WEIGHT -> R.string.weight_title
        else -> R.string.volume_title
    }
}

fun Int.getUnitName(): Int {
    return when (UnitMeasure.getByValue(this)) {
        // Length
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

        // Temperature
        CELSIUS -> R.string.celsius
        KELVIN -> R.string.kelvin
        FAHRENHEIT -> R.string.fahrenheit

        // Weight
        KILOGRAM -> R.string.kilogram
        GRAM -> R.string.gram
        MILLIGRAM -> R.string.milligram
        MICROGRAM -> R.string.microgram
        IMPERIAL_TON -> R.string.imperial_ton
        US_TON -> R.string.us_ton
        STONE -> R.string.stone
        POUND -> R.string.pound
        OUNCE -> R.string.ounce
        CARAT -> R.string.carat

        // Volume
        LITER -> R.string.liter
        CUBIC_METER -> R.string.cubit_meter
//        CUBIC_KILOMETER -> R.string.cubit_kilometer
        CUBIC_CENTIMETER -> R.string.cubit_centimeter
        CUBIC_MILLIMETER -> R.string.cubit_millimeter
//        CUBIC_MILE -> R.string.cubic_mile
        CUBIC_YARD -> R.string.cubit_yard
        CUBIC_FOOT -> R.string.cubit_foot
        CUBIC_INCH -> R.string.cubit_inch
        MILLILITER -> R.string.millimeter
        US_GALLON -> R.string.us_gallon
        US_QUART -> R.string.us_quart
        US_PINT -> R.string.us_pint
        US_CUP -> R.string.us_cup
        US_FLUID_OUNCE -> R.string.us_fluid_ounce
        US_TABLE_SPOON -> R.string.us_tablespoon
        US_TEA_SPOON -> R.string.us_teaspoon
        IMPERIAL_GALLON -> R.string.imperial_gallon
        IMPERIAL_QUART -> R.string.imperial_quart
        IMPERIAL_PINT -> R.string.imperial_pint
        IMPERIAL_CUP -> R.string.imperial_cup
        IMPERIAL_FLUID_OUNCE -> R.string.imperial_fluid_ounce
        IMPERIAL_TABLE_SPOON -> R.string.imperial_tablespoon
        IMPERIAL_TEA_SPOON -> R.string.imperial_teaspoon

        else -> R.string.kilometer
    }
}