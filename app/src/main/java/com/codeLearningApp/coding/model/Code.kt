package com.codeLearningApp.coding.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Code(
    @PrimaryKey
    val codeId: Int,
    val problemId: Int,
    val codeTitle: String,
    val codeDetail: String,
    val codeCode: String,
    val codeLang: String
)
