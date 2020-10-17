package com.example.apifetch.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize

@Parcelize
@Xml(name = "link", writeNamespaces = arrayOf("im=itunes.apple.com/rss"))
data class Link(
    @ColumnInfo(name = "duration")
    @PropertyElement(name = "im:duration ") var duration: String?,
    @ColumnInfo(name = "type")
    @Attribute(name = "type") var type: String?,
    @ColumnInfo(name = "href")
    @Attribute(name = "href") var href: String?
) : Parcelable