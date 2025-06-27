package com.app.literaku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.literaku.databinding.FragmentIntro1Binding
import com.app.literaku.FragmentIntro1
import com.example.yourapp.ui.signin.FragmentSignin

class FragmentIntro1 : Fragment() {

    private var _binding: FragmentIntro1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntro1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace((view.parent as View).id, Fragment_Intro2())
                .addToBackStack(null)
                .commit()
        }

        binding.btnSkip.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace((view.parent as View).id, FragmentSignin())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
