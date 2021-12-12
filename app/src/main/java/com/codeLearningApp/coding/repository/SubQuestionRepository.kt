package com.codeLearningApp.coding.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.codeLearningApp.coding.model.SubQuestion
import com.codeLearningApp.coding.room.AppDatabase
import com.codeLearningApp.coding.utils.OkHttpSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class SubQuestionRepository(val context: Context) {

    var subQuestionList = arrayListOf<SubQuestion>()
    private var database = AppDatabase.getInstance(context)
    var questionLoaded: MutableLiveData<Boolean> = MutableLiveData()
    var networkIsAlive: MutableLiveData<Boolean> = MutableLiveData()

    fun loadSubQuestion(tag: String) {
        // now it is time to show questions here

        CoroutineScope(Dispatchers.IO).launch {
            val databaseCode = database.appDao().getSubQuestions("%$tag%")
            if(databaseCode.isNotEmpty()) {
                subQuestionList.clear()
                for (code in databaseCode) {
                    subQuestionList.add(code)
                }
                Log.d("LOGGING", subQuestionList.toString())
                questionLoaded.postValue(true)
            }
        }

    }

    fun loadSubQuestionFromServer(tag: String) {
        // now it is time to show questions here
        val url = "https://quiz-app-7663.herokuapp.com/subQuestion?tag=$tag"
        OkHttpSingleton.getClient().newCall(OkHttpSingleton.getRequest(url)).enqueue(
                object : okhttp3.Callback {
                    override fun onFailure(call: Call, e: IOException) {
//                        Toast.makeText(context, "Error! No Response From Server", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call, response: Response) {
                        var result = response.body!!.string()
                        val gson = GsonBuilder().create()
                        val resultArray = gson.fromJson(result , Array<SubQuestion>::class.java).toList()
                        subQuestionList.clear()
                        database.appDao().insertSubQuestions(resultArray)
                        subQuestionList.addAll(resultArray)
                        Log.d("LOGGING", subQuestionList.toString())
                        questionLoaded.postValue(true)
                    }
                }
        )
    }


}