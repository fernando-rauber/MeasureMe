package uk.fernando.measure.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uk.fernando.measure.database.entity.LengthUnitEntity

@Dao
interface UnitDao {

    @Query("SELECT * FROM ${LengthUnitEntity.NAME} ")
    fun getUnitList(): List<LengthUnitEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(list: List<LengthUnitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<LengthUnitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUnit(unit: LengthUnitEntity)

    @Delete
    fun deleteUnit(unit: LengthUnitEntity)

}