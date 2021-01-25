package com.example.movie.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.glid.GlideApp
import com.example.movie.model.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.layout_loading.*

const val MAX_CAST_COUNT = 10

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var castAdapter: CastAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ibBack.setOnClickListener {
            it.findNavController().popBackStack()
        }

        castAdapter = CastAdapter()

        rvCast.apply {
            isNestedScrollingEnabled = false
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    showLoading(false)
                    val movie = it.data
                    GlideApp.with(ivBackdrop)
                        .load("https://image.tmdb.org/t/p/original${movie?.backdropPath}")
                        .placeholder(R.drawable.image)
                        .error(R.drawable.broken)
                        .into(ivBackdrop)

                    GlideApp.with(ivPoster)
                        .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
                        .placeholder(R.drawable.image)
                        .error(R.drawable.broken)
                        .into(ivPoster)

                    tvTitle.text = movie?.title
                    if (movie?.genres != null && movie.genres.isNotEmpty()) {
                        val genres = movie.genres.joinToString(separator = " | ",
                            transform = { genre -> genre.name })

                        tvGenres.text = genres

                    } else {
                        tvGenres.visibility = View.GONE
                    }

                    if (movie?.runtime != null) {
                        tvRuntime.text = getString(R.string.format_runtime, movie.runtime)
                    } else {
                        tvRuntime.visibility = View.GONE
                    }

                    if (movie?.releaseDate != null && movie.releaseDate.isNotBlank()) {
                        tvReleaseDate.text = movie.releaseDate
                    } else {
                        tvReleaseDate.visibility = View.GONE
                    }

                    movie?.voteAverage?.let { rating ->
                        rbRating.rating = (rating / 2).toFloat()
                    }
                    tvVoteCount.text = movie?.voteCount.toString()
                    tvOverview.text = movie?.overview

                    val casts = movie?.credits?.cast
                    if (casts != null && casts.isNotEmpty()) {
                        val numberOffCast =
                            if (casts.size <= MAX_CAST_COUNT) casts.size else MAX_CAST_COUNT

                        castAdapter.submitList(casts.take(numberOffCast))
                    }


                }
                Status.ERROR -> {
                    showLoading(false)
                    Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    showLoading(true)
                }
            }
        })
    }

    private fun showLoading(isShow: Boolean) {
        loadingContainer.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}