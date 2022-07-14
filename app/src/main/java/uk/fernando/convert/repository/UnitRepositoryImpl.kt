package uk.fernando.convert.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.convert.database.dao.UnitDao
import uk.fernando.convert.database.entity.UnitEntity
import uk.fernando.convert.enum.UnitType

class UnitRepositoryImpl(private val dao: UnitDao) : UnitRepository {

    override suspend fun getUnitList(unit: UnitType) = withContext(Dispatchers.IO) {
        dao.getUnitList(unit.value)
    }

    override suspend fun updateAll(list: List<UnitEntity>) {
        withContext(Dispatchers.IO) {
            dao.updateAll(list)
        }
    }

    override suspend fun deleteUnit(unit: UnitEntity) {
        withContext(Dispatchers.IO) {
            dao.deleteUnit(unit)
        }
    }
}