package com.konstantion.lab3

sealed interface Readable {
    val viewType: Int
}

data class Post(
    val title: String,
    val description: String,
) : Readable {
    override val viewType: Int
        get() = 0
}

data class Book(
    val title: String,
    val author: String,
    val writtenAt: String,
) : Readable {
    override val viewType: Int
        get() = 1
}
