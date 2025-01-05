package com.example.newsapp.Models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class ArticleCache(
    @PrimaryKey val url: String,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val author: String?,
    val content: String?,
    val articleJsonString: String?
)


@Dao
interface ArticleCacheDao {
    @Query("SELECT * FROM ArticleCache")
    fun getAll(): List<ArticleCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articleCache: ArticleCache): Long

    @Delete
    fun delete(articleCache: ArticleCache)
}
