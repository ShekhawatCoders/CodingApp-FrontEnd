package com.lifetimelearner.quizapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Blog(
    @PrimaryKey
    val blogId : Int,
    val blogTitle : String,
    val blogDesc : String,
    val blogUrl : String,
    val blogTags : String // for quiz
)
