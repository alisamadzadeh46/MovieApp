package com.example.movie.ui.cast


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie.model.Cast
import com.example.movie.model.ProfileImage
import com.example.movie.model.Resource
import com.example.movie.repository.MovieRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PhotoViewModel @AssistedInject constructor(
    movieRepository: MovieRepository,
    @Assisted private val castId: Long
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _photo = MutableLiveData<Resource<List<ProfileImage>>>()
    val photo: LiveData<Resource<List<ProfileImage>>>
        get() = _photo

    init {
        compositeDisposable.addAll(
            movieRepository.getPersonImage(castId)
                .doOnSubscribe { _photo.value = Resource.Loading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response -> _photo.value = Resource.Success(response) },
                    { error -> _photo.value = Resource.Error(error.message!!) }
                )
        )

    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(castId: Long): PhotoViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            castId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(castId) as T
            }

        }
    }
}