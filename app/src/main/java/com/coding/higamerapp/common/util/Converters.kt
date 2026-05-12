package com.coding.higamerapp.common.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


var showInterstitial = true


fun fromLangToAbbreviation(profileLang: Int?): String? {
    var lang : String? = ""
    when (profileLang) {
        0 -> lang = "ENG"
        1 -> lang = "FR"
        2 -> lang = "SPA"
        3 -> lang = "GER"
        4 -> lang = "ITA"
        5 -> lang = "PT"
        6 -> lang = "PL"
        7 -> lang = "RU"
        8 -> lang = "KR"
        9 -> lang = "CN"
        10 -> lang = "ARA"
        11 -> lang = null
    }
    return lang
}

object Converters {
    @TypeConverter
    fun fromStringToChampionsList(value: String?): List<Int?>? {
        val listType: Type = object : TypeToken<List<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromChampionsListToString(list: List<Int?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}