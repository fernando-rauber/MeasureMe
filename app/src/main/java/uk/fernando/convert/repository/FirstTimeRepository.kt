package uk.fernando.convert.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.convert.database.dao.UnitDao
import uk.fernando.convert.database.entity.LengthUnitEntity
import uk.fernando.convert.enum.UnitMeasure
import uk.fernando.convert.enum.UnitType
import uk.fernando.convert.ext.TAG
import uk.fernando.logger.MyLogger

class FirstTimeRepository(private val dao: UnitDao, private val logger: MyLogger) {

    suspend fun setUpDatabase() = withContext(Dispatchers.IO) {
        runCatching {
            dao.insertAll(lengthList)
            dao.insertAll(temperatureList)
            dao.insertAll(weightList)
            dao.insertAll(volumeList)
        }.onFailure { e ->
            logger.e(TAG, e.message.toString())
            logger.addExceptionToCrashlytics(e)
        }
    }

    private val lengthList by lazy {
        val unitType = UnitType.LENGTH.value
        listOf(
            LengthUnitEntity(1, UnitMeasure.KILOMETER.value, unitType, 1.0, 1.0),
            LengthUnitEntity(2, UnitMeasure.METER.value, unitType, 1000.0, 1000.0),
            LengthUnitEntity(7, UnitMeasure.MILE.value, unitType, 0.6213688756, 0.6213688756),
            LengthUnitEntity(9, UnitMeasure.FOOT.value, unitType, 3280.839895, 3280.839895),
        )
    }

    private val temperatureList by lazy {
        val unitType = UnitType.TEMPERATURE.value
        listOf(
            LengthUnitEntity(100, UnitMeasure.CELSIUS.value, unitType, 1.0, 1.0),
            LengthUnitEntity(101, UnitMeasure.KELVIN.value, unitType, 1.0, 274.15),
            LengthUnitEntity(102, UnitMeasure.FAHRENHEIT.value, unitType, 1.8, 33.8)
        )
    }

    private val weightList by lazy {
        val unitType = UnitType.WEIGHT.value
        listOf(
            LengthUnitEntity(200, UnitMeasure.KILOGRAM.value, unitType, 1.0, 1.0),
            LengthUnitEntity(201, UnitMeasure.GRAM.value, unitType, 1000.0, 1000.0),
            LengthUnitEntity(206, UnitMeasure.STONE.value, unitType, 0.157473, 0.157473),
            LengthUnitEntity(207, UnitMeasure.POUND.value, unitType, 2.2046244202, 2.2046244202),
        )
    }

    private val volumeList by lazy {
        val unitType = UnitType.VOLUME.value
        listOf(
            LengthUnitEntity(300, UnitMeasure.LITER.value, unitType, 1.0, 1.0),
            LengthUnitEntity(301, UnitMeasure.CUBIC_METER.value, unitType, 0.001, 0.001),
            LengthUnitEntity(310, UnitMeasure.US_GALLON.value, unitType, 0.2641721769, 0.2641721769),
            LengthUnitEntity(322, UnitMeasure.IMPERIAL_TABLE_SPOON.value, unitType, 56.312127565, 56.312127565),
        )
    }
}
