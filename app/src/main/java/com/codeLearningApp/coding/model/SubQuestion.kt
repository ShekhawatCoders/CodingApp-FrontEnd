package com.codeLearningApp.coding.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubQuestion(
        @PrimaryKey
        val questionId: Int,
        val question: String,
        val answer: String,
        val questionTags: String
)
