package com.theapache64.papercop.di.modules

import android.content.Context
import androidx.room.Room
import com.theapache64.papercop.data.local.PaperCopDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by theapache64 : Dec 10 Thu,2020 @ 11:36
 */
@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PaperCopDatabase {
        return Room.databaseBuilder(context, PaperCopDatabase::class.java, "papercop.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePlayersDao(paperCopDatabase: PaperCopDatabase) =
        paperCopDatabase.playersDao()
}