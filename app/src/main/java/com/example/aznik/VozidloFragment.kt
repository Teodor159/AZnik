package com.example.aznik

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VozidloFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VozidloFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vozidlo, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageButtons = listOf(
            view.findViewById<ImageButton>(R.id.imageButton3),
            view.findViewById<ImageButton>(R.id.imageButton4),
            view.findViewById<ImageButton>(R.id.imageButton5),
            view.findViewById<ImageButton>(R.id.imageButton6),
            view.findViewById<ImageButton>(R.id.imageButton7),
            view.findViewById<ImageButton>(R.id.imageButton8),
            view.findViewById<ImageButton>(R.id.imageButton9),
            view.findViewById<ImageButton>(R.id.imageButton10),
            view.findViewById<ImageButton>(R.id.imageButton11),
            view.findViewById<ImageButton>(R.id.imageButton12),
            view.findViewById<ImageButton>(R.id.imageButton13)
        )
        val choiceImages = listOf(
            R.drawable.xylon1,
            R.drawable.xylon2,
            R.drawable.xylon3,
            R.drawable.fendt920,
            R.drawable.fendt930,
            R.drawable.challenger,
            R.drawable.claasaxion,
            R.drawable.claasxerion3800,
            R.drawable.fendtgt,
            R.drawable.claasxerion3800saddletrac,
            R.drawable.samochod
        )
        val choiceTexts = listOf("Choice 1", "Choice 2", "Choice 3", "Choice 4", "Choice 5", "Choice 6", "Choice 7", "Choice 8", "Choice 9", "Choice 10", "Choice 11")
        var selectedButton: ImageButton? = null
        imageButtons.forEachIndexed { index, imageButton ->
            imageButton.setOnClickListener {
                (activity as? FormActivity)?.updateFinalQuestionFragment(this::class.simpleName ?: "", index)

                if (selectedButton != null && selectedButton != imageButton) {
                    // Fade in the previously selected button and enable it
                    selectedButton?.animate()?.alpha(1.0f)?.duration = 300
                    selectedButton?.isEnabled = true
                }

                if (selectedButton != imageButton) {
                    // Fade out the other ImageButtons and disable them
                    imageButtons.forEach { otherImageButton ->
                        if (otherImageButton != imageButton) {
                            otherImageButton.animate().alpha(0.3f).duration = 300
                            otherImageButton.isEnabled = false
                        }
                    }
                    // Update the currently selected button
                    selectedButton = imageButton
                } else {
                    // If the user clicks the same button again, deselect it
                    selectedButton = null

                    // Fade in all the other ImageButtons and enable them
                    imageButtons.forEach { otherImageButton ->
                        otherImageButton.animate().alpha(1.0f).duration = 300
                        otherImageButton.isEnabled = true
                    }
                }
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VozidloFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VozidloFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}