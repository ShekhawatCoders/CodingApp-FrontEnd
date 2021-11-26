package com.lifetimelearner.quizapp.ui.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.model.Code
import com.lifetimelearner.quizapp.model.Problem
import com.lifetimelearner.quizapp.room.AppDatabase
import com.lifetimelearner.quizapp.ui.splash.SplashActivity
import com.lifetimelearner.quizapp.utils.OkHttpSingleton
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var database : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = AppDatabase.getInstance(applicationContext)
        toolbar = findViewById(R.id.main_toolbar)

        setSupportActionBar(toolbar)

        // schedule for downloading content

        // Authentication Listener
        Firebase.auth.addAuthStateListener {
            if(it.currentUser == null) {
                val intent = Intent(this, SplashActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

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
            R.id.logout-> {
                val dialog = getAlertDialog()
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getAlertDialog(): AlertDialog {
        return AlertDialog.Builder(this, R.style.Theme_Design_BottomSheetDialog)
            .setTitle("Logout")
            .setMessage("Are you sure want to logout")
            .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                FirebaseAuth.getInstance().signOut()
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .setCancelable(true)
            .setIcon(R.drawable.icon_exit)
            .create()
    }

}