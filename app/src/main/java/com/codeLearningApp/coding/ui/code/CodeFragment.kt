package com.codeLearningApp.coding.ui.code

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeLearningApp.coding.R
import com.codeLearningApp.coding.adapter.CodeRecyclerAdapter
import com.codeLearningApp.coding.viewmodel.CodeViewModel

private const val ARG_PARAM1 = "codeLanguage"

class CodeFragment : Fragment() {

    private var param1: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1) ?: ""
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_code, container, false)


        val recyclerView = view.findViewById<RecyclerView>(R.id.code_recycler_view)
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        val adapter = CodeRecyclerAdapter(requireActivity(), param1)
        recyclerView.adapter = adapter

        val viewModel = ViewModelProvider(requireActivity()).get(CodeViewModel::class.java)
        viewModel.repository.codeLoaded.observe(requireActivity(), {
            adapter.notifyDataSetChanged()
        })

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                CodeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
}