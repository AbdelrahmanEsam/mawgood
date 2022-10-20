package com.iraqsoft.mawgood.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//@Entity(tableName = "Users")
@Parcelize
data class UserModel (
    @PrimaryKey(autoGenerate = true) val rowId: Int,
    val id: Int?,
    val lang: String?,
    val name: String?,
    val phone: String?,
    val token: String?
) : Parcelable