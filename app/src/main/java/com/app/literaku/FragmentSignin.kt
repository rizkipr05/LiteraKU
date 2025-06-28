package com.app.literaku

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.literaku.databinding.FragmentSigninBinding
import com.example.yourapp.database.DatabaseHelper

class FragmentSignin : Fragment() {

    private lateinit var binding: FragmentSigninBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSigninBinding.inflate(inflater, container, false)

        // Initialize the database helper and shared preferences
        dbHelper = DatabaseHelper(requireContext())
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

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
        // Validate the email and password (now checking the database)
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Check if the email and password exist in the database and get user data
            val userData = dbHelper.getUserData(email, password)

            if (userData != null) {
                // If credentials are valid, save user session and navigate to HomeActivity
                sharedPreferencesManager.saveUserSession(userData)
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
        } else {
            // Handle empty fields
            Toast.makeText(requireContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show()
        }
    }
}