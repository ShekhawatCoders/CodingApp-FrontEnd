package com.lifetimelearner.quizapp.adapter

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.model.ObjQuestion
import com.lifetimelearner.quizapp.ui.quiz.ObjQuizActivity
import com.lifetimelearner.quizapp.viewmodel.QuizViewModel

class ObjQuestionRecyclerAdapter(private val context: Context, private var questionList: ArrayList<ObjQuestion>) : RecyclerView.Adapter<ObjQuestionRecyclerAdapter.ViewHolder>() {

    // private val viewModel = ViewModelProvider(context as BlogActivity).get(BlogViewModel::class.java)

    private val wrongSound = MediaPlayer.create(context, R.raw.wrong_answer_sound)
    private val correctSound = MediaPlayer.create(context, R.raw.correct_answer_sound)
    private val viewModel = ViewModelProvider(context as ObjQuizActivity).get(QuizViewModel::class.java)
    private val shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake_animation)
    private val lowRotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_low_animation)
    private val zoomAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_animation)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_obj_question, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val question = questionList[position]
        holder.itemView.findViewById<TextView>(R.id.card_obj_question).text = question.question.trim()

        val questionHint = holder.itemView.findViewById<TextView>(R.id.card_obj_question_hint)
        questionHint.text = "Question : ${position+1}/${questionList.size}"

        val optionA = holder.itemView.findViewById<TextView>(R.id.card_obj_option_a)
        optionA.text = question.optionA.trim()

        val optionB = holder.itemView.findViewById<TextView>(R.id.card_obj_option_b)
        optionB.text = question.optionB.trim()

        val optionC = holder.itemView.findViewById<TextView>(R.id.card_obj_option_c)
        optionC.text = question.optionC.trim()

        val optionD = holder.itemView.findViewById<TextView>(R.id.card_obj_option_d)
        optionD.text = question.optionD.trim()

        val explanation = holder.itemView.findViewById<TextView>(R.id.explanation)

        val answer = question.answer

        // perform click
        optionA.setOnClickListener {
            performClickOperation(1,position,answer,optionA,
                    optionB,optionC,optionD,true,explanation)
        }

        optionB.setOnClickListener {
            performClickOperation(2,position,answer,optionA,
                    optionB,optionC,optionD,true,explanation)
        }

        optionC.setOnClickListener {
            performClickOperation(3,position,answer,optionA,
                    optionB,optionC,optionD,true,explanation)
        }

        optionD.setOnClickListener {
            performClickOperation(4,position,answer,optionA,
                    optionB,optionC,optionD,true,explanation)
        }


        // handle device rotation
        when(viewModel.repository.answers[position]) {
            1 -> performClickOperation(1,position,answer,optionA,
                    optionB,optionC,optionD,false,explanation)
            2 -> performClickOperation(2,position,answer,optionA,
                    optionB,optionC,optionD,false,explanation)
            3 -> performClickOperation(3,position,answer,optionA,
                    optionB,optionC,optionD,false,explanation)
            4 -> performClickOperation(4,position,answer,optionA,
                    optionB,optionC,optionD,false,explanation)
        }


        explanation.setOnClickListener {

            val moreMsg = "Explanation : " + question.explanation
            val moreSpannableString = SpannableString(moreMsg)
            moreSpannableString.setSpan(ForegroundColorSpan(context.getColor(R.color.correct)),
                    0,
                    12,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            moreSpannableString.setSpan(ForegroundColorSpan(
                    context.getColor(R.color.toggleDark)),
                    13,
                    moreMsg.length,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

            val lessMsg = "See Explanation"

            if(explanation.text == lessMsg) {
                explanation.text = moreSpannableString
            } else {
                explanation.text = lessMsg
                explanation.setTextColor(context.getColor(R.color.correct))
            }
        }

    }

    private fun disableClick(optionA: TextView, optionB: TextView,
                             optionC: TextView, optionD: TextView) {

        optionA.isClickable = false
        optionB.isClickable = false
        optionC.isClickable = false
        optionD.isClickable = false

    }

    private fun performClickOperation(curr: Int, position: Int, answer: Int,
                                      optionA: TextView, optionB: TextView,
                                      optionC: TextView, optionD: TextView,
                                      sound: Boolean, explanation: TextView) {

        if(!viewModel.repository.testMode) {
            // disable buttons
            disableClick(optionA, optionB, optionC, optionD)
            // make explanation visible
            explanation.visibility = View.VISIBLE
        }

        when(viewModel.repository.answers[position]) {
            1 -> clearButton(optionA)
            2 -> clearButton(optionB)
            3 -> clearButton(optionC)
            4 -> clearButton(optionD)
        }

        // If pressed the same button
        if(curr == viewModel.repository.answers[position]) return

        // store answer
        viewModel.repository.answers[position] = curr


        when(curr) {
            1 -> {
                optionA.startAnimation(zoomAnimation)
                if(answer == 1) updateButton(optionA, true, sound)
                else updateButton(optionA, false, sound)
            }
            2 -> {
                optionB.startAnimation(zoomAnimation)
                if(answer == 2) updateButton(optionB, true, sound)
                else updateButton(optionB, false, sound)
            }
            3 -> {
                optionC.startAnimation(zoomAnimation)
                if(answer == 3) updateButton(optionC, true, sound)
                else updateButton(optionC, false, sound)
            }
            4 -> {
                optionD.startAnimation(zoomAnimation)
                if(answer == 4) updateButton(optionD, true, sound)
                else updateButton(optionD, false, sound)
            }
        }

    }

    private fun clearButton(option: TextView) {
        option.setCompoundDrawablesWithIntrinsicBounds(
                context.getDrawable(R.drawable.icon_radio_empty),
                null,
                null,
                null)
        option.background = context.getDrawable(R.drawable.rect_code_background)
    }

    private fun updateButton(option: TextView, correct: Boolean, sound: Boolean) {

        if(viewModel.repository.testMode) {
            option.setCompoundDrawablesWithIntrinsicBounds(
                    context.getDrawable(R.drawable.icon_radio_fill),
                    null,
                    null,
                    null)
            return
        }

        option.elevation = 5f

        // stop & prepare sound
        correctSound.stop()
        wrongSound.stop()
        correctSound.prepare()
        wrongSound.prepare()

        if(correct) {
            // correct option
            // make sound only once avoid when device rotate
            if(sound) {
                correctSound.start()
                option.startAnimation(lowRotateAnimation)
            }

            option.setCompoundDrawablesWithIntrinsicBounds(
                    context.getDrawable(R.drawable.right_mark),
                    null,
                    context.getDrawable(R.drawable.correct_answer_1),
                    null)
            option.background = context.getDrawable(R.drawable.rect_correct_background)
        } else {

            if(sound) {
                option.startAnimation(shakeAnimation)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    (context.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    wrongSound.start()
                }
            }

            option.setCompoundDrawablesWithIntrinsicBounds(
                    context.getDrawable(R.drawable.wrong_mark),
                    null,
                    context.getDrawable(R.drawable.wrong_answer_1),
                    null)

            option.background = context.getDrawable(R.drawable.rect_wrong_background)
        }

    }

    override fun getItemCount() = questionList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}