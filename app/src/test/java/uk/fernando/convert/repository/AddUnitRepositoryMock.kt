package uk.fernando.convert.repository

import uk.fernando.convert.database.dao.UnitDao
import uk.fernando.convert.database.entity.UnitEntity
import uk.fernando.convert.enum.UnitMeasure
import uk.fernando.convert.enum.UnitType

open class AddUnitRepositoryMock : UnitDao {

    companion object {
        private val lengthUnits by lazy {
            listOf(
                UnitEntity(1, UnitMeasure.KILOMETER.value, UnitType.LENGTH.value, 1.0, 1.0),
                UnitEntity(2, UnitMeasure.METER.value, UnitType.LENGTH.value, 1000.0, 1000.0),

                UnitEntity(3, UnitMeasure.KILOGRAM.value, UnitType.WEIGHT.value, 100000.0, 100000.0),
                UnitEntity(4, UnitMeasure.GRAM.value, UnitType.WEIGHT.value, 1000000.0, 1000000.0),
            )
        }
    }

    override fun getUnitList(type: Int): List<UnitEntity> {
        return lengthUnits.filter { it.type == type }
    }

    override fun getUnitIDListByType(type: Int): List<Int> {
        return lengthUnits.map { it.id }
    }

    override fun updateAll(list: List<UnitEntity>) {

    }

    override fun insertAll(list: List<UnitEntity>) {

    }

    override fun insertUnit(unit: UnitEntity) {

    }

    override fun deleteUnit(unit: UnitEntity) {

    }

}

