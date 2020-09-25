package com.hardikmahant.recurringtask.di

import android.content.Context
import androidx.room.Room
import com.hardikmahant.recurringtask.repository.LogRepository
import com.hardikmahant.recurringtask.repository.local.LogDB
import com.hardikmahant.recurringtask.repository.local.LogDao
import com.hardikmahant.recurringtask.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLogDB(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, LogDB::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideLogDao(
        db: LogDB
    ) = db.logDao()

    @Singleton
    @Provides
    fun provideLogRepository(
        logDao: LogDao
    ) = LogRepository(logDao)

}