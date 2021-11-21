package com.lifetimelearner.quizapp.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.lifetimelearner.quizapp.model.Code
import com.lifetimelearner.quizapp.room.AppDatabase
import com.lifetimelearner.quizapp.utils.OkHttpSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class CodeRepository(val context: Context) {

    var database = AppDatabase.getInstance(context)
    var codeMap = mutableMapOf<String, ArrayList<Code>>()
    var problemId: Int = 1
    var codeLoaded: MutableLiveData<Boolean> = MutableLiveData()

    fun loadCode() {
        // now it is time to show questions here

        CoroutineScope(Dispatchers.IO).launch {
            val databaseCode = database.appDao().getCodes(problemId)
            if(databaseCode.isNotEmpty()) {
                codeMap.clear()
                for (code in databaseCode) {
                    codeMap.putIfAbsent(code.codeLang.toLowerCase().trim(), arrayListOf())
                    codeMap[code.codeLang.toLowerCase().trim()]!!.add(code)
                }
                codeLoaded.postValue(true)
            } else {
                loadCodeFromServer()
            }
        }


    }

    private fun loadCodeFromServer() {

        // request from server

        val url = "https://quiz-app-7663.herokuapp.com/code?problemId=$problemId"
        OkHttpSingleton.getClient().newCall(OkHttpSingleton.getRequest(url)).enqueue(
            object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    /*Toast.makeText(context, "Error! Please make sure you " +
                            "have good Internet Connectivity", Toast.LENGTH_SHORT)
                            .show()*/
                    codeLoaded.postValue(true)
                }

                override fun onResponse(call: Call, response: Response) {
                    var result = response.body!!.string()
                    val gson = GsonBuilder().create()
                    val resultArray = gson.fromJson(result, Array<Code>::class.java).toList()
                    codeMap.clear()
                    database.appDao().insertCodes(resultArray)
                    for (code in resultArray) {
                        codeMap.putIfAbsent(code.codeLang.toLowerCase().trim(), arrayListOf())
                        codeMap[code.codeLang.toLowerCase().trim()]!!.add(code)
                    }
                    codeLoaded.postValue(true)
                }
            }
        )

    }

}