package com.konstantion.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReadableViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val books = generateTestData()
        adapter = ReadableViewAdapter(books)
        recyclerView.adapter = adapter
    }

    private fun generateTestData(): List<Readable> {
        val dataList = mutableListOf<Readable>()

        for (i in 1..10) {
            dataList.add(
                Post(
                    title = "Post Title $i",
                    description = "Description for Post $i"
                )
            )
        }

        for (i in 1..10) {
            dataList.add(
                Book(
                    title = "Book Title $i",
                    author = "Author $i",
                    writtenAt = "Year ${2000 + i}"
                )
            )
        }

        return dataList.shuffled()
    }
}
