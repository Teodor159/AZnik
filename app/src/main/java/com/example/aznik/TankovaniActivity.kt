package com.example.aznik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class TankovaniActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tankovani)

        val submitButton = findViewById<Button>(R.id.submit_button)
        val editText1 = findViewById<EditText>(R.id.tankovano_input)
        val editText2 = findViewById<EditText>(R.id.koncovy_stav_input)

        submitButton.setOnClickListener {
            val input1 = editText1.text.toString()
            val input2 = editText2.text.toString()

            // Process the input data, e.g., send it to a server or save it locally
        }
    }
}
