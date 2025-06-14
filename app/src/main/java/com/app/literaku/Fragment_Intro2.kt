package com.app.literaku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.literaku.databinding.FragmentIntro2Binding

class Fragment_Intro2 : Fragment() {

    private var _binding: FragmentIntro2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntro2Binding.inflate(inflater, container, false)

        // Logika untuk tombol Sign Up
        binding.btnSignUp.setOnClickListener {
            // Ganti fragment tanpa container statis
            val fragment = FragmentSign_up()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(android.R.id.content, fragment)  // Menggunakan android.R.id.content sebagai wadah
            transaction.addToBackStack(null)  // Tambahkan ke back stack agar bisa kembali ke fragment sebelumnya
            transaction.commit()
        }

        // Logika untuk tombol Sign In (jika dibutuhkan)
        binding.btnSignIn.setOnClickListener {
            // Ganti fragment ke FragmentSignin (login)
            val fragment = FragmentSignin()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(android.R.id.content, fragment)
            transaction.addToBackStack(null)  // Menambahkan ke back stack untuk navigasi kembali
            transaction.commit()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
