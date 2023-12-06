package com.example.f_myapp6.screans

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f_myapp6.Data.DataOrException
import com.example.f_myapp6.model.QuastionItem
import com.example.f_myapp6.repristory.QuestionRepristory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private  val repristory: QuestionRepristory)
    :ViewModel(){
    val data:MutableState<DataOrException<ArrayList<QuastionItem>,Boolean,Exception>> =
        mutableStateOf( DataOrException(null,true,Exception("")))

    init {
        getAllQuestions()
    }

   private fun getAllQuestions(){
        viewModelScope.launch {
            data.value.loading=true
            data.value=repristory.getAllQuestions()
            if (data.value.data.toString().isNotEmpty()){
                data.value.loading=false
            }
        }
    }
    fun getToutelQuestionCount():Int{
        return data.value.data?.toMutableList()!!.size
    }
}