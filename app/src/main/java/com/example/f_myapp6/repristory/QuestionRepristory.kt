package com.example.f_myapp6.repristory

import android.util.Log
import com.example.f_myapp6.Data.DataOrException
import com.example.f_myapp6.model.QuastionItem
import com.example.f_myapp6.network.QuestionApi
import javax.inject.Inject

class QuestionRepristory @Inject constructor(private  val api:QuestionApi) {

    private val dataOrException = DataOrException<ArrayList<QuastionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuastionItem>, Boolean, java.lang.Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if (dataOrException.data
                    .toString().isNotEmpty())  dataOrException.loading=false

        } catch (exception: Exception) {
            dataOrException.e=exception
            Log.d("Exc", "getAllQuestions: ${dataOrException.e!!.localizedMessage}")


        }
        return dataOrException

    }
}


