package com.konstantion.lab3

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed interface Readable {
    val viewType: Int
}

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
) : Readable {
    override val viewType: Int
        get() = 0
}

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val writtenAt: String,
) : Readable {
    override val viewType: Int
        get() = 1
}
