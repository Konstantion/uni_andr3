package com.konstantion.lab3

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert
    fun insertPost(post: Post): Long

    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<Post>>
}

@Dao
interface BookDao {

    @Insert
    fun insertBook(book: Book): Long

    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<Book>>
}

@Database(entities = [Post::class, Book::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun bookDao(): BookDao
}

object DatabaseHelper {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun ofContext(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "my_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}