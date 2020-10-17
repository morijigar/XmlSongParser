package com.example.apifetch.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tbl_songs")
@Xml(name = "entry", writeNamespaces = arrayOf("im=itunes.apple.com/rss"))
data class Entry(
    @ColumnInfo(name = "id")
    @PropertyElement(name = "id ")
    var id: String? = "",
    @ColumnInfo(name = "title") @PrimaryKey
    @PropertyElement(
        name = "title",
        converter = HtmlEscapeStringConverter::class
    ) var title: String="",
    @ColumnInfo(name = "image")
    @PropertyElement(name = "im:image") var imageUrl: String?,
    @ColumnInfo(name = "artist")
    @PropertyElement(
        name = "im:artist",
        converter = HtmlEscapeStringConverter::class
    ) var artist: String?,
    @ColumnInfo(name = "price")
    @PropertyElement(name = "im:price") var price: String?,
    @ColumnInfo(name = "content")
    @PropertyElement(
        name = "content",
        converter = HtmlEscapeStringConverter::class
    ) var content: String,
    @Embedded
    @Element var link: Link,
    @Embedded
    @Element var category: Category
) : Parcelable