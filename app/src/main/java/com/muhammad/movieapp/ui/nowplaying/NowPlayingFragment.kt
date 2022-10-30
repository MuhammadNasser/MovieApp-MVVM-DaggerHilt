package com.muhammad.movieapp.ui.nowplaying

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
import com.muhammad.movieapp.databinding.FragmentNowPlayingBinding
import com.muhammad.movieapp.helpers.Constants.QUERY_PAGE_SIZE
import com.muhammad.movieapp.ui.adapter.MoviesAdapter
import com.muhammad.movieapp.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingFragment : Fragment() {

    private var _binding: FragmentNowPlayingBinding? = null
    private val moviesViewModel: MoviesViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var isLastPage = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = MoviesAdapter(MoviesAdapter.MovieClickListener {
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra("movieId", it.id)
            startActivity(intent)
        })

        binding.nowPlayingRecyclerView.apply {
            this.adapter = adapter
            addOnScrollListener(this@NowPlayingFragment.scrollListener)
        }

        with(moviesViewModel) {
            getNowPlayingMovies()

            nowPlayingMovies.observe(viewLifecycleOwner) {
                finishLoading()
                val totalPages = it.totalPages / QUERY_PAGE_SIZE + 2
                isLastPage = nowPlayingMoviesPage == totalPages
                adapter.moviesResponse = it
            }
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
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = !isLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                moviesViewModel.getNowPlayingMovies()
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