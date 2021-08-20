package com.iraqsoft.mawgood.db.model

data class WorkingDay(
    val _id: String,
    val day: Int,
    val endHour: Int,
    val endMinutes: Int,
    val startHour: Int,
    val startMinutes: Int
)