package com.codeLearningApp.coding.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codeLearningApp.coding.R
import com.codeLearningApp.coding.model.Problem
import com.codeLearningApp.coding.ui.code.CodeActivity


class ProblemRecyclerAdapter(private val context: Context,
                             private var problemList: ArrayList<Problem>):
    RecyclerView.Adapter<ProblemRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_problem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val problem = problemList[position]

        val title = holder.itemView.findViewById<TextView>(R.id.problem_title)
        title.text = "" + problem.problemId + ". " + problem.problemTitle.trim()
        val desc = holder.itemView.findViewById<TextView>(R.id.problem_detail)
        if(problem.problemTags.contains("pattern"))
            desc.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)

        desc.text = problem.problemDetail

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CodeActivity::class.java)
            intent.putExtra("PROBLEM_ID", problem.problemId)
            intent.putExtra("PROBLEM_TITLE", problem.problemTitle)
            intent.putExtra("PROBLEM_DETAIL", problem.problemDetail)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = problemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}