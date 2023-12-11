package com.example.domain.domain.entity

import android.os.Parcel
import android.os.Parcelable

data class StaffItem(
    val kinopoiskId: Int,
    val description: String?,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val professionKey: String?,
    val professionText: String?,
    val staffId: Int,
    val sex: String?,
    val webUrl: String?,
    val name: String?
) : com.example.domain.domain.entity.BaseMediaItem() {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(kinopoiskId)
        parcel.writeString(description)
        parcel.writeString(nameEn)
        parcel.writeString(nameRu)
        parcel.writeString(posterUrl)
        parcel.writeString(professionKey)
        parcel.writeString(professionText)
        parcel.writeInt(staffId)
        parcel.writeString(sex)
        parcel.writeString(webUrl)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<com.example.domain.domain.entity.StaffItem> {
        override fun createFromParcel(parcel: Parcel): com.example.domain.domain.entity.StaffItem {
            return com.example.domain.domain.entity.StaffItem(parcel)
        }

        override fun newArray(size: Int): Array<com.example.domain.domain.entity.StaffItem?> {
            return arrayOfNulls(size)
        }
    }
}