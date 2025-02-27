package com.salas.msu.geoquiz

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.salas.msu.geoquiz.databinding.ActivityMainBinding


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding
    private val quizViewModel:QuizViewModel by viewModels ()

    private val cheatLauncher = registerForActivityResult(

        ActivityResultContracts.StartActivityForResult()
    ) { result ->


        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.markAsCheater(quizViewModel.currentIndex)
        }
       /* if (result.resultCode == Activity.RESULT_OK) {
        quizViewModel.isCheater =
            result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate (Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel:$quizViewModel")



        binding.trueButton.setOnClickListener{

            checkAnswer(true)

        }

        binding.falseButton.setOnClickListener{

            checkAnswer(false)
        }
        binding.cheatButton.setOnClickListener{
            //val intent = Intent (this, CheatActivity::class.java)
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            //startActivity(intent)

            cheatLauncher.launch(intent)
        }

        binding.nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()



        }

        binding.questionTextview.setOnClickListener {
            quizViewModel.moveToNext()

            updateQuestion()


        }
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText

        binding.questionTextview.setText(questionTextResId)
    }



    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer


        /*val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        }else{
            R.string.incorrect_toast

        }*/
        val messageResId = when {
            quizViewModel.isCheater(quizViewModel.currentIndex) -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }


        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show()
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

}