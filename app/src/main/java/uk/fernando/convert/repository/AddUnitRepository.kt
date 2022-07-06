package uk.fernando.convert.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.convert.database.dao.UnitDao
import uk.fernando.convert.database.entity.LengthUnitEntity

class AddUnitRepository(private val dao: UnitDao) {

    suspend fun getUnitIDListByType(type: Int) = withContext(Dispatchers.IO) {
        dao.getUnitIDListByType(type)
    }

    suspend fun insertUnit(unit: LengthUnitEntity) {
        withContext(Dispatchers.IO) {
            dao.insertUnit(unit)
        }
    }
}