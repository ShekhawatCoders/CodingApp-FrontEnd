package com.lifetimelearner.quizapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.model.Blog
import com.lifetimelearner.quizapp.ui.blog.WebViewActivity

class BlogRecyclerAdapter(private val context: Context, private var blogList: ArrayList<Blog>) : RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder>() {

    // private val viewModel = ViewModelProvider(context as BlogActivity).get(BlogViewModel::class.java)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_blog, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = blogList[position]
        holder.itemView.findViewById<TextView>(R.id.blog_title).text = blog.blogTitle.trim()
        holder.itemView.findViewById<TextView>(R.id.blog_desc).text = blog.blogDesc.trim()
        val chipGroup = holder.itemView.findViewById<ChipGroup>(R.id.blog_tag)
        val tokens = blog.blogTags.split(",")
        for(token in tokens) {
            token.trim()
            if(token.isEmpty() || token == "resources") continue
            val chip = Chip(context)
            chip.text = token
            chipGroup.addView(chip)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("BLOG_URL", blog.blogUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = blogList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun adapterNotify() {
        // blogList = viewModel.blogList
        notifyDataSetChanged()
    }

}