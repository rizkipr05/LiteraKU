package com.app.literaku

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class FragmentProfile : Fragment() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var logoutButton: Button
    private lateinit var editButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize SharedPreferencesManager
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        // Initialize views
        initializeViews(view)

        // Load user data
        loadUserData()

        // Set click listeners
        setClickListeners()

        return view
    }

    private fun initializeViews(view: View) {
        // Find TextViews based on your XML layout
        nameTextView = view.findViewById(R.id.nameTextView) // You need to add ID to your TextView in XML
        emailTextView = view.findViewById(R.id.emailTextView) // You need to add ID to your TextView in XML
        passwordTextView = view.findViewById(R.id.passwordTextView) // You need to add ID to your TextView in XML
        logoutButton = view.findViewById(R.id.btn_logout)
        editButton = view.findViewById(R.id.btn_edit)
    }

    private fun loadUserData() {
        // Check if user is logged in
        if (sharedPreferencesManager.isLoggedIn()) {
            // Get user data from SharedPreferences
            val userName = sharedPreferencesManager.getUserName() ?: "Unknown"
            val userEmail = sharedPreferencesManager.getUserEmail() ?: "Unknown"
            val userPassword = sharedPreferencesManager.getUserPassword() ?: "Unknown"

            // Display user data
            nameTextView.text = userName
            emailTextView.text = userEmail
            passwordTextView.text = "**********" // For security, show masked password
        } else {
            // If user is not logged in, redirect to login
            Toast.makeText(requireContext(), "Please log in first", Toast.LENGTH_SHORT).show()
            // You can redirect to login fragment/activity here
        }
    }

    private fun setClickListeners() {
        // Logout button click listener
        logoutButton.setOnClickListener {
            logout()
        }

        // Edit button click listener
        editButton.setOnClickListener {
            // Handle edit profile functionality and navigate to FragmentEditProfile
            openEditProfileFragment()
        }
    }

    private fun openEditProfileFragment() {
        // Create an instance of FragmentEditProfile
        val fragmentEditProfile = FragmentEditProfile()

        // Start a FragmentTransaction to replace FragmentProfile with FragmentEditProfile
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragmentEditProfile)  // Make sure to use the correct container ID
        transaction.addToBackStack(null)  // This allows users to navigate back to the previous fragment
        transaction.commit()
    }

    private fun logout() {
        // Clear user session
        sharedPreferencesManager.clearUserSession()

        // Show logout message
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()

        // Navigate back to login screen
        // You can customize this based on your app structure
        val intent = Intent(requireContext(), SplashActivity::class.java) // Assuming SplashActivity has login
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    // Method to refresh user data (call this if user data is updated)
    fun refreshUserData() {
        loadUserData()
    }
}
