package com.app.literaku

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import android.widget.ImageView
import android.view.View

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

            // Menyembunyikan gambar saat pindah ke fragment
            val splashimageView = findViewById<ImageView>(R.id.splashimageView)
            splashimageView.setImageResource(R.drawable.gambar)


            // memberikan delay 3 detik sebelum pindah ke fragment intro
            Handler().postDelayed({
                splashimageView.visibility = View.GONE


                // Pindah ke IntroFragment setelah logo hilang
                supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, FragmentIntro1())  // Ganti dengan nama fragment yang sesuai
                    .commit()
           }, 3000) // ms = 3 detik


    }
}
