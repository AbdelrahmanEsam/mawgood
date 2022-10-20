package com.iraqsoft.mawgood.util

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import com.iraqsoft.mawgood.data.model.Branch
import com.iraqsoft.mawgood.data.model.Location
import java.util.ArrayList





class Converters {

    @TypeConverter
    fun restoreList(listOfString: String?): List<Branch?>? {
        return Gson().fromJson(listOfString, object : TypeToken<List<Branch?>?>() {}.type)
    }


    @TypeConverter
    fun fromArrayList(list: List<Branch?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun locationToString(location: Location): String = Gson().toJson(location)


    @TypeConverter
    fun stringToLocation(string: String): Location = Gson().fromJson(string, Location::class.java)

}