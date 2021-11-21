package com.lifetimelearner.quizapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ObjQuestion(
    @PrimaryKey
    val questionId: Int,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val answer: Int,
    val explanation: String,
    val questionTags: String
)
