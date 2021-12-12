package com.codeLearningApp.coding.ui.code

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.GsonBuilder
import com.codeLearningApp.coding.R
import com.codeLearningApp.coding.adapter.ProblemRecyclerAdapter
import com.codeLearningApp.coding.model.Problem
import com.codeLearningApp.coding.room.AppDatabase
import com.codeLearningApp.coding.utils.OkHttpSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class ProblemActivity : AppCompatActivity() {

    private lateinit var problemList: ArrayList<Problem>
    private lateinit var adapter: ProblemRecyclerAdapter
    private lateinit var progressBar : CircularProgressIndicator
    private lateinit var database : AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem)

        val tag = intent.getStringExtra("CODE_TAG") ?: ""
        val title = intent.getStringExtra("CODE_TITLE") ?: ""

        database = AppDatabase.getInstance(applicationContext)

        supportActionBar?.title = title

        problemList = arrayListOf()

        progressBar = findViewById(R.id.problem_progress_bar)
        progressBar.show()

        val recyclerView = findViewById<RecyclerView>(R.id.problem_recycler_view)

        val layoutManager = when(tag) {
            "pattern" -> GridLayoutManager(this,2)
            else -> LinearLayoutManager(this)
        }

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(dividerItemDecoration)
        adapter = ProblemRecyclerAdapter(this, problemList)
        recyclerView.adapter = adapter

        loadProblem(tag)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.dark_mode-> {
                if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadProblem(tag: String) {
        // now it is time to show questions here
        // first check in database

        CoroutineScope(Dispatchers.IO).launch {
            val databaseProblem = database.appDao().getProblems("%$tag%")
            if(databaseProblem.isNotEmpty()) {
                problemList.clear()
                problemList.addAll(databaseProblem)
                adapter.notifyDataSetChanged()
                withContext(Dispatchers.Main) {
                    progressBar.hide()
                }
            } else {
                loadProblemFromServer(tag)
            }
        }

    }

    private fun loadProblemFromServer(tag: String) {
        // request from server

        val url = "https://quiz-app-7663.herokuapp.com/problem?tag=$tag";
        OkHttpSingleton.getClient().newCall(OkHttpSingleton.getRequest(url)).enqueue(
                object : okhttp3.Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            progressBar.hide()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        var result = response.body!!.string()
                        val gson = GsonBuilder().create()
                        val resultArray = gson.fromJson(result, Array<Problem>::class.java).toList()

                        problemList.clear()
                        problemList.addAll(resultArray)
                        database.appDao().insertProblems(resultArray)

                        runOnUiThread {
                            adapter.notifyDataSetChanged()
                            progressBar.hide()
                        }
                    }
                }
        )
    }

}