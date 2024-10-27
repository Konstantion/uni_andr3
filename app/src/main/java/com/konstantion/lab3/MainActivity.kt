package com.konstantion.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReadableViewAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = DatabaseHelper.ofContext(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        initDatabase()

        adapter = ReadableViewAdapter(listOf())
        recyclerView.adapter = adapter
    }

    private fun initDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            val postDao = database.postDao()
            val bookDao = database.bookDao()

            val now = Instant.now()
            val post = Post(
                title = "Database Post Title ${now.epochSecond}", description = now.toString()
            )
            postDao.insertPost(post)

            val book = Book(
                title = "Database Book Title ${now.epochSecond}",
                author = now.toString(),
                writtenAt = "Year 2024"
            )
            bookDao.insertBook(book)

            withContext(Dispatchers.Main) {
                loadDataFromDatabase()
            }
        }
    }

    private fun loadDataFromDatabase() {
        val postDao = database.postDao()
        val bookDao = database.bookDao()

        val postsFlow = postDao.getAllPosts()
        val booksFlow = bookDao.getAllBooks()

        lifecycleScope.launch {
            postsFlow.combine(booksFlow) { posts, books ->
                val dataList = mutableListOf<Readable>()
                dataList.addAll(posts)
                dataList.addAll(books)
                dataList
            }.collectLatest { dataList ->
                withContext(Dispatchers.Main) {
                    adapter.updateData(dataList)
                }
            }
        }
    }
}
