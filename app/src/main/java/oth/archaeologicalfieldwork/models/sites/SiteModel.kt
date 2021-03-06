package oth.archaeologicalfieldwork.models.sites

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SiteModel(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var hasVisited: Boolean = false,
    var visitDate: String = "",
    var images: ArrayList<String> = ArrayList(),
    var location: Location = Location(),
    var isFavorite: Boolean = false
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 11f
) : Parcelable

