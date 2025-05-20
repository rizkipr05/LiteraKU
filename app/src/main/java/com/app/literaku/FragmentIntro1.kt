package com.app.literaku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentIntro1 : Fragment() {

    private lateinit var continueButton: Button
    private lateinit var skipButton: Button
    private lateinit var titleText: TextView
    private lateinit var descriptionText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intro1, container, false)

        // Initialize the UI elements using findViewById
        continueButton = view.findViewById(R.id.introcontinueButton)
        skipButton = view.findViewById(R.id.introskipButton) // Don't forget to add the skipButton in XML
        titleText = view.findViewById(R.id.titleText)
        descriptionText = view.findViewById(R.id.descriptionText) // Add this TextView in XML if needed

        // Set up the continue button to navigate to Fragment_Intro2
        continueButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, Fragment_Intro2())
                .addToBackStack(null)
                .commit()
        }

        // Optionally, add the skip functionality
        skipButton.setOnClickListener {
            // Skip logic, navigate to the next fragment or activity
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentIntro1().apply {
                arguments = Bundle().apply {
                    putString("param1", param1)
                    putString("param2", param2)
                }
            }
    }
}
