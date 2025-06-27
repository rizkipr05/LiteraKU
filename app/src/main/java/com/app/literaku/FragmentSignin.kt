package com.app.literaku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.literaku.databinding.FragmentSigninBinding

class FragmentSignin : Fragment() {

    private lateinit var binding: FragmentSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSigninBinding.inflate(inflater, container, false)

        // Retrieve email and password passed from FragmentSignUp
        arguments?.let {
            val userEmail = it.getString("userEmail")
            val userPassword = it.getString("userPassword")

            // Optionally, show email and password in Toast for testing
            Toast.makeText(requireContext(), "Email: $userEmail, Password: $userPassword", Toast.LENGTH_SHORT).show()
        }

        // Handle sign-in logic here, e.g., authenticate the user

        return binding.root
    }
}
