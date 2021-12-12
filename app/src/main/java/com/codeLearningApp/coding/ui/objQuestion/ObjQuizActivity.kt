package com.codeLearningApp.coding.ui.objQuestion

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.codeLearningApp.coding.R
import com.codeLearningApp.coding.viewmodel.ObjQuestionViewModel

class ObjQuizActivity : AppCompatActivity() {

    private lateinit var viewModel: ObjQuestionViewModel
    private lateinit var controller: NavHostController
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_obj_question)

        viewModel = ViewModelProvider(this).get(ObjQuestionViewModel::class.java)

        dialog = getAlertDialog()

        viewModel.repository.tag = intent.getStringExtra("TAG") ?: ""

        controller = Navigation.findNavController(this, R.id.nav_host_quiz_fragment) as NavHostController

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


    override fun onBackPressed() {
        // you have to go to result fragment
        when(controller.currentDestination?.id) {
            R.id.quizSplashFragment -> {
                super.onBackPressed()
            }
            R.id.quizMainFragment -> {
                dialog.show()
            }
            R.id.quizResultFragment -> {
                finish()
            }
        }
    }

    private fun getAlertDialog(): AlertDialog {
        return AlertDialog.Builder(this, R.style.Theme_Design_BottomSheetDialog)
            .setTitle("Exit Quiz")
            .setMessage("Are you sure want to exit quiz")
            .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                controller.navigate(R.id.quizMainFragment_to_quizResultFragment)
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.cancel()
            }
            .setCancelable(true)
            .setIcon(R.drawable.icon_exit)
            .create()
    }

}

