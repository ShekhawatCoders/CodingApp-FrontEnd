package com.codeLearningApp.coding.ui.objQuestion

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.codeLearningApp.coding.R
import com.codeLearningApp.coding.viewmodel.ObjQuestionViewModel

class QuizResultFragment : Fragment() {

    private lateinit var sound: MediaPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_obj_question_result, container, false)

        val viewModel = ViewModelProvider(requireActivity()).get(ObjQuestionViewModel::class.java)

        val totalQuestions = viewModel.repository.size
        var correctQuestions = 0
        var attemptQuestions = 0

        for(answer in viewModel.repository.answers.keys) {
            if(viewModel.repository.answers[answer] ==
                    viewModel.repository.objRandomQuestionList[answer].answer)
                correctQuestions += 1
            attemptQuestions += 1
        }

        view.findViewById<TextView>(R.id.fragment_result_total_question)
                .text = "Total Questions: $totalQuestions"
        view.findViewById<TextView>(R.id.fragment_result_attempt_question)
                .text = "Attempt Questions: $attemptQuestions"
        view.findViewById<TextView>(R.id.fragment_result_correct_question)
                .text = "Correct Answers: $correctQuestions"
        view.findViewById<TextView>(R.id.fragment_result_wrong_question)
                .text = "Wrong Answers: ${attemptQuestions - correctQuestions}"

        sound = MediaPlayer.create(context, R.raw.audience_host)
        sound.setVolume(0.2f,0.2f)
        sound.start()

        return view

    }

    override fun onResume() {
        sound.start()
        super.onResume()
    }

    override fun onPause() {
        sound.pause()
        super.onPause()
    }

    override fun onDestroy() {
        sound.stop()
        sound.release()
        super.onDestroy()
    }

}