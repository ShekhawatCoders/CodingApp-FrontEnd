package com.lifetimelearner.quizapp.ui.code

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.adapter.CodePagerAdapter
import com.lifetimelearner.quizapp.viewmodel.CodeViewModel

class CodeActivity : AppCompatActivity() {

    private lateinit var progressBar: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)

        MobileAds.initialize(this) {}

        val mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        progressBar = findViewById(R.id.code_progress_bar)
        progressBar.show()

        val viewModel = ViewModelProvider(this).get(CodeViewModel::class.java)
        viewModel.repository.problemId = intent.getIntExtra("PROBLEM_ID",0)
        val problemTitle = intent.getStringExtra("PROBLEM_TITLE")

        supportActionBar?.title = "${viewModel.repository.problemId}. $problemTitle"

        viewModel.repository.loadCode()


        val viewPager = findViewById<ViewPager>(R.id.code_view_pager)

        viewModel.repository.codeLoaded.observe(this, {
            val adapter = CodePagerAdapter(supportFragmentManager, 1, this, viewModel.repository.codeMap)
            viewPager.adapter = adapter
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