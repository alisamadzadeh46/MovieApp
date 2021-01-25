package com.example.movie.ui.landing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import kotlinx.android.synthetic.main.item_movie_footer_state.view.*

class MovieFootersStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MovieFootersStateAdapter.MovieFootersStateViewHolder>() {

    override fun onBindViewHolder(holder: MovieFootersStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieFootersStateViewHolder {
        return  MovieFootersStateViewHolder.create(parent, retry)
    }

    class MovieFootersStateViewHolder private constructor(itemView: View, retry: () -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            itemView.apply {
                if (loadState is LoadState.Error) {
                    tvErrorMessage.text = loadState.error.message
                }
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                tvErrorMessage.isVisible = loadState !is LoadState.Loading
            }
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): MovieFootersStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movie_footer_state, parent, false)
                return MovieFootersStateViewHolder(view, retry)
            }
        }

    }
}