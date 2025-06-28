package com.app.literaku

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.app.literaku.databinding.ActivityHomeBinding
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.graphics.Color
import android.graphics.Typeface



class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateNavbarSelection(R.id.layout_home)

        // Home
        binding.bottomNavigation.findViewById<LinearLayout>(R.id.layout_home).setOnClickListener {
            updateNavbarSelection(R.id.layout_home)
            binding.mainScrollView.visibility = View.VISIBLE
            supportFragmentManager.popBackStack(
                null,
                androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        // Kategori
        binding.bottomNavigation.findViewById<LinearLayout>(R.id.layout_categories).setOnClickListener {
            updateNavbarSelection(R.id.layout_categories)
            binding.mainScrollView.visibility = View.GONE
            val fragment = KategoriFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        // Riwayat
        binding.bottomNavigation.findViewById<LinearLayout>(R.id.layout_riwayat).setOnClickListener {
            updateNavbarSelection(R.id.layout_riwayat)
            binding.mainScrollView.visibility = View.GONE
            val fragment = RiwayatFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        // Notifikasi
        binding.bottomNavigation.findViewById<LinearLayout>(R.id.layout_notifikasi).setOnClickListener {
            updateNavbarSelection(R.id.layout_notifikasi)
            binding.mainScrollView.visibility = View.GONE
            val fragment = NotifikasiFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        // Profil
        binding.bottomNavigation.findViewById<LinearLayout>(R.id.layout_profil).setOnClickListener {
            updateNavbarSelection(R.id.layout_profil)
            binding.mainScrollView.visibility = View.GONE
            val fragment =FragmentProfile()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

    }

    private fun updateNavbarSelection(selectedId: Int) {
        val navItems = listOf(
            R.id.layout_home,
            R.id.layout_categories,
            R.id.layout_riwayat,
            R.id.layout_notifikasi,
            R.id.layout_profil
        )

        for (id in navItems) {
            val layout = findViewById<LinearLayout>(id)
            val icon = layout.getChildAt(0) as ImageView
            val label = layout.getChildAt(1) as TextView

            if (id == selectedId) {
                layout.setBackgroundColor(Color.parseColor("#E3F2FD")) // Biru terang
                icon.setColorFilter(Color.parseColor("#2196F3"))       // Biru utama
                label.setTextColor(Color.parseColor("#2196F3"))
                label.setTypeface(null, Typeface.BOLD)
            } else {
                layout.setBackgroundColor(Color.WHITE)
                icon.setColorFilter(Color.parseColor("#666666"))
                label.setTextColor(Color.parseColor("#666666"))
                label.setTypeface(null, Typeface.NORMAL)
            }
        }
    }
}