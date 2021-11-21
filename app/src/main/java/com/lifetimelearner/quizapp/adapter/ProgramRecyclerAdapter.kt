package com.lifetimelearner.quizapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.model.Program
import com.lifetimelearner.quizapp.ui.code.ProblemActivity
import com.lifetimelearner.quizapp.ui.question.SubQuestionActivity
import com.lifetimelearner.quizapp.ui.quiz.ObjQuizActivity


class ProgramRecyclerAdapter(private val context: Context,
                             private val programList: ArrayList<Program>,
                             private val type: Int):
    RecyclerView.Adapter<ProgramRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(type) {
            0,1 -> ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_program, parent, false))
            else -> ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_large_program, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val program = programList[position]

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

    override fun getItemCount() = programList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

}