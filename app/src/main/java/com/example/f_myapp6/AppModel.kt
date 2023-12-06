package com.example.f_myapp6

import com.example.f_myapp6.network.QuestionApi
import com.example.f_myapp6.repristory.QuestionRepristory
import com.example.f_myapp6.util.constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModel {
    @Singleton
    @Provides
fun provideQuestionRepostory(api:QuestionApi) = QuestionRepristory(api)

    @Singleton
    @Provides
    fun provideQuestionApi():QuestionApi{
        return Retrofit.Builder()
            .baseUrl(constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }
}