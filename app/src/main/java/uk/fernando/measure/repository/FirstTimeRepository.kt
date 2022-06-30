package uk.fernando.measure.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.measure.database.dao.UnitDao
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.enum.UnitMeasure
import uk.fernando.measure.enum.UnitType

class FirstTimeRepository(private val dao: UnitDao) {

    suspend fun setUpDatabase() = withContext(Dispatchers.IO) {
        dao.insertAll(lengthList)
        dao.insertAll(temperatureList)
        dao.insertAll(weightList)
    }

    private val lengthList by lazy {
        val unitType = UnitType.LENGTH.value
        listOf(
            LengthUnitEntity(1, UnitMeasure.KILOMETER.value, unitType, 1.0, 1.0),
            LengthUnitEntity(2, UnitMeasure.METER.value, unitType, 1000.0, 1000.0),
            LengthUnitEntity(7, UnitMeasure.MILE.value, unitType, 0.6213688756, 0.6213688756),
            LengthUnitEntity(9, UnitMeasure.FOOT.value, unitType, 3280.839895, 3280.839895),
            LengthUnitEntity(10, UnitMeasure.INCH.value, unitType, 39370.07874, 39370.07874),
        )
    }

    private val temperatureList by lazy {
        val unitType = UnitType.TEMPERATURE.value
        listOf(
            LengthUnitEntity(100, UnitMeasure.CELSIUS.value, unitType, 1.0, 1.0),
            LengthUnitEntity(101, UnitMeasure.KELVIN.value, unitType, 274.15, 274.15),
            LengthUnitEntity(102, UnitMeasure.FAHRENHEIT.value, unitType, 33.8, 33.8)
        )
    }

    private val weightList by lazy {
        val unitType = UnitType.WEIGHT.value
        listOf(
            LengthUnitEntity(200, UnitMeasure.KILOGRAM.value, unitType, 1.0, 1.0),
            LengthUnitEntity(201, UnitMeasure.GRAM.value, unitType, 1000.0, 1000.0),
            LengthUnitEntity(206, UnitMeasure.STONE.value, unitType, 0.157473, 0.157473),
            LengthUnitEntity(207, UnitMeasure.POUND.value, unitType, 2.2046244202, 2.2046244202),
            LengthUnitEntity(208, UnitMeasure.OUNCE.value, unitType, 35.273990723, 35.273990723),
        )
    }
}
