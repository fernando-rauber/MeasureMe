package uk.fernando.convert.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = LengthUnitEntity.NAME)
data class LengthUnitEntity(
    @PrimaryKey
    val id: Int,
    val unit: Int,
    val type: Int,
    val multiple: Double = 0.0,
    var amount: Double = 0.0,
    val date: Date = Date()
) : Serializable {

    companion object {
        const val NAME = "length"
    }
}
