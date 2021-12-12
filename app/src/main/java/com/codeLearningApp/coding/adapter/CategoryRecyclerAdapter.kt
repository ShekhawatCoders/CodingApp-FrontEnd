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
import com.codeLearningApp.coding.model.Category
import com.codeLearningApp.coding.ui.code.ProblemActivity
import com.codeLearningApp.coding.ui.subQuestion.SubQuestionActivity
import com.codeLearningApp.coding.ui.objQuestion.ObjQuizActivity


class CategoryRecyclerAdapter(private val context: Context,
                              private val categoryList: ArrayList<Category>,
                              private val type: Int):
    RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(type) {
            0,1 -> ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_category, parent, false))
            else -> ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_large_category, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val program = categoryList[position]

        val image = holder.itemView.findViewById<ImageView>(R.id.program_image)
        val title = holder.itemView.findViewById<TextView>(R.id.program_title)

        when(type) {
            0 -> { // go to question
                holder.itemView.setOnClickListener {
                    val intent = Intent(context, SubQuestionActivity::class.java)
                    intent.putExtra("TAG", program.tag)
                    context.startActivity(intent)
                }
            }
            1 -> { // go to quiz
                holder.itemView.setOnClickListener {
                    val intent = Intent(context, ObjQuizActivity::class.java)
                    intent.putExtra("TAG", program.tag)
                    context.startActivity(intent)
                }
            }
            else -> { // go to problem
                holder.itemView.setOnClickListener {
                    val intent = Intent(context, ProblemActivity::class.java)
                    intent.putExtra("CODE_TAG", program.tag)
                    intent.putExtra("CODE_TITLE", program.title.trim())
                    context.startActivity(intent)
                }
            }
        }

        with(image) {
            setImageResource(program.image)
        }

        with(title) {
            text = program.title.trim()
            isSelected = true
        }

    }

    override fun getItemCount() = categoryList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

}