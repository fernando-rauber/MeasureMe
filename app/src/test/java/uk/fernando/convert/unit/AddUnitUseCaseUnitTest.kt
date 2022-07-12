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
import uk.fernando.convert.di.allMockedModules
import uk.fernando.convert.enum.UnitType
import uk.fernando.convert.usecase.AddUnitUseCase
import uk.fernando.convert.util.Resource
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AddUnitUseCaseUnitTest : KoinTestCase() {

    private val useCase: AddUnitUseCase by inject()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    override val koinRule = KoinTestRule.create {
        modules(allMockedModules())
    }

    @Test
    fun `return success list of units by type - LENGTH`() = runTest {

        useCase.getAvailableUnitList(UnitType.LENGTH.value).test {
            assertTrue { awaitItem() is Resource.Loading }
            assertTrue { awaitItem() is Resource.Loading }

            val result = awaitItem() // Success

            assertTrue { result is Resource.Success }
            assertTrue { result.data?.isNotEmpty() ?: false }

            awaitComplete()
        }
    }

    @Test
    fun `return success list of units by type - Weight`() = runTest {

        useCase.getAvailableUnitList(UnitType.WEIGHT.value).test {
            assertTrue { awaitItem() is Resource.Loading }
            assertTrue { awaitItem() is Resource.Loading }

            val result = awaitItem() // Success

            assertTrue { result is Resource.Success }
            assertTrue { result.data?.isNotEmpty() ?: false }

            awaitComplete()
        }
    }

    @Test
    fun `return success with empty list of units for type - TEMPERATURE`() = runTest {

        useCase.getAvailableUnitList(UnitType.TEMPERATURE.value).test {
            assertTrue { awaitItem() is Resource.Loading }
            assertTrue { awaitItem() is Resource.Loading }

            val result = awaitItem() // Success but empty list

            assertTrue { result is Resource.Success }
            assertTrue { result.data?.isEmpty() ?: true }

            awaitComplete()
        }
    }
}