package com.example.movie.ui.cast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.movie.R
import com.example.movie.model.Status
import com.example.movie.utils.toAge
import com.example.movie.utils.toStandarDateFormat
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_landing.*
import javax.inject.Inject
import kotlin.properties.Delegates

private const val CAST_ID = "castId"

@AndroidEntryPoint
class AboutFragment : Fragment(R.layout.fragment_about) {

    private var castId by Delegates.notNull<Long>()

    @Inject
    lateinit var aboutViewModelFactory: AboutViewModel.AssistedFactory

    private val aboutViewModel: AboutViewModel by viewModels {
        AboutViewModel.provideFactory(
            aboutViewModelFactory,
            castId
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            castId = it.getLong(CAST_ID)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AboutLayout.isEnabled = false

        aboutViewModel.cast.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { cast ->
                        tvBirthday.text =
                            if (!cast.birthday?.toStandarDateFormat()
                                    .isNullOrEmpty()
                            ) cast.birthday?.toStandarDateFormat() else getString(R.string.deathday)
                        tvAge.text =
                            if (!cast.birthday?.toAge().isNullOrEmpty ()) cast.birthday?.toAge() else getString(
                                R.string.deathday
                            )
                        tvPlaceOfBirth.text =
                            if (!cast.placeOfBirth.isNullOrEmpty()) cast.placeOfBirth else getString(
                                R.string.deathday
                            )
                        tvBiography.text =
                            if (!cast.biography.isNullOrEmpty()) cast.biography else getString(R.string.deathday)
                        tvKnownAs.text =
                            if (!cast.knowAs.isNullOrEmpty()) cast.knowAs.joinToString(" , ") else getString(
                                R.string.deathday
                            )
                        cast.deathday?.let { deathday ->
                            tvDeathDay.text = deathday.toStandarDateFormat()
                            IIDeathDay.visibility = View.VISIBLE
                        }
                    }
                    showLoading(false)
                }
                Status.ERROR -> {
                    showLoading(false)
                    Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> showLoading(true)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        AboutLayout.isRefreshing = isLoading
        if (!isLoading) {
            nsv.visibility = View.VISIBLE
        }
    }


    companion object {
        fun newInstance(castId: Long) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putLong(CAST_ID, castId)
                }
            }

    }

}