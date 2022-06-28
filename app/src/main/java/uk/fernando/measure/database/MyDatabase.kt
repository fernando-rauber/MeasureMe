package uk.fernando.measure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.fernando.measure.database.dao.UnitDao
import uk.fernando.measure.database.entity.LengthUnitEntity


@Database(
    version = DATABASE_VERSION,
    exportSchema = false,
    entities = [LengthUnitEntity::class]
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun unitDao(): UnitDao
}

const val DATABASE_VERSION = 1