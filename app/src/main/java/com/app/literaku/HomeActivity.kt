package com.app.literaku

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.app.literaku.databinding.ActivityHomeBinding
import com.app.literaku.KategoriFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val kategoriButton = findViewById<LinearLayout>(R.id.layout_categories)

        kategoriButton.setOnClickListener {
            // Contoh: Pindah ke fragment
            val fragment = KategoriFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}