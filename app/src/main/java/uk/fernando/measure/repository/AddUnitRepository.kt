package uk.fernando.measure.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.measure.database.dao.UnitDao
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.enum.UnitMeasure.*
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.ext.roundOffDecimal

class AddUnitRepository(private val dao: UnitDao, private val prefs: PrefsStore) {

    private val lengthUnits by lazy {
        val unitType = UnitType.LENGTH.value
        listOf(
            LengthUnitEntity(1, KILOMETER.value, unitType, 1.0, 1.0),
            LengthUnitEntity(2, METER.value, unitType, 1000.0, 1000.0),
            LengthUnitEntity(3, CENTIMETER.value, unitType, 100000.0, 100000.0),
            LengthUnitEntity(4, MILLIMETER.value, unitType, 1000000.0, 1000000.0),
            LengthUnitEntity(5, MICROMETER.value, unitType, 1000000000.0, 1000000000.0),
            LengthUnitEntity(6, NANOMETER.value, unitType, 1000000000000.0, 1000000000000.0),
            LengthUnitEntity(7, MILE.value, unitType, 0.6213688756, 0.6213688756),
            LengthUnitEntity(8, YARD.value, unitType, 1093.6132983, 1093.6132983),
            LengthUnitEntity(9, FOOT.value, unitType, 3280.839895, 3280.839895),
            LengthUnitEntity(10, INCH.value, unitType, 39370.07874, 39370.07874),
            LengthUnitEntity(11, NAUTICAL_MILE.value, unitType, 1.852, 1.852)
        )
    }

    private val temperatureUnits by lazy {
        val unitType = UnitType.TEMPERATURE.value
        listOf(
            LengthUnitEntity(100, CELSIUS.value, unitType, 1.0, 1.0),
            LengthUnitEntity(101, KELVIN.value, unitType, 274.15, 274.15),
            LengthUnitEntity(102, FAHRENHEIT.value, unitType, 33.8, 33.8)
        )
    }

    private val weightUnits by lazy {
        val unitType = UnitType.WEIGHT.value
        listOf(
            LengthUnitEntity(200, KILOGRAM.value, unitType, 1.0, 1.0),
            LengthUnitEntity(201, GRAM.value, unitType, 1000.0, 1000.0),
            LengthUnitEntity(202, MILLIGRAM.value, unitType, 1000000.0, 1000000.0),
            LengthUnitEntity(203, MICROGRAM.value, unitType, 1e+9, 1e+9),
            LengthUnitEntity(204, IMPERIAL_TON.value, unitType, 0.000984207, 0.000984207),
            LengthUnitEntity(205, US_TON.value, unitType, 0.00110231, 0.00110231),
            LengthUnitEntity(206, STONE.value, unitType, 0.157473, 0.157473),
            LengthUnitEntity(207, POUND.value, unitType, 2.2046244202, 2.2046244202),
            LengthUnitEntity(208, OUNCE.value, unitType, 35.273990723, 35.273990723),
            LengthUnitEntity(209, CARAT.value, unitType, 5000.0, 5000.0)
        )
    }

    suspend fun getUnitList(type: Int) = withContext(Dispatchers.IO) {
        val dbUnitIDList = dao.getUnitIDList(type)
        when (UnitType.getByValue(type)) {
            UnitType.LENGTH -> lengthUnits.filter { !dbUnitIDList.contains(it.id) }
            UnitType.WEIGHT -> weightUnits.filter { !dbUnitIDList.contains(it.id) }
            UnitType.TEMPERATURE -> temperatureUnits.filter { !dbUnitIDList.contains(it.id) }
            else -> weightUnits.filter { !dbUnitIDList.contains(it.id) }
        }
    }

    suspend fun insertUnit(unit: LengthUnitEntity) {
        withContext(Dispatchers.IO) {
            val storedValue = when (UnitType.getByValue(unit.type)) {
                UnitType.LENGTH -> prefs.getLength()
                UnitType.TEMPERATURE -> prefs.getLength()
                UnitType.WEIGHT -> prefs.getWeight()
                else -> prefs.getVolume()
            }

            unit.amount = (unit.multiple * storedValue).roundOffDecimal()
            dao.insertUnit(unit)
        }
    }
}