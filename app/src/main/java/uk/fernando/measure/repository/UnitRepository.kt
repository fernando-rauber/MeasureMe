package uk.fernando.measure.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.measure.database.dao.UnitDao
import uk.fernando.measure.database.entity.LengthUnitEntity

class UnitRepository(private val dao: UnitDao) {

    suspend fun getUnitList() = withContext(Dispatchers.IO) {
        dao.getUnitList()
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