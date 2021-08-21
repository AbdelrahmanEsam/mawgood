package com.iraqsoft.mawgood.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize



@Entity(tableName = "selectedEmployees")
@Parcelize
data class GetResponseItem(
    @PrimaryKey    val _id: String,
    val branch: String,

    val company: String,

    val displayName: String,

    val image: String,

    val phone: String,

    var selected:Boolean,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var fingerPrint : ByteArray?

): Parcelable