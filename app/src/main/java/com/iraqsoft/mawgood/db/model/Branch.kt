package com.iraqsoft.mawgood.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selectedBranches")
data class Branch(
    @PrimaryKey(autoGenerate = true) val rowId: Int,
    val _id: String,
    val name: String,
    val radius: Int,
    var selected:Boolean=false,
    var code:String?
)