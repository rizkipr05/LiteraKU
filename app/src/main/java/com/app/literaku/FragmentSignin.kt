package com.app.literaku

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.literaku.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class FragmentSignin : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.signUpButton.setOnClickListener {
            val fullName = binding.fullNameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            if (validateInput(fullName, email, password, confirmPassword)) {
                registerUser(email, password)
            }
        }

        setupPasswordToggle()

        binding.signInLink.setOnClickListener {
            navigateToSignIn()
        }

        binding.googleSignUp.setOnClickListener {
            showToast("Fitur daftar dengan Google belum tersedia")
        }

        binding.facebookSignUp.setOnClickListener {
            showToast("Fitur daftar dengan Facebook belum tersedia")
        }

        return binding.root
    }

    private fun setupPasswordToggle() {
        binding.showPasswordIcon.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            binding.passwordEditText.inputType =
                if (isPasswordVisible) InputType.TYPE_CLASS_TEXT
                else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.passwordEditText.setSelection(binding.passwordEditText.text.length)
        }

        binding.showConfirmPasswordIcon.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            binding.confirmPasswordEditText.inputType =
                if (isConfirmPasswordVisible) InputType.TYPE_CLASS_TEXT
                else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.confirmPasswordEditText.setSelection(binding.confirmPasswordEditText.text.length)
        }
    }

    private fun validateInput(fullName: String, email: String, password: String, confirmPassword: String): Boolean {
        when {
            fullName.isEmpty() -> {
                showToast("Nama lengkap tidak boleh kosong")
                return false
            }
            email.isEmpty() -> {
                showToast("Email tidak boleh kosong")
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showToast("Format email tidak valid")
                return false
            }
            password.isEmpty() -> {
                showToast("Password tidak boleh kosong")
                return false
            }
            password.length < 6 -> {
                showToast("Password minimal 6 karakter")
                return false
            }
            password != confirmPassword -> {
                showToast("Password tidak cocok")
                return false
            }
        }
        return true
    }

    private fun registerUser(email: String, password: String) {
        binding.signUpButton.isEnabled = false

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                binding.signUpButton.isEnabled = true

                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("SignUp", "createUserWithEmail:success")
                    showToast("Pendaftaran berhasil! Selamat datang ${user?.email}")
                    navigateToSignIn()
                } else {
                    Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                    val errorMessage = when (task.exception?.message) {
                        "The email address is already in use by another account." ->
                            "Email sudah terdaftar, silakan gunakan email lain"
                        "The email address is badly formatted." ->
                            "Format email tidak valid"
                        else -> "Pendaftaran gagal: ${task.exception?.localizedMessage}"
                    }
                    showToast(errorMessage)
                }
            }
    }

    private fun navigateToSignIn() {
        val intent = Intent(requireContext(), FragmentSignin::class.java)
        startActivity(intent)
        activity?.finish() // Jika ingin hapus halaman sign up dari backstack
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
