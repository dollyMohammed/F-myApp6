package com.example.f_myapp6.network

import com.example.f_myapp6.model.Quastion
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestions():Quastion
}