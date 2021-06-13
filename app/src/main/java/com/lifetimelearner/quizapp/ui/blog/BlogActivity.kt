package com.lifetimelearner.quizapp.ui.blog

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.adapter.BlogRecyclerAdapter
import com.lifetimelearner.quizapp.model.Blog
import com.lifetimelearner.quizapp.singleton.OkHttpSingleton
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class BlogActivity : AppCompatActivity() {

    private lateinit var blogList : ArrayList<Blog>
    private lateinit var adapter : BlogRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        // get blogTopicId, blogTopicTitle, blogTopicDescription, blogTopicImageUrl

        blogList = ArrayList()
        val recyclerView = findViewById<RecyclerView>(R.id.blog_recycler_list)
        adapter = BlogRecyclerAdapter(this, blogList)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        loadQuestion()

    }

    private fun loadQuestion() {
        // now it is time to show questions here
        val url = "https://quiz-app-7663.herokuapp.com/blog"
        OkHttpSingleton.getClient().newCall(OkHttpSingleton.getRequest(url)).enqueue(
                object : okhttp3.Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onResponse(call: Call, response: Response) {
                        var result = response.body!!.string()
                        val gson = GsonBuilder().create()
                        val resultArray = gson.fromJson(result , Array<Blog>::class.java).toList()
                        runOnUiThread {
                            blogList.clear()
                            for(blog in resultArray) {
                                blogList.add(blog)
                                Log.d("LOGGING", blog.toString())
                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
        )
    }

}