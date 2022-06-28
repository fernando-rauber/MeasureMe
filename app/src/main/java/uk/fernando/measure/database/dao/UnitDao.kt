package uk.fernando.measure.database.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uk.fernando.measure.database.entity.LengthUnitEntity

@Dao
interface UnitDao {

    @Query("SELECT * FROM ${LengthUnitEntity.NAME} ")
    fun getUnitList(playerID: Long): Flow<List<LengthUnitEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(list: List<LengthUnitEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<LengthUnitEntity>)

}