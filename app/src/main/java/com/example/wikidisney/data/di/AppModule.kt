package com.example.wikidisney.data.di

import android.app.Application
import androidx.room.Room
import com.example.wikidisney.common.Constants
import com.example.wikidisney.data.database.CharacterDatabase
import com.example.wikidisney.data.database.dao.CharacterDao
import com.example.wikidisney.data.remote.DisneyApi
import com.example.wikidisney.data.repository.CharacterRepositoryImpl
import com.example.wikidisney.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDisneyApi(): DisneyApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DisneyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterDatabase(app: Application): CharacterDatabase {
        return Room.databaseBuilder(
            app,
            CharacterDatabase::class.java,
            "character_db"
        ).build()
    }

    @Provides
    fun provideCharacterDao(db: CharacterDatabase): CharacterDao {
        return db.characterDao()
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(api: DisneyApi, dao: CharacterDao): CharacterRepository {
        return CharacterRepositoryImpl(api, dao)
    }
}