package com.codeLearningApp.coding.utils

import okhttp3.OkHttpClient
import okhttp3.Request

object OkHttpSingleton {

    fun getClient() : OkHttpClient {
        return OkHttpClient()
    }

    fun getRequest(url : String) : Request {
        return Request.Builder()
                .url(url)
                .get()
                .build()
    }

}