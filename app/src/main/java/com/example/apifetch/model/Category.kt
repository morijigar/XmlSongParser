package com.example.apifetch.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize

@Parcelize
@Xml(name = "category")
data class Category(
    @ColumnInfo(name = "term")
    @Attribute(name = "term") var term: String?,
    @ColumnInfo(name = "scheme")
    @Attribute(name = "scheme") var scheme: String?,
    @ColumnInfo(name = "label")
    @Attribute(name = "label") var label: String?
) : Parcelable