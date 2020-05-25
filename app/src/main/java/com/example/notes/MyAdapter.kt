package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{



    private var items: List<Blogs> = ArrayList()


    fun submitList(blogList: List<Blogs>)
    {
        items = blogList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.blogs, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is BlogViewHolder -> {
                holder.bind(items[position])
            }

        }
    }



    class BlogViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView)
    {
        private val title_view: TextView = itemView.findViewById(R.id.title)
        private val desc_view: TextView = itemView.findViewById(R.id.desc)


        fun bind(blogs: Blogs)
        {
            title_view.text = blogs.title
            desc_view.text = blogs.description

        }

    }

}