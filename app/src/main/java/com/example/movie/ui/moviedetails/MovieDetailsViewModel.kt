package com.example.movie.ui.moviedetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movie.model.Movie
import com.example.movie.model.Resource
import com.example.movie.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

class MovieDetailsViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,

    ) : ViewModel() {
    private val _movie = MutableLiveData<Resource<Movie>>()
    private val compositeDisposable = CompositeDisposable()
    val movie: LiveData<Resource<Movie>>
        get() = _movie

    init {
        if (savedStateHandle.contains("movieId")) {
            val movieId = savedStateHandle.get<Long>("movieId")
            compositeDisposable.addAll(
                movieRepository.getMovieDetails(movieId!!)
                    .doOnSubscribe { _movie.value = Resource.Loading() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { movie -> _movie.value = Resource.Success(movie) },
                        { error -> _movie.value = Resource.Error(error.message!!) }
                    )

            )

        } else {
            _movie.value = Resource.Error("unable to get the argument!!")
        }

    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}