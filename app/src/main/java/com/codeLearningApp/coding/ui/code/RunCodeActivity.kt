package com.codeLearningApp.coding.ui.code

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.codeLearningApp.coding.R
import com.codeLearningApp.coding.utils.OkHttpSingleton
import com.codeLearningApp.coding.utils.Parser
import okhttp3.*
import java.io.IOException


class RunCodeActivity : AppCompatActivity() {

    private lateinit var tvOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_code)

        val code = intent.getStringExtra("Code")

        val btnRunCode = findViewById<Button>(R.id.BTN_run_code)
        val etCode = findViewById<EditText>(R.id.ET_code)
        tvOutput = findViewById(R.id.TV_output)
        val etInput = findViewById<EditText>(R.id.ET_input)

        etCode.setText(getHighlightCode(code))

        btnRunCode.setOnClickListener {
            runCode(
                etCode.text.toString(),
                etInput.text.toString())
        }

        Handler(Looper.getMainLooper()).post {
            fun run() {
                etCode.setText(getHighlightCode(etCode.text.toString()))
                Thread.sleep(1500)
            }
            run()
        }

    }

    private fun getHighlightCode(code: String?): SpannableString {

        val spannableString = SpannableString(code)
        if(code == null) return spannableString

        val map = Parser.getPythonSpan(code!!)
        for (list in map["Keyword"]!!) {
            spannableString.setSpan(
                ForegroundColorSpan(getColor(R.color.keyword)),
                list.start,
                list.end,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        for (list in map["Comment"]!!) {
            spannableString.setSpan(
                ForegroundColorSpan(getColor(R.color.comment)),
                list.start,
                list.end,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        for (list in map["SemiColon"]!!) {
            spannableString.setSpan(
                ForegroundColorSpan(getColor(R.color.semicolon)),
                list.start,
                list.end,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannableString

    }


    private fun runCode(code: String, input: String): String {
        val url = "https://quiz-app-7663.herokuapp.com/run"
        val userId = "5" // get userId through firebase
        val formBody: RequestBody = FormBody.Builder()
            .add("userId", userId)
            .add("lang", "Python")
            .add("code", code)
            .add("input", input)
            .build()
        val request: Request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        OkHttpSingleton.getClient().newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        tvOutput.text = e.message ?: "Error"
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    var result = response.body!!.string()
                    runOnUiThread {
                        tvOutput.text = result
                    }
                }
            }
        )

        return ""

    }
}