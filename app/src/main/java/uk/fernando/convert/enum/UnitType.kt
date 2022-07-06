package uk.fernando.convert.enum

enum class UnitType(val value: Int) {
    LENGTH(1),
    TEMPERATURE(2),
    WEIGHT(3),
    VOLUME(4);

    companion object {
        fun getByValue(value: Int) = values().firstOrNull { it.value == value }
    }
}