package uk.fernando.measure.model

data class MeasureUnitModel(
    val name: String = "",
    val abbreviation : String = "",
    val multiple: Double = 0.0,
    val position: Int = 1,
    var amount: Double = 0.0
)