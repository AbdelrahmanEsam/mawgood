package com.iraqsoft.mawgood.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.iraqsoft.mawgood.util.Converters


@Entity(tableName = "Response")
@TypeConverters(Converters::class)
data class LoginCompanyResponse(
    @PrimaryKey(autoGenerate = true) val rowId: Int,
    val branches: List<Branch>,
    val currency: String,
    val displayName: String,
    val location: Location,
    val userId: String,
    val username: String
)