package com.iraqsoft.mawgood.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize



@Entity(tableName = "EmpNeedsToBeSynced")
@Parcelize
data class EmpNeedsToBeSynced(

    @PrimaryKey    val _id: String,

    val displayName: String? = null,

    val date:String?=null,

    val time:Long?,
): Parcelable