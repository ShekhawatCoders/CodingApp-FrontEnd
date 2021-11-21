package com.lifetimelearner.quizapp.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.lifetimelearner.quizapp.model.Code
import com.lifetimelearner.quizapp.ui.code.CodeActivity
import com.lifetimelearner.quizapp.ui.code.CodeFragment
import com.lifetimelearner.quizapp.viewmodel.CodeViewModel
import java.util.*
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
            0 -> lang[0].toUpperCase(Locale.getDefault())
            1 -> lang[1].toUpperCase(Locale.getDefault())
            2 -> lang[2].toUpperCase(Locale.getDefault())
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