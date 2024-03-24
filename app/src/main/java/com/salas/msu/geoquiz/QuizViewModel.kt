package com.salas.msu.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import java.lang.Exception

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
class QuizViewModel(private val savedStateHandle:SavedStateHandle):ViewModel() {


    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private val cheatedQuestions = mutableSetOf<Int>()

    var currentIndex: Int
    get() = savedStateHandle.get(CURRENT_INDEX_KEY)?:0
    set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)


    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

  /*  var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)*/

    fun isCheater(index: Int): Boolean {
        return cheatedQuestions.contains(index)
    }

    fun markAsCheater(index: Int) {
        cheatedQuestions.add(index)
    }

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }
}