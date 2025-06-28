package com.app.literaku

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.yourapp.database.DatabaseHelper

class FragmentProfile : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    companion object {
        // Define arguments keys for passing data to the fragment
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi DatabaseHelper
        dbHelper = DatabaseHelper(requireContext())

        // Memastikan arguments tidak null sebelum mengambil nilai parameter
        arguments?.let { args ->
            val param1 = args.getString(ARG_PARAM1)
            val param2 = args.getString(ARG_PARAM2)
            // Gunakan param1 dan param2 jika diperlukan
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        emailEditText = rootView.findViewById(R.id.editTextEmail)  // EditText for email
        passwordEditText = rootView.findViewById(R.id.editTextPassword)  // EditText for password
        loginButton = rootView.findViewById(R.id.btnLogin)  // Login button

        // Set up login button click listener
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (dbHelper.isValidUser(email, password)) {
                    // If valid user, navigate to profile page
                    navigateToProfile()
                } else {
                    // Show error if credentials are incorrect
                    Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }

    // Navigate to profile page (This is where you can display the profile content)
    private fun navigateToProfile() {
        // Show a toast to simulate navigation (could be replaced with actual navigation logic)
        Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()

        // Example navigation to a new activity after login
        val intent = Intent(requireContext(), FragmentProfile::class.java)
        startActivity(intent)
    }
}
