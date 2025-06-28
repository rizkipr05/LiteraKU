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
    }

    private fun updateNavbarSelection(selectedId: Int) {
        val navItems = listOf(
            R.id.layout_home,
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