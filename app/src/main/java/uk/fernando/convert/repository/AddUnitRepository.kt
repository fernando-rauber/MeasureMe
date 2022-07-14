package uk.fernando.convert.repository

import uk.fernando.convert.database.entity.UnitEntity

interface AddUnitRepository {

    suspend fun getUnitIDListByType(type: Int): List<Int>

    suspend fun insertUnit(unit: UnitEntity)
}