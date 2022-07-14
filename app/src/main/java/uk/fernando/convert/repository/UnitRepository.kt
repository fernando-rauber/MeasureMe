package uk.fernando.convert.repository

import uk.fernando.convert.database.entity.UnitEntity
import uk.fernando.convert.enum.UnitType

interface UnitRepository {

    suspend fun getUnitList(unit: UnitType): List<UnitEntity>

    suspend fun updateAll(list: List<UnitEntity>)

    suspend fun deleteUnit(unit: UnitEntity)
}