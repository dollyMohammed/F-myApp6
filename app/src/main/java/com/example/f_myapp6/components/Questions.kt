package com.example.f_myapp6.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.BoxScopeInstance.align
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.f_myapp6.model.QuastionItem
import com.example.f_myapp6.screans.QuestionViewModel
import com.example.f_myapp6.util.AppColors
import java.nio.file.WatchEvent

@Composable
fun Questions(viewModel: QuestionViewModel) {
    val questions=viewModel.data.value.data?.toMutableList()
    val questionIndex= remember {
        mutableStateOf(0)
    }
    if (viewModel.data.value.loading==true){
       // CircularProgressIndicator()

    }else{
      val question=  try {
            questions?.get(questionIndex.value)
        }catch (ex:Exception){
            null
        }
        if(questions != null){
            QuestionDiaplay(question = question!!,questionIndex=questionIndex, viewModel = viewModel){
                questionIndex.value=questionIndex.value+1
            }
        }
    }
}
//@Preview
@Composable
fun QuestionDiaplay(
    question:QuastionItem,
    questionIndex:MutableState<Int>,
    viewModel: QuestionViewModel,
    onNextClicked :(Int) -> Unit ={}

){
    val choicesState= remember(question) {
        question.choices.toMutableList()

    }
    val answareState= remember(question) {
        mutableStateOf<Int?>(null)

    }
    val correctAnswareState= remember(question) {
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer:(Int) -> Unit = remember(question) {
        {
            answareState.value = it
            correctAnswareState.value=choicesState[it]== question.answer
        }
    }
    val pathEffect=PathEffect.dashPathEffect(floatArrayOf(10f,10f),0f)
   Surface(modifier = Modifier
       .fillMaxWidth()
       .fillMaxHeight()
       .padding(4.dp),
   color = AppColors.mDarkpurple) {
       Column(modifier = Modifier.padding(12.dp),
       verticalArrangement = Arrangement.Top,
       horizontalAlignment = Alignment.Start) {
           if (questionIndex.value >=3) ShowProgress(score = questionIndex.value)
           QuestionTracker(counter = questionIndex.value,viewModel.getToutelQuestionCount())
           DrawDottLine(  pathEffect)
           Column {

               Text(text = question.question,
                   modifier = Modifier
                       .padding(6.dp)
                       .align(alignment = Alignment.Start)
                       .fillMaxHeight(0.3f),
               fontSize = 17.sp,
               fontWeight = FontWeight.Bold,
               lineHeight = 22.sp,
               color = AppColors.mOffWhite)
               choicesState.forEachIndexed { index, answerText ->

                   Row(modifier = Modifier
                       .padding(3.dp)
                       .fillMaxWidth()
                       .height(45.dp)
                       .border(
                           width = 4.dp, brush = Brush.linearGradient(
                               colors = listOf(
                                   AppColors.mLightPurple,
                                   AppColors.mOffDarkpurple
                               )
                           ), shape = RoundedCornerShape(15.dp)
                       )
                       .clip(
                           RoundedCornerShape(
                               topStartPercent = 50,
                               topEndPercent = 50,
                               bottomEndPercent = 50,
                               bottomStartPercent = 50
                           )
                       )
                       .background(Color.Transparent),
                       verticalAlignment = Alignment.CenterVertically) {
                       RadioButton(selected =answareState.value==index , onClick = {
                           updateAnswer(index) },
                       modifier = Modifier.padding(start = 16.dp),
                           colors = RadioButtonDefaults.colors(
                               selectedColor = if(correctAnswareState.value ==true && index == answareState.value){
                                   Color.Green.copy(alpha = 0.2f)
                               }else{
                                   Color.Red.copy(alpha = 0.2f) }))



                       val annotatedString= buildAnnotatedString {
                           withStyle(style = SpanStyle(fontWeight = FontWeight.Light,
                           color=  if(correctAnswareState.value ==true && index == answareState.value){
                               Color.Green
                           }else if (correctAnswareState.value ==false && index == answareState.value) {
                               Color.Red
                           }else{AppColors.mOffWhite}, fontSize = 17.sp)
                           ){
append(answerText)
                       }

                       }
                       Text(text = annotatedString, modifier = Modifier.padding(6.dp))
                   }
               }
               Button(onClick = { onNextClicked(questionIndex.value) },
               modifier = Modifier
                   .padding(3.dp)
                   .align(alignment = Alignment.CenterHorizontally),
               shape = RoundedCornerShape(34.dp),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = AppColors.mBlue
                   )

               ) {
                   Text(text = "Next",
                   modifier = Modifier.padding(4.dp),
                  fontSize = 17.sp,
                   color = AppColors.mOffWhite)


               }
           }
       }}}





@Composable
fun DrawDottLine(pathEffect: PathEffect){
    androidx.compose.foundation.Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp),

        ){
        drawLine(color = AppColors.mLightGray,
        start = Offset(0f,0f),
            end = Offset(size.width,y=0f),
            pathEffect=pathEffect

        )

    }
}









@Preview
@Composable
fun QuestionTracker(counter:Int=10,OutOf:Int=100){
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(style = SpanStyle(color = AppColors.mLightGray, fontWeight = FontWeight.Bold,
                fontSize = 27.sp)){
                append("Question$counter/")
                withStyle(style = SpanStyle(color = AppColors.mLightGray, fontWeight = FontWeight.Light,
                fontSize = 14.sp)){
                    append("$OutOf")
                }
            }
        }
    },
    modifier = Modifier.padding(20.dp))

}
@Preview
@Composable
fun ShowProgress(score:Int=12){
    val gradient=Brush.linearGradient(listOf(Color(0xFFF95075),
    Color(0xFFBE6BE5)
    ))
    val ProgressFactor = remember(score) {
        mutableStateOf(score*0.005f)

    }
    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp, brush = Brush.linearGradient(
                colors = listOf(
                    AppColors.mLightPurple, AppColors.mLightPurple
                )
            ),
            shape = RoundedCornerShape(34.dp)
        )
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomStartPercent = 50,
                bottomEndPercent = 50
            )
        )
        .background(color = Color.Transparent),
    verticalAlignment = Alignment.CenterVertically) {
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = {  },
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(ProgressFactor.value)
            .background(brush = gradient),
        elevation = null,
            enabled = false,
            colors = buttonColors(
                disabledContainerColor = Color.Transparent
            )
            ) {
            Text(text = (score * 10).toString(),
                modifier = Modifier.clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxWidth(87f)
                    .fillMaxHeight().padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center

            )


        }

    }
}
