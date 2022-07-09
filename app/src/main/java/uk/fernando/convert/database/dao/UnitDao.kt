package uk.fernando.convert.database.dao

import androidx.room.*
import uk.fernando.convert.database.entity.UnitEntity

@Dao
interface UnitDao {

    @Query("SELECT * FROM ${UnitEntity.NAME} WHERE type = :type")
    fun getUnitList(type: Int): List<UnitEntity>

    @Query("SELECT id FROM ${UnitEntity.NAME} WHERE type = :type ")
    fun getUnitIDListByType(type: Int): List<Int>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(list: List<UnitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<UnitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUnit(unit: UnitEntity)

    @Delete
    fun deleteUnit(unit: UnitEntity)

}