package uk.fernando.convert.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.convert.database.dao.UnitDao
import uk.fernando.convert.database.entity.UnitEntity
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
            UnitEntity(1, UnitMeasure.KILOMETER.value, unitType, 1.0, 1.0),
            UnitEntity(2, UnitMeasure.METER.value, unitType, 1000.0, 1000.0),
            UnitEntity(7, UnitMeasure.MILE.value, unitType, 0.6213688756, 0.6214),
            UnitEntity(9, UnitMeasure.FOOT.value, unitType, 3280.839895, 3280.8399)
        )
    }

    private val temperatureList by lazy {
        val unitType = UnitType.TEMPERATURE.value
        listOf(
            UnitEntity(100, UnitMeasure.CELSIUS.value, unitType, 1.0, 1.0),
            UnitEntity(101, UnitMeasure.KELVIN.value, unitType, 1.0, 274.15),
            UnitEntity(102, UnitMeasure.FAHRENHEIT.value, unitType, 1.8, 33.8)
        )
    }

    private val weightList by lazy {
        val unitType = UnitType.WEIGHT.value
        listOf(
            UnitEntity(200, UnitMeasure.KILOGRAM.value, unitType, 1.0, 1.0),
            UnitEntity(201, UnitMeasure.GRAM.value, unitType, 1000.0, 1000.0),
            UnitEntity(207, UnitMeasure.STONE.value, unitType, 0.157473, 0.1575),
            UnitEntity(208, UnitMeasure.POUND.value, unitType, 2.2046244202, 2.2047),
        )
    }

    private val volumeList by lazy {
        val unitType = UnitType.VOLUME.value
        listOf(
            UnitEntity(300, UnitMeasure.LITER.value, unitType, 1.0, 1.0),
            UnitEntity(301, UnitMeasure.CUBIC_METER.value, unitType, 0.001, 0.001),
            UnitEntity(310, UnitMeasure.US_GALLON.value, unitType, 0.2641721769, 0.2642),
            UnitEntity(322, UnitMeasure.IMPERIAL_TABLE_SPOON.value, unitType, 56.312127565, 56.3122),
        )
    }
}
