package com.salas.msu.geoquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.salas.msu.geoquiz.databinding.ActivityMainBinding


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    var numberOfRightAnswers = 0

    private lateinit var binding : ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate (Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.trueButton.setOnClickListener{

            checkAnswer(true)
            disableButton()
            showScoreIfLastQuestion()
        }

        binding.falseButton.setOnClickListener{

            checkAnswer(false)
            disableButton()
            showScoreIfLastQuestion()
        }

        binding.nextButton.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            enableButton()


        }

        binding.questionTextview.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            enableButton()
            updateQuestion()


        }
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextview.setText(questionTextResId)
    }
    private fun disableButton() {
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }

    private fun enableButton() {
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }


    private fun showScoreIfLastQuestion() {
        if (currentIndex == questionBank.size - 1) {
            showScore()
        }
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        if (userAnswer == correctAnswer) {
            numberOfRightAnswers++
        }


        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        }else{
            R.string.incorrect_toast

        }

        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show()
    }
    private fun computeScore(): Double {
        return (numberOfRightAnswers.toDouble() / questionBank.size) * 100.0
    }
    private fun showScore() {
        if (currentIndex == questionBank.size - 1) {
            val score = computeScore()
            val formattedScore = String.format("%.1f%%", score)

            Toast.makeText(this, "Your Score: $formattedScore", Toast.LENGTH_SHORT).show()

            currentIndex = 0
            numberOfRightAnswers = 0
        }
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