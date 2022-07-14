package uk.fernando.convert.repository

import uk.fernando.convert.database.entity.UnitEntity
import uk.fernando.convert.enum.UnitMeasure
import uk.fernando.convert.enum.UnitType

open class AddUnitRepositoryMock : AddUnitRepository {

    companion object {
        private val lengthUnits by lazy {
            listOf(
                UnitEntity(1, UnitMeasure.KILOMETER.value, UnitType.LENGTH.value, 1.0, 1.0),
                UnitEntity(2, UnitMeasure.METER.value, UnitType.LENGTH.value, 1000.0, 1000.0),

                UnitEntity(3, UnitMeasure.KILOGRAM.value, UnitType.WEIGHT.value, 100000.0, 100000.0),
                UnitEntity(4, UnitMeasure.GRAM.value, UnitType.WEIGHT.value, 1000000.0, 1000000.0),

                UnitEntity(100, UnitMeasure.CELSIUS.value, UnitType.TEMPERATURE.value, 1.0, 1.0),
                UnitEntity(101, UnitMeasure.KELVIN.value, UnitType.TEMPERATURE.value, 1.0, 274.15),
                UnitEntity(102, UnitMeasure.FAHRENHEIT.value, UnitType.TEMPERATURE.value, 1.8, 33.8)
            )
        }
    }

    override suspend fun getUnitIDListByType(type: Int): List<Int> {
        return lengthUnits.map { it.id }
    }

    override suspend fun insertUnit(unit: UnitEntity) {

    }

}

