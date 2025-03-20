package com.example.zodiac

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zodiac.data.Horoscope

class MainActivity : AppCompatActivity() {

    val horoscopeList = listOf(
        Horoscope("aries", R.string.horoscope_name_aries, R.string.horoscope_date_aries, R.drawable.aries_icon),
        Horoscope("taurus", R.string.horoscope_name_taurus, R.string.horoscope_date_taurus, R.drawable.taurus_icon),
        Horoscope("gemini", R.string.horoscope_name_gemini, R.string.horoscope_date_gemini, R.drawable.gemini_icon),
        Horoscope("cancer", R.string.horoscope_name_cancer, R.string.horoscope_date_cancer, R.drawable.cancer_icon),
        Horoscope("leo", R.string.horoscope_name_leo, R.string.horoscope_date_leo, R.drawable.leo_icon),
        Horoscope("virgo", R.string.horoscope_name_virgo, R.string.horoscope_date_virgo, R.drawable.virgo_icon),
        Horoscope("libra", R.string.horoscope_name_libra, R.string.horoscope_date_libra, R.drawable.libra_icon),
        Horoscope("scorpio", R.string.horoscope_name_scorpio, R.string.horoscope_date_scorpio, R.drawable.scorpio_icon),
        Horoscope("sagittarius", R.string.horoscope_name_sagittarius, R.string.horoscope_date_sagittarius, R.drawable.sagittarius_icon),
        Horoscope("capricorn", R.string.horoscope_name_capricorn, R.string.horoscope_date_capricorn, R.drawable.capricorn_icon),
        Horoscope("aquarius", R.string.horoscope_name_aquarius, R.string.horoscope_date_aquarius, R.drawable.aquarius_icon),
        Horoscope("pisces", R.string.horoscope_name_pisces, R.string.horoscope_date_pisces, R.drawable.pisces_icon)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}