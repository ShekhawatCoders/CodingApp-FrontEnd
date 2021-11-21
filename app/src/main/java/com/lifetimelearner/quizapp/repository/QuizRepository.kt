package com.lifetimelearner.quizapp.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.lifetimelearner.quizapp.model.ObjQuestion
import com.lifetimelearner.quizapp.room.AppDatabase
import com.lifetimelearner.quizapp.utils.OkHttpSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import kotlin.random.Random

class QuizRepository(val context: Context) {

    var testMode: Boolean = false
    private var objQuestionList : ArrayList<ObjQuestion> = arrayListOf()
    private var database = AppDatabase.getInstance(context)
    var objRandomQuestionList: ArrayList<ObjQuestion>  = arrayListOf()
    var answers = mutableMapOf<Int, Int>()
    lateinit var tag: String
    var size: Int = 5 // generate random number of size by default it is 5
    var quizLoaded: MutableLiveData<Boolean> = MutableLiveData()

    fun loadObjQuestion() {
        // now it is time to show questions here

        // handle device rotation
        if(objRandomQuestionList.size > 0)
            return

        CoroutineScope(Dispatchers.IO).launch {
            val databaseCode = database.appDao().getObjQuestions("%$tag%")
            if (databaseCode.isNotEmpty()) {
                objQuestionList.clear()
                for (code in databaseCode) {
                    objQuestionList.add(code)
                }

                if (objQuestionList.size < size) loadObjQuestionFromServer()
                else generateRandom()

            } else {
                loadObjQuestionFromServer()
            }
        }

    }

    private fun loadObjQuestionFromServer() {
        // now it is time to show questions here

        val url = "https://quiz-app-7663.herokuapp.com/objQuestion?tag=$tag"

        OkHttpSingleton.getClient().newCall(OkHttpSingleton.getRequest(url)).enqueue(
            object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // try again after some time
                    // keep track of time
                }
                override fun onResponse(call: Call, response: Response) {
                    var result = response.body!!.string()
                    val gson = GsonBuilder().create()
                    val resultArray = gson.fromJson(result, Array<ObjQuestion>::class.java).toList()
                    database.appDao().insertObjQuestions(resultArray)
                    objQuestionList.clear()
                    for (question in resultArray) {
                        objQuestionList.add(question)
                    }
                    if (objQuestionList.size < size) {
                        Toast.makeText(context, "Not Enough Questions Found", Toast.LENGTH_SHORT)
                                .show()
                    } else {
                        generateRandom()
                    }
                }
            }
        )
    }

    private fun generateRandom() {

        Log.d("LOGGING", "GENERATING RANDOM NUMBER")

        val randomList = arrayListOf<Int>()

        while (randomList.size < size) {
            val randomNumber = Random(System.currentTimeMillis()).nextInt(size)
            if (!randomList.contains(randomNumber))
                randomList.add(randomNumber)
        }

        randomList.shuffle()

        Log.d("LOGGING", "SHUFFLING RANDOM NUMBER")

        for(ind in randomList)
            objRandomQuestionList.add(objQuestionList[ind])

        quizLoaded.postValue(true)

    }

}