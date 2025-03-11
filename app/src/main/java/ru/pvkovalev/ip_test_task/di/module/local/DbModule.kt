package ru.pvkovalev.ip_test_task.di.module.local

import android.app.Application
import ru.pvkovalev.ip_test_task.domain.repository.DbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.pvkovalev.ip_test_task.data.local.AppDatabase
import ru.pvkovalev.ip_test_task.data.local.ItemsDao
import ru.pvkovalev.ip_test_task.data.local.mapper.Mapper
import ru.pvkovalev.ip_test_task.data.local.repository.DbRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun providesRepository(
        itemsDao: ItemsDao,
        mapper: Mapper
    ): DbRepository =
        DbRepositoryImpl(itemsDao = itemsDao, mapper = mapper)

    @Singleton
    @Provides
    fun providesDao(
        application: Application
    ): ItemsDao {
        return AppDatabase.getInstance(application).ipTestTaskDao()
    }
}