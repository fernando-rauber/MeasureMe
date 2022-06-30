package uk.fernando.measure.enum

enum class UnitMeasure(val value: Int) {
    KILOMETER(1),
    METER(2),
    CENTIMETER(3),
    MILLIMETER(4),
    MICROMETER(5),
    NANOMETER(6),
    MILE(7),
    YARD(8),
    FOOT(9),
    INCH(10),
    NAUTICAL_MILE(11),

    CELSIUS(12),
    KELVIN(13),
    FAHRENHEIT(14),

    KILOGRAM(15),
    GRAM(16),
    MILLIGRAM(17),
    MICROGRAM(18),
    IMPERIAL_TON(19),
    US_TON(20),
    STONE(21),
    POUND(22),
    OUNCE(23),
    TONNE(24),
    CARAT(25);

    companion object {
        fun getByValue(value: Int) = values().firstOrNull { it.value == value }
    }
}