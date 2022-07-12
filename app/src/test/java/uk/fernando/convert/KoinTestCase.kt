package uk.fernando.convert

import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class KoinTestCase : KoinTest {
    @get:Rule
    abstract val koinRule: KoinTestRule

//    @get:Rule
//    open val mockProvider = MockProviderRule.create { clazz ->
//        mockkClass(clazz)
//    }
//
//    @Rule
//    @JvmField
//    val rule = InstantTaskExecutorRule()
//
//    protected fun freeUpModules() {
//        unloadKoinModules(allMockedModules())
//    }
}