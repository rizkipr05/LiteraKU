package com.example.yourapp.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.yourapp.databinding.FragmentSigninBinding

class FragmentSignin : Fragment() {

    private lateinit var binding: FragmentSigninBinding
    private var userEmail: String? = null
    private var userPassword: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninBinding.inflate(inflater, container, false)

        // Retrieve the data passed from FragmentSignUp
        arguments?.let {
            userEmail = it.getString("userEmail")
            userPassword = it.getString("userPassword")
        }

        // Optionally, you can display the passed data for testing
        Toast.makeText(requireContext(), "Email: $userEmail, Password: $userPassword", Toast.LENGTH_LONG).show()

        // Handle the sign-in logic (if necessary)
        binding.signInButton.setOnClickListener {
            signInUser(userEmail, userPassword)
        }

        return binding.root
    }

    // Function to sign-in the user (just a placeholder for now)
    private fun signInUser(email: String?, password: String?) {
        if (email != null && password != null) {
            Toast.makeText(requireContext(), "Sign-In Successful: Email: $email", Toast.LENGTH_SHORT).show()
            // Proceed with sign-in logic (e.g., check credentials, etc.)
        } else {
            Toast.makeText(requireContext(), "Sign-In Failed", Toast.LENGTH_SHORT).show()
        }
    }
}
