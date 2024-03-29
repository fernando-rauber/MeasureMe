package uk.fernando.convert.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.convert.database.dao.UnitDao
import uk.fernando.convert.database.entity.UnitEntity

class AddUnitRepositoryImpl(private val dao: UnitDao) : AddUnitRepository {

    override suspend fun getUnitIDListByType(type: Int) = withContext(Dispatchers.IO) {
        dao.getUnitIDListByType(type)
    }

    override suspend fun insertUnit(unit: UnitEntity) {
        withContext(Dispatchers.IO) {
            dao.insertUnit(unit)
        }
    }
}