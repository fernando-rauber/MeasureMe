package uk.fernando.convert.unit

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTestRule
import org.koin.test.inject
import uk.fernando.convert.KoinTestCase
import uk.fernando.convert.MainCoroutineRule
import uk.fernando.convert.database.entity.UnitEntity
import uk.fernando.convert.di.allMockedModules
import uk.fernando.convert.enum.UnitMeasure
import uk.fernando.convert.enum.UnitType
import uk.fernando.convert.usecase.GetUnitsUseCase
import uk.fernando.convert.util.Resource
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetUnitUseCaseUnitTest : KoinTestCase() {

    private val useCase: GetUnitsUseCase by inject()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    override val koinRule = KoinTestRule.create {
        modules(allMockedModules())
    }

    @Test
    fun `return success list of units by type - LENGTH`() = runTest {

        useCase.getUnitsByType(UnitType.LENGTH).test {
            assertTrue { awaitItem() is Resource.Loading }
            assertTrue { awaitItem() is Resource.Loading }

            val result = awaitItem() // Success

            assertTrue { result is Resource.Success }
            assertTrue { result.data?.isNotEmpty() ?: false }

            awaitComplete()
        }
    }

    @Test
    fun `return success list of units by type - WEIGHT`() = runTest {

        useCase.getUnitsByType(UnitType.WEIGHT).test {
            assertTrue { awaitItem() is Resource.Loading }
            assertTrue { awaitItem() is Resource.Loading }

            val result = awaitItem() // Success

            assertTrue { result is Resource.Success }
            assertTrue { result.data?.isNotEmpty() ?: false }

            awaitComplete()
        }
    }

    @Test
    fun `return success but empty list of units - Volume`() = runTest {

        useCase.getUnitsByType(UnitType.VOLUME).test {
            assertTrue { awaitItem() is Resource.Loading }
            assertTrue { awaitItem() is Resource.Loading }

            val result = awaitItem() // Success

            assertTrue { result is Resource.Success }
            assertTrue { result.data?.isEmpty() ?: false }

            awaitComplete()
        }
    }

    @Test
    fun `return success but empty list of units - Temperature`() = runTest {

        useCase.getUnitsByType(UnitType.TEMPERATURE).test {
            assertTrue { awaitItem() is Resource.Loading }
            assertTrue { awaitItem() is Resource.Loading }

            val result = awaitItem() // Success

            assertTrue { result is Resource.Success }
            assertTrue { result.data?.isNotEmpty() ?: false }

            awaitComplete()
        }
    }

    @Test
    fun `update success Length Amount`() = runTest {
        val baseUnit = UnitEntity(1, UnitMeasure.KILOMETER.value, UnitType.LENGTH.value, 1.0, 3.0)
        val unitList = listOf(
            UnitEntity(1, UnitMeasure.KILOMETER.value, UnitType.LENGTH.value, 1.0, 1.0),
            UnitEntity(2, UnitMeasure.METER.value, UnitType.LENGTH.value, 1000.0, 1000.0)
        )

        val newList = useCase.updateAmount(baseUnit, unitList)

        assertEquals(3.0, newList[0].amount)
        assertEquals(3000.0, newList[1].amount)
    }

    @Test
    fun `update fail Length Amount`() = runTest {
        val baseUnit = UnitEntity(1, UnitMeasure.KILOMETER.value, UnitType.LENGTH.value, 1.0, 3.0)
        val unitList = listOf(
            UnitEntity(1, UnitMeasure.KILOMETER.value, UnitType.LENGTH.value, 1.0, 1.0),
            UnitEntity(2, UnitMeasure.METER.value, UnitType.LENGTH.value, 1000.0, 1000.0)
        )

        val newList = useCase.updateAmount(baseUnit, unitList)

        assertNotEquals(1.0, newList[0].amount)
        assertNotEquals(1.0, newList[1].amount)
    }

    @Test
    fun `update success Temperature Amount`() = runTest {
        val baseUnit = UnitEntity(1, UnitMeasure.CELSIUS.value, UnitType.TEMPERATURE.value, 1.0, 40.0)
        val unitList = listOf(
            UnitEntity(100, UnitMeasure.CELSIUS.value, UnitType.TEMPERATURE.value, 1.0, 1.0),
            UnitEntity(101, UnitMeasure.KELVIN.value, UnitType.TEMPERATURE.value, 1.0, 274.15),
            UnitEntity(102, UnitMeasure.FAHRENHEIT.value, UnitType.TEMPERATURE.value, 1.8, 33.8)
        )

        val newList = useCase.updateAmount(baseUnit, unitList)

        assertEquals(40.0, newList[0].amount)
        assertEquals(313.15, newList[1].amount)
        assertEquals(104.0, newList[2].amount)
    }
}