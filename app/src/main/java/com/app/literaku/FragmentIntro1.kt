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

        // Menghubungkan elemen-elemen UI dengan id yang sesuai
        continueButton = view.findViewById(R.id.introcontinueButton)
        titleText = view.findViewById(R.id.introtitleText)
        descriptionText = view.findViewById(R.id.introdescriptionText)

        // Menghubungkan ke fragment 2 saat mengklik tombol continue
        continueButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, Fragment_Intro2())
                .addToBackStack(null)
                .commit()
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