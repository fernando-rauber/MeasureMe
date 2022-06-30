package uk.fernando.measure.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.enum.UnitMeasure
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.ext.roundOffDecimal
import uk.fernando.measure.repository.AddUnitRepository

class AddUnitUseCase(private val repository: AddUnitRepository, private val prefs: PrefsStore) {

    suspend fun getAvailableUnitList(type: Int): List<LengthUnitEntity> {
        val dbUnitIDList = repository.getUnitIDListByType(type)

        return when (UnitType.getByValue(type)) {
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
                UnitType.TEMPERATURE -> prefs.getTemperature()
                UnitType.WEIGHT -> prefs.getWeight()
                else -> prefs.getVolume()
            }

            val add = when (UnitMeasure.getByValue(unit.unit)) {
                UnitMeasure.FAHRENHEIT -> 32.0
                UnitMeasure.KELVIN -> 273.15
                else -> 0.0
            }

            unit.amount = ((unit.multiple * storedValue) + add).roundOffDecimal()

            repository.insertUnit(unit)
        }
    }

    private val lengthUnits by lazy {
        val unitType = UnitType.LENGTH.value
        listOf(
            LengthUnitEntity(1, UnitMeasure.KILOMETER.value, unitType, 1.0, 1.0),
            LengthUnitEntity(2, UnitMeasure.METER.value, unitType, 1000.0, 1000.0),
            LengthUnitEntity(3, UnitMeasure.CENTIMETER.value, unitType, 100000.0, 100000.0),
            LengthUnitEntity(4, UnitMeasure.MILLIMETER.value, unitType, 1000000.0, 1000000.0),
            LengthUnitEntity(5, UnitMeasure.MICROMETER.value, unitType, 1000000000.0, 1000000000.0),
            LengthUnitEntity(6, UnitMeasure.NANOMETER.value, unitType, 1000000000000.0, 1000000000000.0),
            LengthUnitEntity(7, UnitMeasure.MILE.value, unitType, 0.6213688756, 0.6213688756),
            LengthUnitEntity(8, UnitMeasure.YARD.value, unitType, 1093.6132983, 1093.6132983),
            LengthUnitEntity(9, UnitMeasure.FOOT.value, unitType, 3280.839895, 3280.839895),
            LengthUnitEntity(10, UnitMeasure.INCH.value, unitType, 39370.07874, 39370.07874),
            LengthUnitEntity(11, UnitMeasure.NAUTICAL_MILE.value, unitType, 1.852, 1.852)
        )
    }

    private val temperatureUnits by lazy {
        val unitType = UnitType.TEMPERATURE.value
        listOf(
            LengthUnitEntity(100, UnitMeasure.CELSIUS.value, unitType, 1.0, 1.0),
            LengthUnitEntity(101, UnitMeasure.KELVIN.value, unitType, 1.0, 274.15),
            LengthUnitEntity(102, UnitMeasure.FAHRENHEIT.value, unitType, 1.8, 33.8)
        )
    }

    private val weightUnits by lazy {
        val unitType = UnitType.WEIGHT.value
        listOf(
            LengthUnitEntity(200, UnitMeasure.KILOGRAM.value, unitType, 1.0, 1.0),
            LengthUnitEntity(201, UnitMeasure.GRAM.value, unitType, 1000.0, 1000.0),
            LengthUnitEntity(202, UnitMeasure.MILLIGRAM.value, unitType, 1000000.0, 1000000.0),
            LengthUnitEntity(203, UnitMeasure.MICROGRAM.value, unitType, 1e+9, 1e+9),
            LengthUnitEntity(204, UnitMeasure.IMPERIAL_TON.value, unitType, 0.000984207, 0.000984207),
            LengthUnitEntity(205, UnitMeasure.US_TON.value, unitType, 0.00110231, 0.00110231),
            LengthUnitEntity(206, UnitMeasure.STONE.value, unitType, 0.157473, 0.157473),
            LengthUnitEntity(207, UnitMeasure.POUND.value, unitType, 2.2046244202, 2.2046244202),
            LengthUnitEntity(208, UnitMeasure.OUNCE.value, unitType, 35.273990723, 35.273990723),
            LengthUnitEntity(209, UnitMeasure.CARAT.value, unitType, 5000.0, 5000.0)
        )
    }
}