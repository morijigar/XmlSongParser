package com.example.myapplication.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apifetch.databse.FeedsDao
import com.example.apifetch.databse.MoviesDao
import com.example.apifetch.model.Entry
import com.example.apifetch.model.Result

@Database(entities = [Result::class,Entry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    abstract fun feedsDao(): FeedsDao

}


