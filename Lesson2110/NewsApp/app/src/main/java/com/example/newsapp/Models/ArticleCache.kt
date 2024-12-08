package com.example.newsapp.Models

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class ArticleCache (
    @PrimaryKey
    val url : String,
    val articleJsonString : String
)

@Dao
interface ArticleCacheDao {

    @Query("SELECT * FROM ArticleCache")
    suspend fun getAll():List<ArticleCache>

    @Query("SELECT * FROM ArticleCache Where url = :url")
    suspend fun getByUrl(url : String) : ArticleCache?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleCache: ArticleCache)

    @Query("DELETE FROM ArticleCache Where url = :url")
    suspend fun delete(url: String)

}