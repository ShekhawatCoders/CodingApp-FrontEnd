package com.lifetimelearner.quizapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hanks.htextview.base.HTextView
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.utils.GlobalData
import com.lifetimelearner.quizapp.ui.main.MainActivity
import com.lifetimelearner.quizapp.ui.question.SubQuestionActivity
import com.lifetimelearner.quizapp.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

        val userAuth = Firebase.auth.uid != null

        if(userAuth) {

            Handler().postDelayed({

                goToMainActivity()

            },1500)

        } else {

            Handler().postDelayed({

                Navigation.findNavController(
                    this,
                    R.id.nav_host_splash_fragment
                )
                    .navigate(R.id.action_splashFragment_to_loginFragment)

            },1500)

            viewModel.userLogin.observe(this, {
                if(it) {
                    goToMainActivity()
                }
            })

        }


    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        // splash activity
        // super.onBackPressed()
        finish()
    }

}