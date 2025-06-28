package com.app.literaku

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.Editable
import android.text.TextWatcher
import com.app.literaku.databinding.FragmentKategoriBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KategoriFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KategoriFragment : Fragment() {

    private var _binding: FragmentKategoriBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKategoriBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Deteksi teks yang diketik di kolom pencarian
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterKategori(s.toString())
            }
        })
    }
    private fun filterKategori(query: String) {
        val q = query.lowercase()

        val kategoriList = listOf(
            Pair(binding.kategoriNonfiction, "non-fiction"),
            Pair(binding.kategoriClassics, "classics"),
            Pair(binding.kategoriFantasy, "fantasy"),
            Pair(binding.kategoriYoungAdult, "young adult"),
            Pair(binding.kategoriCrime, "crime"),
            Pair(binding.kategoriHorror, "horror"),
            Pair(binding.kategoriScifi, "sci-fi"),
            Pair(binding.kategoriDrama, "drama")
        )

        for ((view, keyword) in kategoriList) {
            view.visibility = if (keyword.contains(q, ignoreCase = true)) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}