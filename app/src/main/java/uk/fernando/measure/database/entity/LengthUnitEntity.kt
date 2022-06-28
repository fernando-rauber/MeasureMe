package uk.fernando.measure.database.entity

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = LengthUnitEntity.NAME)
data class LengthUnitEntity(
    val id: Int = 0,
    val name: String = "",
    val abbreviation: String = "",
    val multiple: Double = 0.0,
    var amount: Double = 0.0
) : Serializable {

    companion object {
        const val NAME = "length"
    }
}
