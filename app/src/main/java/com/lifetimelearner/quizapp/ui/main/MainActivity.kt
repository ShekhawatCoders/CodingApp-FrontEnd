package com.lifetimelearner.quizapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.GsonBuilder
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.model.Code
import com.lifetimelearner.quizapp.model.Problem
import com.lifetimelearner.quizapp.room.AppDatabase
import com.lifetimelearner.quizapp.utils.OkHttpSingleton
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var database : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = AppDatabase.getInstance(applicationContext)


        // schedule for downloading content

    }

    private fun loadAllCode() {
        // now it is time to show questions here
        val url = "https://quiz-app-7663.herokuapp.com/allCode"
        OkHttpSingleton.getClient().newCall(OkHttpSingleton.getRequest(url)).enqueue(
            object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    var result = response.body!!.string()
                    val gson = GsonBuilder().create()
                    val resultArray = gson.fromJson(result, Array<Code>::class.java).toList()
                    if(!resultArray.isNullOrEmpty())
                        database.appDao().insertCodes(resultArray)
                }
            }
        )
    }

    private fun loadAllProblem() {
        // now it is time to show questions here
        val url = "https://quiz-app-7663.herokuapp.com/allProblem";
        OkHttpSingleton.getClient().newCall(OkHttpSingleton.getRequest(url)).enqueue(
            object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    var result = response.body!!.string()
                    val gson = GsonBuilder().create()
                    val resultArray = gson.fromJson(result, Array<Problem>::class.java).toList()
                    if(!resultArray.isNullOrEmpty())
                        database.appDao().insertProblems(resultArray)
                }
            }
        )
    }

    override fun onBackPressed() {
        finish()
        // super.onBackPressed()
    }

}