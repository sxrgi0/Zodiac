package com.example.zodiac.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zodiac.R
import com.example.zodiac.data.Horoscope
import com.example.zodiac.data.HoroscopeProvider
import com.example.zodiac.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class DetailActivity : AppCompatActivity() {

    lateinit var nameTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var iconImageView: ImageView
    lateinit var horoscopeLuckTextView: TextView
    lateinit var progressBar: ProgressBar

    lateinit var session: SessionManager

    var isFavorite = false

    lateinit var horoscope: Horoscope

    lateinit var favoriteMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameTextView = findViewById(R.id.nameTextView)
        dateTextView = findViewById(R.id.dateTextView)
        iconImageView = findViewById(R.id.iconImageView)
        horoscopeLuckTextView = findViewById(R.id.horoscopeLuckTextView)
        progressBar = findViewById(R.id.progressBar)

        session = SessionManager(this)

        val id = intent.getStringExtra("HOROSCOPE_ID")!!

        horoscope = HoroscopeProvider.getById(id)!!

        isFavorite = session.getFavoriteHoroscope() == horoscope.id


        nameTextView.setText(horoscope.name)
        dateTextView.setText(horoscope.dates)
        iconImageView.setImageResource(horoscope.icon)

        getHoroscopeLuck()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_detail, menu)

        favoriteMenuItem = menu.findItem(R.id.menu_favorite)
        setFavoriteIcon()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_favorite -> {
                if(isFavorite){
                    session.setFavoriteHoroscope("")
                } else {
                    session.setFavoriteHoroscope(horoscope.id)
                }

                isFavorite = !isFavorite

                setFavoriteIcon()

                return true
            }
            R.id.menu_share -> {
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                sendIntent.setType("text/plain")

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    fun setFavoriteIcon(){
        if(isFavorite){
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_selected)
        } else {
            favoriteMenuItem.setIcon(R.drawable.ic_favorite)
        }
    }


    fun getHoroscopeLuck(){
        progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            val url = URL ("https://horoscope-app-api.vercel.app/api/v1/get-horoscope/daily?sign=${horoscope.id}")

            val urlConnection = url.openConnection() as HttpsURLConnection

            urlConnection.requestMethod = "GET"

            try {

                if(urlConnection.responseCode ==  HttpURLConnection.HTTP_OK){
                    val bufferedReader= BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val response = StringBuffer()
                    var inputLine: String? = null

                    while ((bufferedReader.readLine().also { inputLine = it }) != null) {
                        response.append(inputLine)
                    }
                    bufferedReader.close()

                    val result = JSONObject(response.toString()).getJSONObject("data").getString(("horoscope_data"))

                    CoroutineScope(Dispatchers.Main).launch {
                        progressBar.visibility = View.GONE
                        horoscopeLuckTextView.text = result
                    }

                } else {
                    Log.i("API", "Error en la llamada al API")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
        }

    }

}