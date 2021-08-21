package com.iraqsoft.mawgood.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selectedEmployees")
data class GetResponseItem(
    @PrimaryKey(autoGenerate = true) val rowId: Int,

    val _id: String,

    val branch: String,

    val company: String,

    val displayName: String,

    val image: String,

    val phone: String,

    var selected:Boolean,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var fingerPrint : ByteArray

){

}