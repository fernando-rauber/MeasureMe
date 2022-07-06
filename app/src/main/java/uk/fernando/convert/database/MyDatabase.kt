package uk.fernando.convert.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uk.fernando.convert.database.converter.DateTypeConverter
import uk.fernando.convert.database.dao.UnitDao
import uk.fernando.convert.database.entity.LengthUnitEntity

@TypeConverters(DateTypeConverter::class)
@Database(
    version = DATABASE_VERSION,
    exportSchema = false,
    entities = [LengthUnitEntity::class]
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun unitDao(): UnitDao
}

const val DATABASE_VERSION = 1
