package uk.fernando.measure.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.measure.database.dao.UnitDao
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.enum.UnitType

class UnitRepository(private val dao: UnitDao) {

    suspend fun getUnitList(unit: UnitType) = withContext(Dispatchers.IO) {
        dao.getUnitList(unit.value)
    }

    suspend fun updateAll(list: List<LengthUnitEntity>) {
        withContext(Dispatchers.IO) {
            dao.updateAll(list)
        }
    }

    suspend fun deleteUnit(unit: LengthUnitEntity) {
        withContext(Dispatchers.IO) {
            dao.deleteUnit(unit)
        }
    }
}