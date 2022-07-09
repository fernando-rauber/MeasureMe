package uk.fernando.convert.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.convert.database.dao.UnitDao
import uk.fernando.convert.database.entity.UnitEntity
import uk.fernando.convert.enum.UnitType

class UnitRepository(private val dao: UnitDao) {

    suspend fun getUnitList(unit: UnitType) = withContext(Dispatchers.IO) {
        dao.getUnitList(unit.value)
    }

    suspend fun updateAll(list: List<UnitEntity>) {
        withContext(Dispatchers.IO) {
            dao.updateAll(list)
        }
    }

    suspend fun deleteUnit(unit: UnitEntity) {
        withContext(Dispatchers.IO) {
            dao.deleteUnit(unit)
        }
    }
}