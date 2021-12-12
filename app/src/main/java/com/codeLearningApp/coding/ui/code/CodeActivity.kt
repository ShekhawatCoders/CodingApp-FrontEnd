package com.codeLearningApp.coding.ui.code

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.*
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.codeLearningApp.coding.R
import com.codeLearningApp.coding.adapter.CodePagerAdapter
import com.codeLearningApp.coding.viewmodel.CodeViewModel

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

}