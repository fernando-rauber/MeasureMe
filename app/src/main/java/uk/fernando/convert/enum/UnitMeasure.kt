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
    TONNE(25),

    //Volume
    LITER(26),
    CUBIC_METER(27),
    CUBIC_KILOMETER(28),
    CUBIC_CENTIMETER(29),
    CUBIC_MILLIMETER(30),
    CUBIC_MILE(31),
    CUBIC_YARD(32),
    CUBIC_FOOT(33),
    CUBIC_INCH(34),
    MILLILITER(35),
    US_GALLON(36),
    US_QUART(37),
    US_PINT(38),
    US_CUP(39),
    US_FLUID_OUNCE(41),
    US_TABLE_SPOON(41),
    US_TEA_SPOON(42),
    IMPERIAL_GALLON(43),
    IMPERIAL_QUART(44),
    IMPERIAL_PINT(45),
    IMPERIAL_CUP(46),
    IMPERIAL_FLUID_OUNCE(47),
    IMPERIAL_TABLE_SPOON(48),
    IMPERIAL_TEA_SPOON(49);

    companion object {
        fun getByValue(value: Int) = values().firstOrNull { it.value == value }
    }
}