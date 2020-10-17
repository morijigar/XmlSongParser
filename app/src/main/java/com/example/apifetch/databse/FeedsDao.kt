package com.example.apifetch.databse

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.apifetch.model.Entry
import com.example.apifetch.model.Result


@Dao
abstract class FeedsDao {

    @Query("DELETE FROM tbl_songs")
    abstract fun deleteAllRow()

    @Insert(onConflict = REPLACE)
    abstract fun save(item: Entry): Long

    @Insert(onConflict = REPLACE)
    abstract suspend fun save(item: List<Entry>)

    @Update
    abstract fun update(item: Entry?): Int

    @Query("SELECT * FROM tbl_songs")
    abstract fun selectAll(): Cursor

    @Query("select * from tbl_songs")
    abstract fun getNumberOfRows(): Int

    @Query("select * from tbl_songs")
    abstract suspend fun getAllData(): MutableList<Entry>

    @Query("SELECT * FROM tbl_songs LIMIT :limit OFFSET :offset")
    abstract suspend fun getAllData(limit: Int, offset: Int): MutableList<Entry>

    @Query("select * from tbl_songs WHERE title =:title")
    abstract suspend fun getTitleSong(title: String): Entry

}