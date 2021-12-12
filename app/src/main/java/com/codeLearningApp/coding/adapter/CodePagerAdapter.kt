package com.codeLearningApp.coding.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.codeLearningApp.coding.model.Code
import com.codeLearningApp.coding.ui.code.CodeActivity
import com.codeLearningApp.coding.ui.code.CodeFragment
import com.codeLearningApp.coding.viewmodel.CodeViewModel
import kotlin.collections.ArrayList

class CodePagerAdapter(
    fm: FragmentManager,
    behavior: Int,
    context: Context,
    private val codeMap: MutableMap<String, ArrayList<Code>>
) :
    FragmentPagerAdapter(fm, behavior) {

    val viewModel = ViewModelProvider(context as CodeActivity).get(CodeViewModel::class.java)

    override fun getPageTitle(position: Int): CharSequence? {

        val lang = arrayListOf<String>()
        for(x in codeMap.keys) {
            lang.add(x)
        }

        return when (position) {
            0 -> lang[0].uppercase()
            1 -> lang[1].uppercase()
            2 -> lang[2].uppercase()
            else -> ""
        }
    }

    override fun getCount(): Int {
        return codeMap.size
    }

    override fun getItem(position: Int): Fragment {

        val lang = arrayListOf<String>()
        for(x in codeMap.keys) {
            lang.add(x)
        }

        return when(position) {
            0 -> {
                CodeFragment.newInstance(lang[0])
            }
            1 -> {
                CodeFragment.newInstance(lang[1])
            }
            2 -> {
                CodeFragment.newInstance(lang[2])
            }
            else -> {
                CodeFragment.newInstance("")
            }
        }


    }

}