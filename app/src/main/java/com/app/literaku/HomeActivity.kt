package com.app.literaku

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.literaku.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle any logic for your HomeActivity here
    }
}