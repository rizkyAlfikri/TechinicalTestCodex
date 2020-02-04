package com.dicoding.picodiploma.techinicaltestcodex.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class IntListConverter {


    companion object{
        private val gson = Gson()

        @TypeConverter
        fun stringToIntegerList(data: String?): List<Int> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listInt = object : TypeToken<List<Int>>(){}.type

            return gson.fromJson(data, listInt)
        }

        @TypeConverter
        fun integerListToString(data: List<Int?>?): String {
            return gson.toJson(data)
        }
    }


}