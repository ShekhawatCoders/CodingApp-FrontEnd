package com.codeLearningApp.coding.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.codeLearningApp.coding.repository.CodeRepository

class CodeViewModel(app: Application): AndroidViewModel(app) {

    val repository = CodeRepository(app)

}