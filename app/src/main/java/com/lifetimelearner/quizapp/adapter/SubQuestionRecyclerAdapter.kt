package com.lifetimelearner.quizapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.card.MaterialCardView
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.model.SubQuestion

class SubQuestionRecyclerAdapter(private val context: Context, private var subQuestionList: ArrayList<SubQuestion>) : RecyclerView.Adapter<SubQuestionRecyclerAdapter.ViewHolder>() {

    // private val viewModel = ViewModelProvider(context as BlogActivity).get(BlogViewModel::class.java)

    private var prevHolder: ViewHolder? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_sub_question, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = subQuestionList[position]
        val tvQuestion = holder.itemView.findViewById<TextView>(R.id.card_sub_question)
        val tvAnswer = holder.itemView.findViewById<TextView>(R.id.card_sub_answer)
        val showAnswerHint = holder.itemView.findViewById<TextView>(R.id.card_sub_show_answer_hint)
        showAnswerHint.visibility = View.GONE

        tvQuestion.text = question.question.trim()
        tvAnswer.text = question.answer.trim()

        holder.itemView.setOnClickListener {

            if(prevHolder != null) {
                showAnswer(prevHolder!!)
            }
            if(prevHolder != holder) {
                prevHolder = holder
                showAnswer(holder)
            } else {
                prevHolder = null
            }
        }
    }

    private fun showAnswer(holder: ViewHolder) {

        val tvQuestion = holder.itemView.findViewById<TextView>(R.id.card_sub_question)
        val showAnswer = holder.itemView.findViewById<RelativeLayout>(R.id.card_sub_show_answer)
        val mainCard = holder.itemView.findViewById<MaterialCardView>(R.id.card_sub_main)
        val showAnswerHint = holder.itemView.findViewById<TextView>(R.id.card_sub_show_answer_hint)

        if(showAnswer.isVisible) {

            tvQuestion.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    context.getDrawable(R.drawable.expand_more),
                    null)

            showAnswerHint.visibility = View.GONE
            /*TransitionManager.beginDelayedTransition(mainCard,
                    AutoTransition())*/
            showAnswer.visibility = View.GONE
            mainCard.elevation = 0f
        } else {

            tvQuestion.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    context.getDrawable(R.drawable.expand_less),
                    null)

            showAnswerHint.text = "Answer"
            showAnswerHint.visibility = View.VISIBLE

            showAnswer.visibility = View.VISIBLE
            TransitionManager.beginDelayedTransition(mainCard,
                    AutoTransition())
            mainCard.elevation = 50f
        }

    }

    override fun getItemCount() = subQuestionList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}