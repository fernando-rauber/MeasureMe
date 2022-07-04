package uk.fernando.measure.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import uk.fernando.measure.database.entity.LengthUnitEntity
import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.enum.UnitMeasure
import uk.fernando.measure.enum.UnitType
import uk.fernando.measure.ext.getPatterDecimalFormat
import uk.fernando.measure.ext.roundOffDecimal
import uk.fernando.measure.repository.AddUnitRepository
import uk.fernando.measure.util.Resource

class AddUnitUseCase(private val repository: AddUnitRepository, private val prefs: PrefsStore) {

    suspend fun getAvailableUnitList(type: Int): Flow<Resource<List<LengthUnitEntity>>> = flow {
        try {
            emit(Resource.Loading(true))
            val dbUnitIDList = repository.getUnitIDListByType(type)

            val availableUnits = when (UnitType.getByValue(type)) {
                UnitType.LENGTH -> lengthUnits.filter { !dbUnitIDList.contains(it.id) }
                UnitType.WEIGHT -> weightUnits.filter { !dbUnitIDList.contains(it.id) }
                UnitType.TEMPERATURE -> temperatureUnits.filter { !dbUnitIDList.contains(it.id) }
                else -> volumeUnits.filter { !dbUnitIDList.contains(it.id) }
            }

            emit(Resource.Success(availableUnits))
            emit(Resource.Loading(false))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            emit(Resource.Loading(false))
        }
    }

    suspend fun insertUnit(unit: LengthUnitEntity) {
        withContext(Dispatchers.IO) {

            val baseStoredValue = when (UnitType.getByValue(unit.type)) {
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

            val newAmount = ((unit.multiple * baseStoredValue) + add)

            unit.amount = newAmount.roundOffDecimal(unit.type.getPatterDecimalFormat())

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

    private val volumeUnits by lazy {
        val unitType = UnitType.VOLUME.value
        listOf(
            LengthUnitEntity(300, UnitMeasure.LITER.value, unitType, 1.0, 1.0),
            LengthUnitEntity(301, UnitMeasure.CUBIC_METER.value, unitType, 0.001, 0.001),
//            LengthUnitEntity(301, UnitMeasure.CUBIC_KILOMETER.value, unitType, 0.001, 0.001),
            LengthUnitEntity(303, UnitMeasure.CUBIC_CENTIMETER.value, unitType, 1000.0, 1000.0),
            LengthUnitEntity(304, UnitMeasure.CUBIC_MILLIMETER.value, unitType, 1000000.0, 1000000.0),
//            LengthUnitEntity(305, UnitMeasure.CUBIC_MILE.value, unitType, 0.00110231, 0.00110231),
            LengthUnitEntity(306, UnitMeasure.CUBIC_YARD.value, unitType, 0.0013079506, 0.0013079506),
            LengthUnitEntity(307, UnitMeasure.CUBIC_FOOT.value, unitType, 0.0353146667, 0.0353146667),
            LengthUnitEntity(308, UnitMeasure.CUBIC_INCH.value, unitType, 61.023744095, 61.023744095),
            LengthUnitEntity(309, UnitMeasure.MILLILITER.value, unitType, 1000.0, 1000.0),
            LengthUnitEntity(310, UnitMeasure.US_GALLON.value, unitType, 0.2641721769, 0.2641721769),
            LengthUnitEntity(311, UnitMeasure.US_QUART.value, unitType, 1.0566887074, 1.0566887074),
            LengthUnitEntity(312, UnitMeasure.US_PINT.value, unitType, 2.1133774149, 2.1133774149),
            LengthUnitEntity(313, UnitMeasure.US_CUP.value, unitType, 4.2267548297, 4.2267548297),
            LengthUnitEntity(314, UnitMeasure.US_FLUID_OUNCE.value, unitType, 33.814038638, 33.814038638),
            LengthUnitEntity(315, UnitMeasure.US_TABLE_SPOON.value, unitType, 67.628077276, 67.628077276),
            LengthUnitEntity(316, UnitMeasure.US_TEA_SPOON.value, unitType, 202.88423183, 202.88423183),
            LengthUnitEntity(317, UnitMeasure.IMPERIAL_GALLON.value, unitType, 0.2199692483, 0.2199692483),
            LengthUnitEntity(318, UnitMeasure.IMPERIAL_QUART.value, unitType, 0.8798769932, 0.8798769932),
            LengthUnitEntity(319, UnitMeasure.IMPERIAL_PINT.value, unitType, 1.7597539864, 1.7597539864),
            LengthUnitEntity(320, UnitMeasure.IMPERIAL_CUP.value, unitType, 3.51951, 3.51951),
            LengthUnitEntity(321, UnitMeasure.IMPERIAL_FLUID_OUNCE.value, unitType, 35.195079728, 35.195079728),
            LengthUnitEntity(322, UnitMeasure.IMPERIAL_TABLE_SPOON.value, unitType, 56.312127565, 56.312127565),
            LengthUnitEntity(323, UnitMeasure.IMPERIAL_TEA_SPOON.value, unitType, 168.93638269, 168.93638269)
        )
    }
}