package com.example.apifetch.model

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize

@Parcelize
@Xml(name = "feed")
data class FeedXml(
    @PropertyElement(name = "id") var id: String?,
    @PropertyElement(name = "title") var title: String?,
    @Element(name = "entry") var entries: MutableList<Entry>
) : Parcelable