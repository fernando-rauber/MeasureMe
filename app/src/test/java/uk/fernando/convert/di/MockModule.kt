package uk.fernando.convert.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import io.mockk.mockk
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.bind
import org.koin.dsl.module
import uk.fernando.convert.database.MyDatabase
import uk.fernando.convert.database.dao.UnitDao
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.di.KoinModule.allModules
import uk.fernando.convert.datastore.PrefsStoreMock
import uk.fernando.convert.repository.UnitRepositoryMock

val mockModule = module {
    single { mockk<Application>() }
    single { mockk<Context>() }
    single<PrefsStore> { PrefsStoreMock() }
    single { Room.inMemoryDatabaseBuilder(get(), MyDatabase::class.java) }
    single { KoinModule.getAndroidLogger() }
    factory(qualifier = StringQualifier("common")) { mockk<SharedPreferences>() }
}

val mockedDAOModule = module {
    factory<UnitDao> { UnitRepositoryMock() } bind UnitDao::class
}


fun allMockedModules() = allModules() + mockModule + mockedDAOModule