package uk.fernando.measure.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.measure.database.dao.UnitDao
import uk.fernando.measure.database.entity.LengthUnitEntity

class FirstTimeRepository(private val dao: UnitDao) {

    suspend fun setUpDatabase() = withContext(Dispatchers.IO) {
        val list = mutableListOf<LengthUnitEntity>()

        list.add(LengthUnitEntity(1, "Kilometer", 1, 1.0, 1.0))
        list.add(LengthUnitEntity(2, "Meter", 1, 1000.0, 1000.0))
        list.add(LengthUnitEntity(3, "Centimeter", 1, 100000.0, 100000.0))
        list.add(LengthUnitEntity(7, "Mile", 1, 1.609, 1.609))
        list.add(LengthUnitEntity(9, "Foot", 1, 3281.0, 3281.0))
        list.add(LengthUnitEntity(10, "Inch", 1, 39370.0, 39370.0))

        dao.insertAll(list)
    }
}
