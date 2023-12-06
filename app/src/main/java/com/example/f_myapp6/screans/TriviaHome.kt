package com.example.f_myapp6.screans

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.f_myapp6.Questions
import com.example.f_myapp6.components.Questions

@Composable
fun TriviaHome(viewModel: QuestionViewModel= hiltViewModel()){
    Questions(viewModel)

}
