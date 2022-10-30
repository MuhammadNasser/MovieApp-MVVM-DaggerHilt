package com.muhammad.movieapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muhammad.movieapp.DetailsActivity
import com.muhammad.movieapp.databinding.FragmentSearchBinding
import com.muhammad.movieapp.helpers.Constants
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
    var searchQuery: String = ""
    var isLastPage = false
    var isScrolling = false

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
            resultsRecyclerView.apply {
                this.adapter = adapter
                addOnScrollListener(this@SearchFragment.scrollListener)
            }

            imageViewSearch.setOnClickListener {
                searchQuery = editTextSearch.text.toString()

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
            moviesViewModel.finishLoading()
            val totalPages = it.totalPages / Constants.QUERY_PAGE_SIZE + 2
            isLastPage = moviesViewModel.searchResultPage == totalPages
            adapter.moviesResponse = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = !isLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                moviesViewModel.searchMovies(searchQuery)
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }
}