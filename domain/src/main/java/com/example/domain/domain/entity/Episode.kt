package com.example.domain.domain.entity

import android.os.Parcel
import android.os.Parcelable

data class Episode(
    val episodeNumber: Int,
    val nameEn: String?,
    val nameRu: String?,
    val releaseDate: String?,
    val seasonNumber: Int,
    val synopsis: Any?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        null
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(episodeNumber)
        parcel.writeString(nameEn)
        parcel.writeString(nameRu)
        parcel.writeString(releaseDate)
        parcel.writeInt(seasonNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<com.example.domain.domain.entity.Episode> {
        override fun createFromParcel(parcel: Parcel): com.example.domain.domain.entity.Episode {
            return com.example.domain.domain.entity.Episode(parcel)
        }

        override fun newArray(size: Int): Array<com.example.domain.domain.entity.Episode?> {
            return arrayOfNulls(size)
        }
    }
}