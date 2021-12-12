package com.codeLearningApp.coding.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Problem(
        @PrimaryKey
    val problemId: Int,
    val problemTitle: String,
    val problemDetail: String,
    val problemTags: String
)
