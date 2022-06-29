package uk.fernando.measure.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.measure.database.dao.UnitDao
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.ext.roundOffDecimal

class AddUnitRepository(private val dao: UnitDao, private val prefs: PrefsStore) {

    private val lengthUnits by lazy {
        listOf(
            LengthUnitEntity(1, "Kilometer", 1, 1.0, 1.0),
            LengthUnitEntity(2, "Meter", 1, 1000.0, 1000.0),
            LengthUnitEntity(3, "Centimeter", 1, 100000.0, 100000.0),
            LengthUnitEntity(4, "Millimeter", 1, 1000000.0, 1000000.0),
            LengthUnitEntity(5, "Micrometer", 1, 1000000000.0, 1000000000.0),
            LengthUnitEntity(6, "Nanometer", 1, 1000000000000.0, 1000000000000.0),
            LengthUnitEntity(7, "Mile", 1, 1.609, 1.609),
            LengthUnitEntity(8, "Yard", 1, 1094.0, 1094.0),
            LengthUnitEntity(9, "Foot", 1, 3281.0, 3281.0),
            LengthUnitEntity(10, "Inch", 1, 39370.0, 39370.0),
            LengthUnitEntity(11, "Nautical mile", 1, 1.852, 1.852)
        )
    }

    suspend fun getLengthUnitList() = withContext(Dispatchers.IO) {
        val dbUnitIDList = dao.getUnitList().map { it.id }
        lengthUnits.filter { !dbUnitIDList.contains(it.id) }
    }

    suspend fun addUnitLength(unit: LengthUnitEntity) {
        withContext(Dispatchers.IO) {
            unit.amount = (unit.multiple * prefs.getLength()).roundOffDecimal()
            dao.insertUnit(unit)
        }
    }
}