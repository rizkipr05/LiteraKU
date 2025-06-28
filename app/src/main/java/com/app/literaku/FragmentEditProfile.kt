package com.app.literaku

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.yourapp.database.DatabaseHelper

class FragmentEditProfile : Fragment() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var dbHelper: DatabaseHelper

    // Views
    private lateinit var btnBack: ImageButton
    private lateinit var profileImageView: ImageView
    private lateinit var btnChangePhoto: Button
    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etCurrentPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnToggleCurrentPassword: ImageButton
    private lateinit var btnToggleNewPassword: ImageButton
    private lateinit var btnToggleConfirmPassword: ImageButton
    private lateinit var btnCancel: Button
    private lateinit var btnSave: Button

    // Password visibility flags
    private var isCurrentPasswordVisible = false
    private var isNewPasswordVisible = false
    private var isConfirmPasswordVisible = false

    // Current user data
    private var currentUserId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        // Initialize components
        initializeComponents(view)
        initializeViews(view)
        loadCurrentUserData()
        setClickListeners()

        return view
    }

    private fun initializeComponents(view: View) {
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
        dbHelper = DatabaseHelper(requireContext())
        currentUserId = sharedPreferencesManager.getUserId()
    }

    private fun initializeViews(view: View) {
        btnBack = view.findViewById(R.id.btnBack)
        profileImageView = view.findViewById(R.id.profileImageView)
        btnChangePhoto = view.findViewById(R.id.btnChangePhoto)
        etFullName = view.findViewById(R.id.etFullName)
        etEmail = view.findViewById(R.id.etEmail)
        etCurrentPassword = view.findViewById(R.id.etCurrentPassword)
        etNewPassword = view.findViewById(R.id.etNewPassword)
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword)
        btnToggleCurrentPassword = view.findViewById(R.id.btnToggleCurrentPassword)
        btnToggleNewPassword = view.findViewById(R.id.btnToggleNewPassword)
        btnToggleConfirmPassword = view.findViewById(R.id.btnToggleConfirmPassword)
        btnCancel = view.findViewById(R.id.btnCancel)
        btnSave = view.findViewById(R.id.btnSave)
    }

    private fun loadCurrentUserData() {
        // Load current user data and populate fields
        val userName = sharedPreferencesManager.getUserName() ?: ""
        val userEmail = sharedPreferencesManager.getUserEmail() ?: ""

        etFullName.setText(userName)
        etEmail.setText(userEmail)
    }

    private fun setClickListeners() {
        // Back button
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Change photo button (placeholder)
        btnChangePhoto.setOnClickListener {
            Toast.makeText(requireContext(), "Photo change feature coming soon", Toast.LENGTH_SHORT).show()
        }

        // Password visibility toggles
        btnToggleCurrentPassword.setOnClickListener {
            togglePasswordVisibility(etCurrentPassword, isCurrentPasswordVisible) { isCurrentPasswordVisible = it }
        }

        btnToggleNewPassword.setOnClickListener {
            togglePasswordVisibility(etNewPassword, isNewPasswordVisible) { isNewPasswordVisible = it }
        }

        btnToggleConfirmPassword.setOnClickListener {
            togglePasswordVisibility(etConfirmPassword, isConfirmPasswordVisible) { isConfirmPasswordVisible = it }
        }

        // Cancel button
        btnCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Save button
        btnSave.setOnClickListener {
            saveChanges()
        }
    }

    private fun togglePasswordVisibility(editText: EditText, isVisible: Boolean, updateFlag: (Boolean) -> Unit) {
        val newVisibility = !isVisible
        updateFlag(newVisibility)

        if (newVisibility) {
            // Show password
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            // Hide password
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        // Move cursor to end
        editText.setSelection(editText.text.length)
    }

    private fun saveChanges() {
        // Get input values
        val fullName = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val currentPassword = etCurrentPassword.text.toString()
        val newPassword = etNewPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        // Validate inputs
        if (!validateInputs(fullName, email, currentPassword, newPassword, confirmPassword)) {
            return
        }

        // Verify current password
        if (!dbHelper.verifyCurrentPassword(currentUserId, currentPassword)) {
            Toast.makeText(requireContext(), "Current password is incorrect", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if email is already taken by another user
        if (dbHelper.isEmailExistsForOtherUser(email, currentUserId)) {
            Toast.makeText(requireContext(), "Email is already taken by another user", Toast.LENGTH_SHORT).show()
            return
        }

        // Determine final password (new password if provided, otherwise current)
        val finalPassword = if (newPassword.isNotEmpty()) newPassword else currentPassword

        // Update user in database
        val isUpdated = dbHelper.updateUser(currentUserId, fullName, email, finalPassword)

        if (isUpdated) {
            // Update SharedPreferences with new data
            val updatedUser = com.example.yourapp.database.User(
                id = currentUserId,
                fullName = fullName,
                email = email,
                password = finalPassword
            )
            sharedPreferencesManager.saveUserSession(updatedUser)

            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        } else {
            Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(
        fullName: String,
        email: String,
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): Boolean {

        // Check if full name is empty
        if (fullName.isEmpty()) {
            etFullName.error = "Full name is required"
            etFullName.requestFocus()
            return false
        }

        // Check if email is empty
        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            return false
        }

        // Check email format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Please enter a valid email address"
            etEmail.requestFocus()
            return false
        }

        // Check if current password is empty
        if (currentPassword.isEmpty()) {
            etCurrentPassword.error = "Current password is required"
            etCurrentPassword.requestFocus()
            return false
        }

        // If new password is provided, validate it
        if (newPassword.isNotEmpty()) {
            // Check minimum password length
            if (newPassword.length < 6) {
                etNewPassword.error = "New password must be at least 6 characters"
                etNewPassword.requestFocus()
                return false
            }

            // Check if confirm password matches
            if (newPassword != confirmPassword) {
                etConfirmPassword.error = "Passwords do not match"
                etConfirmPassword.requestFocus()
                return false
            }
        } else {
            // If new password is empty, confirm password should also be empty
            if (confirmPassword.isNotEmpty()) {
                etConfirmPassword.error = "Please enter new password first"
                etNewPassword.requestFocus()
                return false
            }
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        // Close database helper if needed
    }
}