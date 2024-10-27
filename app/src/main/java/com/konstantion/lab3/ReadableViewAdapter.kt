package com.konstantion.lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReadableViewAdapter(private var data: List<Readable>) :
    RecyclerView.Adapter<ReadableViewHolder>() {

    fun updateData(newData: List<Readable>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadableViewHolder {
        return when (viewType) {
            0 -> {
                val itemView =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
                ReadableViewHolder.Post(itemView)
            }

            1 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_book_layout, parent, false)
                ReadableViewHolder.Book(itemView)
            }

            else -> error("Invalid view type")
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = data[position].viewType

    override fun onBindViewHolder(holder: ReadableViewHolder, position: Int) =
        data[position].let { readable: Readable ->
            when {
                holder is ReadableViewHolder.Book && readable is Book -> {
                    holder.textViewBookTitle.text = readable.title
                    holder.textViewBookAuthor.text = readable.author
                    holder.textViewBookWrittenAt.text = readable.writtenAt
                }

                holder is ReadableViewHolder.Post && readable is Post -> {
                    holder.textViewTitle.text = readable.title
                    holder.textViewDescription.text = readable.description
                }

                else -> error("Type mismatch got view : $holder, readable : $readable.")
            }
        }
}

abstract sealed class ReadableViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class Post(view: View) : ReadableViewHolder(view) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
    }


    class Book(itemView: View) : ReadableViewHolder(itemView) {
        val textViewBookTitle: TextView = itemView.findViewById(R.id.textViewBookTitle)
        val textViewBookAuthor: TextView = itemView.findViewById(R.id.textViewBookAuthor)
        val textViewBookWrittenAt: TextView = itemView.findViewById(R.id.textViewBookWrittenAt)
    }
}

