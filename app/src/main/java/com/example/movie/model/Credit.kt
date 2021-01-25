package com.example.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.example.movie.model.adapter.ZoneDateTimeAdapter
import org.threeten.bp.ZonedDateTime


@JsonClass(generateAdapter = true)
data class Credit(
    val cast: List<Cast>
)

@Parcelize
@JsonClass(generateAdapter = true)
data class Cast(
    val id: Long,
    @Json(name = "cast_id")
    val castId: Long?,
    @Json(name = "credit_id")
    val creditId: String?,
    val character: String?,
    val gender: Int?,
    val name: String,
    @Json(name = "profile_path")
    val profilePath: String?,
    val birthday: ZonedDateTime?,
    val deathday: ZonedDateTime?,
    @Json(name = "place_of_birth")
    val placeOfBirth: String?,
    val biography: String?,
    @Json(name = "known_for_department")
    val department: String?,
    @Json(name = "also_known_as")
    val knowAs: List<String>?
) : Parcelable

@JsonClass(generateAdapter = true)
data class Profile(
    val profiles: List<ProfileImage>
)

@JsonClass(generateAdapter = true)
data class ProfileImage(
    val width: Int?,
    val height: Int?,
    @Json(name = "file_path")
    val path: String?,
    @Json(name = "aspect_ration")
    val aspecRatio: Double?
)