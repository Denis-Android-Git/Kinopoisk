package com.example.domain.domain.entity

import android.os.Parcel
import android.os.Parcelable

data class Season(
    val episodes: List<com.example.domain.domain.entity.Episode>?,
    val number: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(com.example.domain.domain.entity.Episode.CREATOR),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(number)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<com.example.domain.domain.entity.Season> {
        override fun createFromParcel(parcel: Parcel): com.example.domain.domain.entity.Season {
            return com.example.domain.domain.entity.Season(parcel)
        }

        override fun newArray(size: Int): Array<com.example.domain.domain.entity.Season?> {
            return arrayOfNulls(size)
        }
    }
}