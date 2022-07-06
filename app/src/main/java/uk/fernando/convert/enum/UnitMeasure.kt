package uk.fernando.convert.enum

enum class UnitMeasure(val value: Int) {
    //Length
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

    //Temperature
    CELSIUS(12),
    KELVIN(13),
    FAHRENHEIT(14),

    //Weight
    KILOGRAM(15),
    GRAM(16),
    MILLIGRAM(17),
    MICROGRAM(18),
    IMPERIAL_TON(19),
    US_TON(20),
    STONE(21),
    POUND(22),
    OUNCE(23),
    CARAT(24),

    //Volume
    LITER(25),
    CUBIC_METER(26),
    CUBIC_KILOMETER(27),
    CUBIC_CENTIMETER(28),
    CUBIC_MILLIMETER(29),
    CUBIC_MILE(30),
    CUBIC_YARD(31),
    CUBIC_FOOT(32),
    CUBIC_INCH(33),
    MILLILITER(34),
    US_GALLON(35),
    US_QUART(36),
    US_PINT(37),
    US_CUP(38),
    US_FLUID_OUNCE(39),
    US_TABLE_SPOON(40),
    US_TEA_SPOON(41),
    IMPERIAL_GALLON(42),
    IMPERIAL_QUART(43),
    IMPERIAL_PINT(44),
    IMPERIAL_CUP(45),
    IMPERIAL_FLUID_OUNCE(46),
    IMPERIAL_TABLE_SPOON(47),
    IMPERIAL_TEA_SPOON(48);

    companion object {
        fun getByValue(value: Int) = values().firstOrNull { it.value == value }
    }
}