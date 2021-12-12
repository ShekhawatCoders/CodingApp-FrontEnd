package com.codeLearningApp.coding.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codeLearningApp.coding.model.Code
import com.codeLearningApp.coding.model.ObjQuestion
import com.codeLearningApp.coding.model.Problem
import com.codeLearningApp.coding.model.SubQuestion

@Dao
interface AppDao {

    @Query("SELECT * FROM Problem WHERE problemTags LIKE :tag")
    fun getProblems(tag: String) : List<Problem>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProblems(problems : List<Problem>)
    @Query("SELECT * FROM Problem")
    fun getAllProblems() : List<Problem>

    @Query("SELECT * FROM Code WHERE problemId = :problemId")
    fun getCodes(problemId: Int) : List<Code>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCodes(problems : List<Code>)
    @Query("SELECT * FROM Code")
    fun getAllCodes() : List<Code>

    @Query("SELECT * FROM ObjQuestion WHERE questionTags Like :questionTags")
    fun getObjQuestions(questionTags: String) : List<ObjQuestion>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertObjQuestions(problems : List<ObjQuestion>)

    @Query("SELECT * FROM SubQuestion WHERE questionTags Like :questionTags")
    fun getSubQuestions(questionTags: String) : List<SubQuestion>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubQuestions(problems : List<SubQuestion>)

}