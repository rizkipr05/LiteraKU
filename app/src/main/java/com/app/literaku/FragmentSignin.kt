package com.app.literaku

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

            // Check for null or empty values before using them
            if (userEmail.isNullOrEmpty() || userPassword.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Invalid credentials received", Toast.LENGTH_SHORT).show()
            } else {
                // Optionally, show email and password in Toast for testing
                Toast.makeText(requireContext(), "Email: $userEmail, Password: $userPassword", Toast.LENGTH_SHORT).show()

                // Handle sign-in logic here (for now, we assume it's always successful)
                // Start HomeActivity after successful login
                loginUser(userEmail, userPassword)
            }
        }

        // Set click listener for the "Sign In" button
        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            // Ensure both email and password are entered
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Please enter valid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun loginUser(email: String, password: String) {
        // Validate the email and password (you can replace this with actual logic)
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // If credentials are valid, navigate to HomeActivity
            Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()

            // Navigate to HomeActivity
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)

            // Optionally, finish the current activity/fragment if you don't want the user to return to the sign-in page
            activity?.finish()
        } else {
            // If credentials are invalid
            Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }
}
