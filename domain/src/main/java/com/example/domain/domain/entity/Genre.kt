package com.example.domain.domain.entity

import android.os.Parcel
import android.os.Parcelable

data class Genre(
    val genre: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(genre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<com.example.domain.domain.entity.Genre> {
        override fun createFromParcel(parcel: Parcel): com.example.domain.domain.entity.Genre {
            return com.example.domain.domain.entity.Genre(parcel)
        }

        override fun newArray(size: Int): Array<com.example.domain.domain.entity.Genre?> {
            return arrayOfNulls(size)
        }
    }
}
