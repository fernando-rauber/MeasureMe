package uk.fernando.measure.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.measure.database.dao.UnitDao
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.enum.UnitMeasure.*
import uk.fernando.measure.enum.UnitType

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