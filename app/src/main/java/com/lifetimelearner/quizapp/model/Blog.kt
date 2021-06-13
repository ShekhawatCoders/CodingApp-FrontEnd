package com.lifetimelearner.quizapp.model

data class Blog(
    val blogId : Int,
    val blogTitle : String,
    val blogDesc : String,
    val blogUrl : String,
    val blogPriority : Int,
    val blogLevel : Int,
    val blogTopic : Int,
    val blogTag : String
)
