package com.example.domain.domain.entity

import android.os.Parcel
import android.os.Parcelable

data class Country(
    val country: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<com.example.domain.domain.entity.Country> {
        override fun createFromParcel(parcel: Parcel): com.example.domain.domain.entity.Country {
            return com.example.domain.domain.entity.Country(parcel)
        }

        override fun newArray(size: Int): Array<com.example.domain.domain.entity.Country?> {
            return arrayOfNulls(size)
        }
    }
}
