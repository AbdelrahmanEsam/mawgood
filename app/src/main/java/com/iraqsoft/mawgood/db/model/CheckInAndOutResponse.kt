package com.iraqsoft.mawgood.db.model

data class CheckInAndOutResponse(
    val __v: Int,
    val _id: String,
    val branch: String,
    val checkInImage: String,
    val checkInTime: Long,
    val checkInUid: Boolean,
    val checkOutImage: String,
    val checkOutTime: Long,
    val checkOutUid: Boolean,
    val company: String,
    val createdAt: String,
    val date: String,
    val deleted: Boolean,
    val editable: Boolean,
    val edited: Boolean,
    val limitedHoliday: Boolean,
    val locations: List<Any>,
    val notes: String,
    val paid: Boolean,
    val realCheckInTime: Long,
    val realCheckOutTime: Long,
    val status: String,
    val updatedAt: String,
    val user: String,
    val workPeriod: Int
)