package com.codeLearningApp.coding.ui.subQuestion

import android.net.ConnectivityManager
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.codeLearningApp.coding.R
import com.codeLearningApp.coding.adapter.SubQuestionRecyclerAdapter
import com.codeLearningApp.coding.viewmodel.SubQuestionViewModel

class SubQuestionActivity : AppCompatActivity() {

    private lateinit var adapter : SubQuestionRecyclerAdapter
    private lateinit var progressBar: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_question)

        val viewModel = ViewModelProvider(this).get(SubQuestionViewModel::class.java)

        val tag = intent.getStringExtra("TAG") ?: ""

        progressBar = findViewById(R.id.sub_question_progress_bar)
        progressBar.show()

        val recyclerView = findViewById<RecyclerView>(R.id.card_sub_question_recycler_view)
        adapter = SubQuestionRecyclerAdapter(this, viewModel.repository.subQuestionList)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.adapter = adapter

        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModel.repository.networkIsAlive.postValue(true)
            }
            override fun onLost(network: Network) {
                viewModel.repository.networkIsAlive.postValue(false)
            }
        })

        // check in cache
        viewModel.repository.loadSubQuestion(tag)

        viewModel.repository.networkIsAlive.observe(this, {
            if(it) {
                if(viewModel.repository.subQuestionList.isEmpty())
                    viewModel.repository.loadSubQuestionFromServer(tag)
            } else {
                Toast.makeText(applicationContext,
                    "No Network Connection",
                        Toast.LENGTH_SHORT)
                        .show()
            }
        })

        viewModel.repository.questionLoaded.observe(this, {
            adapter.notifyDataSetChanged()
            if(it) progressBar.hide()
        })

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

}