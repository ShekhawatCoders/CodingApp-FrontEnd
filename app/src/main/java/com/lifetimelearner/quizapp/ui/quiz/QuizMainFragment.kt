package com.lifetimelearner.quizapp.ui.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.adapter.ObjQuestionRecyclerAdapter
import com.lifetimelearner.quizapp.viewmodel.QuizViewModel

class QuizMainFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel
    private lateinit var adapter : ObjQuestionRecyclerAdapter
    private lateinit var questionProgress: LinearProgressIndicator
    private lateinit var viewPager: ViewPager2
    private lateinit var continueBtn: MaterialButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz_main, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)

        viewPager = view.findViewById(R.id.card_obj_question_view_pager)
        adapter = ObjQuestionRecyclerAdapter(requireActivity(), viewModel.repository.objRandomQuestionList)
        viewPager.adapter = adapter
        // turn off swipe action in viewpager
        viewPager.isUserInputEnabled = false

        questionProgress = view.findViewById(R.id.card_obj_question_question_progress_bar)
        val skipBtn = view.findViewById<MaterialButton>(R.id.quiz_main_skip_button)
        continueBtn = view.findViewById(R.id.quiz_main_continue_button)

        if(!viewModel.repository.testMode) {
            skipBtn.visibility = View.GONE
        }
        if(viewModel.repository.size == 1) {
            continueBtn.text = "Submit Quiz"
            continueBtn.icon = null
        }

        skipBtn.setOnClickListener {
            val answered = viewModel.repository.answers.containsKey(viewPager.currentItem)
            if(answered) {
                viewModel.repository.answers.remove(viewPager.currentItem)
            }
            showNextItem()
        }

        continueBtn.setOnClickListener {
            val answered = viewModel.repository.answers.containsKey(viewPager.currentItem)
            if(!answered) {
                Snackbar.make(view, "Please Select Option", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showNextItem()
        }

        return view

    }

    private fun showNextItem() {
        val item = viewPager.currentItem + 1
        val total = viewModel.repository.size
        if(item == total-1) {
            // last item
            continueBtn.text = "Submit Quiz"
            continueBtn.icon = null
        }
        if(item >= total) {
            // end quiz
            Navigation.findNavController(context as ObjQuizActivity, R.id.nav_host_quiz_fragment)
                .navigate(R.id.quizMainFragment_to_quizResultFragment)
            return
        }
        viewPager.currentItem = item
        // set progress bar
        questionProgress.progress = ((item+1)*100)/total
    }
}
