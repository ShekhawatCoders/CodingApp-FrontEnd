package com.lifetimelearner.quizapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.model.Blog
import com.lifetimelearner.quizapp.ui.blog.BlogActivity
import com.lifetimelearner.quizapp.ui.blog.WebViewActivity
import com.lifetimelearner.quizapp.ui.code.ProblemActivity
import com.lifetimelearner.quizapp.ui.code.ProgramActivity

class TopPickRecyclerAdapter(private val context: Context, private var topPickList: ArrayList<Blog>) : RecyclerView.Adapter<TopPickRecyclerAdapter.ViewHolder>() {

    // private val viewModel = ViewModelProvider(context as BlogActivity).get(BlogViewModel::class.java)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_top_picks, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pick = topPickList[position]

        holder.itemView.findViewById<ImageView>(R.id.top_picks_image).setImageResource(pick.blogId)
        holder.itemView.findViewById<TextView>(R.id.top_pics_title).text = pick.blogTitle.trim()
        holder.itemView.findViewById<TextView>(R.id.top_picks_detail).text = pick.blogDesc.trim()

        holder.itemView.setOnClickListener {
            when(position) {
                0 -> {
                    val intent = Intent(context, ProgramActivity::class.java)
                    context.startActivity(intent)
                }
                1 -> {
                    val intent = Intent(context, ProblemActivity::class.java)
                    intent.putExtra("CODE_TAG", "pattern")
                    intent.putExtra("CODE_TITLE", "All Patterns")
                    context.startActivity(intent)
                }
                else -> {
                    val intent = Intent(context, BlogActivity::class.java)
                    intent.putExtra("BLOG_TAG", "resources")
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount() = topPickList.size

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