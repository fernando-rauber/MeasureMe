package uk.fernando.convert.database.dao

import androidx.room.*
import uk.fernando.convert.database.entity.LengthUnitEntity

@Dao
interface UnitDao {

    @Query("SELECT * FROM ${LengthUnitEntity.NAME} WHERE type = :type")
    fun getUnitList(type: Int): List<LengthUnitEntity>

    @Query("SELECT id FROM ${LengthUnitEntity.NAME} WHERE type = :type ")
    fun getUnitIDListByType(type: Int): List<Int>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(list: List<LengthUnitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<LengthUnitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUnit(unit: LengthUnitEntity)

    @Delete
    fun deleteUnit(unit: LengthUnitEntity)

}