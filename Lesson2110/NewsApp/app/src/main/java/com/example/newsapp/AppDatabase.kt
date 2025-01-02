package com.example.newsapp

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.Models.ArticleCache
import com.example.newsapp.Models.ArticleCacheDao


@Database(entities = [ArticleCache::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleCacheDao(): ArticleCacheDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            Log.d("DatabaseInit", "Inicializando banco de dados")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db_news"
                ).fallbackToDestructiveMigration() // Garante migração destrutiva
                    .build()
                INSTANCE = instance
                Log.d("DatabaseInit", "Banco de dados inicializado com sucesso")
                instance
            }
        }
    }
}


