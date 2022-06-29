package uk.fernando.measure.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.measure.database.dao.UnitDao
import uk.fernando.measure.database.entity.LengthUnitEntity

class AddUnitRepository(private val dao: UnitDao) {

    private val lengthUnits by lazy {
        listOf(
            LengthUnitEntity(1, "Kilometer", 1.0),
            LengthUnitEntity(2, "Meter", 1000.0),
            LengthUnitEntity(3, "Centimeter", 100000.0),
            LengthUnitEntity(4, "Millimeter", 10.0),
            LengthUnitEntity(5, "Micrometer", 10.0),
            LengthUnitEntity(6, "Nanometer", 10.0),
            LengthUnitEntity(7, "Mile", 1.609),
            LengthUnitEntity(8, "Yard", 1094.0),
            LengthUnitEntity(9, "Foot", 3281.0),
            LengthUnitEntity(10, "Inch", 39370.0),
            LengthUnitEntity(11, "Nautical mile", 1.852)
        )
    }

    suspend fun getLengthUnitList() = withContext(Dispatchers.IO) {
        val dbUnitIDList = dao.getUnitList().map { it.id }
        lengthUnits.filter { !dbUnitIDList.contains(it.id) }
    }

    suspend fun addUnit(unit: LengthUnitEntity) {
        withContext(Dispatchers.IO) {
            dao.insertUnit(unit)
        }
    }
}