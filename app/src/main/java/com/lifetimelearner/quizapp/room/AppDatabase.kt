package com.lifetimelearner.quizapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lifetimelearner.quizapp.model.Code
import com.lifetimelearner.quizapp.model.ObjQuestion
import com.lifetimelearner.quizapp.model.Problem
import com.lifetimelearner.quizapp.model.SubQuestion

@Database(
    entities = [
        Problem::class,
        Code::class,
        ObjQuestion::class,
        SubQuestion::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {

        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    "code_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }

    }

}