package com.example.zodiac.utils

import android.content.Context

class SessionManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("zodiac_session", Context.MODE_PRIVATE)

    fun setFavoriteHoroscope(id: String){
        val editor = sharedPreferences.edit()
        editor.putString("FAVORITE_HOROSCOPE", id)
        editor.apply()
    }

    fun getFavoriteHoroscope(): String {
       return sharedPreferences.getString("FAVORITE_HOROSCOPE", "")!!
    }

}