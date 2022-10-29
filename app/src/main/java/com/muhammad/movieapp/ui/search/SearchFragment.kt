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

        with(moviesViewModel) {
            searchQuery.observe(viewLifecycleOwner) {
                searchMovies(it)
            }

            searchResult.observe(viewLifecycleOwner) {
                adapter.moviesResponse = it
            }
        }

        with(binding) {
            resultsRecyclerView.adapter = adapter

            imageViewSearch.setOnClickListener {
                val searchQuery = moviesViewModel.searchQuery.value
                searchQuery?.let {
                    if (it.isNotEmpty()) {
                        moviesViewModel.searchMovies(it)
                    } else {
                        editTextSearch.error = "Write the movie name"
                    }
                }
            }

            editTextSearch.setOnEditorActionListener { _, _, _ ->
                imageViewSearch.performClick()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}