package com.lifetimelearner.quizapp.adapter

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.utils.Parser
import com.lifetimelearner.quizapp.ui.code.CodeActivity
import com.lifetimelearner.quizapp.viewmodel.CodeViewModel


class CodeRecyclerAdapter(val context: Context, private val ARG_PARAM1: String):
    RecyclerView.Adapter<CodeRecyclerAdapter.ViewHolder>() {

    val viewModel = ViewModelProvider(context as CodeActivity).get(CodeViewModel::class.java)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_code, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val code = viewModel.repository.codeMap[ARG_PARAM1]!![position]

        val title = holder.itemView.findViewById<TextView>(R.id.code_title)
        title.text = " " + code.codeId + ". " + code.codeTitle.trim()

        val spannableString = SpannableString(code.codeCode)

        when (code.codeLang.toLowerCase().trim()) {
            "c" -> {
                title.setCompoundDrawablesWithIntrinsicBounds(
                    context.getDrawable(R.drawable.icon_code),
                    null, null, null
                )
                val map = Parser.getCSpan(code.codeCode)
                for (list in map["Keyword"]!!) {
                    spannableString.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.keyword)),
                        list.start,
                        list.end,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                for (list in map["Comment"]!!) {
                    spannableString.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.comment)),
                        list.start,
                        list.end,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                for (list in map["SemiColon"]!!) {
                    spannableString.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.semicolon)),
                        list.start,
                        list.end,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                holder.itemView.findViewById<TextView>(R.id.code_code).text = spannableString
            }
            "java" -> {
                title.setCompoundDrawablesWithIntrinsicBounds(
                    context.getDrawable(R.drawable.icon_java),
                    null, null, null
                )
                val map = Parser.getJavaSpan(code.codeCode)
                for (list in map["Keyword"]!!) {
                    spannableString.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.keyword)),
                        list.start,
                        list.end,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                for (list in map["Comment"]!!) {
                    spannableString.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.comment)),
                        list.start,
                        list.end,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                for (list in map["SemiColon"]!!) {
                    spannableString.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.semicolon)),
                        list.start,
                        list.end,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                holder.itemView.findViewById<TextView>(R.id.code_code).text = spannableString
            }
            "python" -> {
                title.setCompoundDrawablesWithIntrinsicBounds(
                    context.getDrawable(R.drawable.icon_python),
                    null, null, null
                )
                val map = Parser.getPythonSpan(code.codeCode)
                for (list in map["Keyword"]!!) {
                    spannableString.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.keyword)),
                        list.start,
                        list.end,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                for (list in map["Comment"]!!) {
                    spannableString.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.comment)),
                        list.start,
                        list.end,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                for (list in map["SemiColon"]!!) {
                    spannableString.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.semicolon)),
                        list.start,
                        list.end,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                holder.itemView.findViewById<TextView>(R.id.code_code).text = spannableString
            }
            else -> {
                holder.itemView.findViewById<TextView>(R.id.code_code).text = code.codeCode
            }
        }

        val copyBtn = holder.itemView.findViewById<MaterialButton>(R.id.card_code_copy)
        val shareBtn = holder.itemView.findViewById<MaterialButton>(R.id.card_code_share)

        copyBtn.setOnClickListener {
            val clipboard =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", spannableString)
            clipboard.setPrimaryClip(clip)
            Snackbar.make(holder.itemView, "Copied Successfully", Snackbar.LENGTH_SHORT).show()
        }

        shareBtn.setOnClickListener {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, code.codeCode)
                // (Optional) Here we're setting the title of the content
                putExtra(Intent.EXTRA_TITLE, code.codeTitle)
            }, "Share With Friends")
            context.startActivity(share)
        }

    }

    override fun getItemCount() = viewModel.repository.codeMap[ARG_PARAM1]?.size ?: 0

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}