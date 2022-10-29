package com.muhammad.movieapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.muhammad.movieapp.DetailsActivity
import com.muhammad.movieapp.databinding.FragmentSearchBinding
import com.muhammad.movieapp.ui.adapter.MoviesAdapter
import com.muhammad.movieapp.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val moviesViewModel: MoviesViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = MoviesAdapter(MoviesAdapter.MovieClickListener {
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra("movieId", it.id)
            startActivity(intent)
        })

        with(binding) {
            resultsRecyclerView.adapter = adapter

            imageViewSearch.setOnClickListener {
                val searchQuery = editTextSearch.text.toString()

                if (searchQuery.isNotEmpty()) {
                    moviesViewModel.searchMovies(searchQuery)
                } else {
                    editTextSearch.error = "Write the movie name"
                }
            }

            editTextSearch.setOnEditorActionListener { _, _, _ ->
                imageViewSearch.performClick()
            }
        }

        moviesViewModel.searchResult.observe(viewLifecycleOwner) {
            adapter.moviesResponse = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}