package com.codeLearningApp.coding.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codeLearningApp.coding.R
import com.codeLearningApp.coding.model.TopPick
import com.codeLearningApp.coding.ui.code.ProblemActivity
import com.codeLearningApp.coding.ui.code.CategoryActivity

class TopPickRecyclerAdapter(private val context: Context, private var topPickList: ArrayList<TopPick>) : RecyclerView.Adapter<TopPickRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_top_pick, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pick = topPickList[position]

        holder.itemView.findViewById<ImageView>(R.id.top_picks_image).setImageResource(pick.imageId)
        holder.itemView.findViewById<TextView>(R.id.top_pics_title).text = pick.title.trim()
        holder.itemView.findViewById<TextView>(R.id.top_picks_detail).text = pick.desc.trim()

        holder.itemView.setOnClickListener {
            when(position) {
                0 -> {
                    val intent = Intent(context, CategoryActivity::class.java)
                    context.startActivity(intent)
                }
                1 -> {
                    val intent = Intent(context, ProblemActivity::class.java)
                    intent.putExtra("CODE_TAG", "pattern")
                    intent.putExtra("CODE_TITLE", "All Patterns")
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

}