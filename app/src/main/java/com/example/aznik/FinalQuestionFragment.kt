package com.example.aznik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.api.services.sheets.v4.Sheets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.aznik.GoogleSheetsHelper.sendDataToSheet


class FinalQuestionFragment : Fragment() {

    private lateinit var cerpackaSelectedChoice: TextView
    private lateinit var vozidloSelectedChoice: ImageView
    private lateinit var litersEditText: EditText
    private lateinit var finalStateEditText: EditText
    private lateinit var sendButton: Button

    private var sheetsService: Sheets? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_final_question, container, false)

        cerpackaSelectedChoice = view.findViewById(R.id.cerpacka_selected_choice)
        vozidloSelectedChoice = view.findViewById(R.id.vozidlo_selected_choice)
        litersEditText = view.findViewById(R.id.editTextLiters)
        finalStateEditText = view.findViewById(R.id.editTextFinalState)
        sendButton = view.findViewById(R.id.send_button)

        sendButton.setOnClickListener {
            sendDataToGoogleSheet()
        }

        return view
    }

    private fun sendDataToGoogleSheet() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val spreadsheetId = "1cke8YB2Q0BtpKyOk7-FFi0ymu1lM43SGlBq4s4VO_sg"
                val range = "Sheet1!A1:D1" // Change this to your desired sheet and range
                val data = listOf(
                    listOf(
                        cerpackaSelectedChoice.text.toString(),
                        "Resource_ID", // Replace with the resource ID of the selected image
                        litersEditText.text.toString(),
                        finalStateEditText.text.toString()
                    )
                )

                GoogleSheetsHelper.sendDataToSheet(requireContext(), spreadsheetId, range, data)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun updateSelectedChoice(fragmentClassName: String, choice: Int) {
        when (fragmentClassName) {
            CerpackaFragment::class.simpleName -> {
                cerpackaSelectedChoice.text = choice.toString()
            }
            VozidloFragment::class.simpleName -> {
                vozidloSelectedChoice.setImageResource(choice)
            }
        }
    }
}
