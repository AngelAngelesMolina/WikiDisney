package com.example.wikidisney.data.di

import com.example.wikidisney.common.Constants
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
    fun provideCoinRepository(api: DisneyApi): CharacterRepository {
        return CharacterRepositoryImpl(api)
    }
}