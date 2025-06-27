package com.app.literaku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.yourapp.database.DatabaseHelper
import com.app.literaku.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FragmentSignUp : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var fullNameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var fullNameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var confirmPasswordInputLayout: TextInputLayout
    private lateinit var dbHelper: DatabaseHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        // Initialize the database helper
        dbHelper = DatabaseHelper(requireContext())

        // Bind views
        fullNameEditText = binding.fullNameEditText
        emailEditText = binding.emailEditText
        passwordEditText = binding.passwordEditText
        confirmPasswordEditText = binding.confirmPasswordEditText
        fullNameInputLayout = binding.fullNameInputLayout
        emailInputLayout = binding.emailInputLayout
        passwordInputLayout = binding.passwordInputLayout
        confirmPasswordInputLayout = binding.confirmPasswordInputLayout


        // Handle the sign-up button click
        binding.signUpButton.setOnClickListener {
            validateInputs()
        }

        // Return the root view of the fragment
        return binding.root
    }


    private fun validateInputs() {
        val fullName = fullNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        // Validation for empty fields
        if (fullName.isEmpty()) {
            fullNameInputLayout.error = "Nama Lengkap tidak boleh kosong"
            return
        } else {
            fullNameInputLayout.error = null
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.error = "Email tidak valid"
            return
        } else {
            emailInputLayout.error = null
        }

        if (password.isEmpty()) {
            passwordInputLayout.error = "Password tidak boleh kosong"
            return
        } else {
            passwordInputLayout.error = null
        }

        if (confirmPassword.isEmpty() || confirmPassword != password) {
            confirmPasswordInputLayout.error = "Password konfirmasi tidak cocok"
            return
        } else {
            confirmPasswordInputLayout.error = null
        }

        // Check if the email already exists
        if (dbHelper.isEmailExists(email)) {
            emailInputLayout.error = "Email sudah terdaftar"
            return
        }

        // Insert user into the database
        val result = dbHelper.insertUser(fullName, email, password)
        if (result > 0) {
            Toast.makeText(requireContext(), "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
            navigateToSignIn(email, password)  // Navigate to SignIn with user data
        } else {
            Toast.makeText(requireContext(), "Pendaftaran gagal", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to navigate to FragmentSignIn
    private fun navigateToSignIn(email: String, password: String) {
        val bundle = Bundle()
        bundle.putString("userEmail", email)
        bundle.putString("userPassword", password)

        val fragment = FragmentSignin()
        fragment.arguments = bundle  // Passing the data to the SignIn fragment

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Replace the current fragment with SignIn
            .addToBackStack(null) // Optional: Add to back stack
            .commit()
    }
}
