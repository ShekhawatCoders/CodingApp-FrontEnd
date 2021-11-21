package com.lifetimelearner.quizapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.adapter.ProgramRecyclerAdapter
import com.lifetimelearner.quizapp.adapter.TopPickRecyclerAdapter
import com.lifetimelearner.quizapp.utils.GlobalData.getQuestionTopic
import com.lifetimelearner.quizapp.utils.GlobalData.getQuizTopic
import com.lifetimelearner.quizapp.utils.GlobalData.getTopPickList

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val quizList = getQuizTopic()
        val questionList = getQuestionTopic()

        val questionRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_question)
        val questionAdapter = ProgramRecyclerAdapter(requireActivity(), questionList, 0)
        questionRecyclerView.adapter = questionAdapter

        val quizRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_quiz)
        val quizAdapter = ProgramRecyclerAdapter(requireActivity(), quizList, 1)
        quizRecyclerView.adapter = quizAdapter

        val chip = view.findViewById<Chip>(R.id.top_pick_chip)
        val mLayoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL, false)
        val topPickViewPager = view.findViewById<RecyclerView>(R.id.view_pager_top_pics)
        val topPickList = getTopPickList()
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(topPickViewPager)
        val topPickAdapter = TopPickRecyclerAdapter(requireActivity(), topPickList)
        topPickViewPager.layoutManager = mLayoutManager
        topPickViewPager.adapter = topPickAdapter
        topPickViewPager.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(mLayoutManager)
                    val pos = mLayoutManager.getPosition(centerView!!)
                    chip.text = "${pos+1}/2"
                }
            }
        })

        return view
    }


}