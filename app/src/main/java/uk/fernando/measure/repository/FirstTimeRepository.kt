package uk.fernando.measure.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.measure.database.dao.UnitDao
import uk.fernando.measure.database.entity.LengthUnitEntity

class FirstTimeRepository(private val dao: UnitDao) {

    suspend fun setUpDatabase() = withContext(Dispatchers.IO) {
        val list = mutableListOf<LengthUnitEntity>()

        list.add(LengthUnitEntity(1, "Kilometer", 1.0))
        list.add(LengthUnitEntity(2, "Meter", 1000.0))
        list.add(LengthUnitEntity(3, "Centimeter", 100000.0))
//        list.add(LengthUnitEntity(4, "Millimeter", 10.0))
//        list.add(LengthUnitEntity(5, "Micrometer", 10.0))
//        list.add(LengthUnitEntity(6, "Nanometer", 10.0))
        list.add(LengthUnitEntity(7, "Mile", 1.609))
        list.add(LengthUnitEntity(8, "Yard", 1094.0))
        list.add(LengthUnitEntity(9, "Foot", 3281.0))
        list.add(LengthUnitEntity(10, "Inch", 39370.0))
        list.add(LengthUnitEntity(11, "Nautical mile", 1.852))

        dao.insertAll(list)
    }
}
