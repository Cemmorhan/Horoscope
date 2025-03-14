package com.example.horoscope

import android.content.ClipData.Item
import android.content.Intent
import android.media.session.MediaSessionManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zodiac.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailActivity : AppCompatActivity() {

    companion object{
        const val HOROSCOPE_ID = "HOROSCOPE_ID"
    }
    lateinit var nameTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var iconImageView: ImageView
    lateinit var descripcionTextView: TextView
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var progressBar: LinearProgressIndicator

    lateinit var horoscope: Horoscope
    var isFavorite = false
    lateinit var favoriteMenu: MenuItem
    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        session = SessionManager(this)

        val id =intent.getStringExtra(HOROSCOPE_ID)
        horoscope = Horoscope.findByID(id!!)
        isFavorite = session.isFavorite(id)
        initView()
        loadData()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_detail, menu)

        favoriteMenu = menu.findItem(R.id.action_favorite)
        setFavoriteIcon()
        return true
    }
// da funcionalidad a los botones del menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_share -> {
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                sendIntent.setType("text/plain")

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }
            R.id.action_favorite -> {
                isFavorite = !isFavorite
                session.setFavorite(horoscope.id,isFavorite)
                setFavoriteIcon()
                true
            }
            //dar funcion a la flecha de atras
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadData(){
        supportActionBar?.setTitle(horoscope.name)
        supportActionBar?.setSubtitle(horoscope.date)

        nameTextView.setText(horoscope.name)
        dateTextView.setText(horoscope.date)
        iconImageView.setImageResource(horoscope.icon)
        getHoroscopeluck("daily")
    }
    private fun initView(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nameTextView = findViewById(R.id.nameTextView)
        dateTextView = findViewById(R.id.dateTextView)
        iconImageView = findViewById(R.id.iconImageView)
        descripcionTextView = findViewById(R.id.descriptionTextView)
        progressBar = findViewById(R.id.progressBar)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.action_daily -> {
                    getHoroscopeluck("daily")
                    true
                }
                R.id.action_weekly -> {
                    getHoroscopeluck("weekly")
                    true
                }
                R.id.action_monthly -> {
                    getHoroscopeluck("monthly")
                    true
                }
                else -> false
            }
        }

    }
    private fun setFavoriteIcon() {
        if (isFavorite) {
            favoriteMenu.setIcon(R.drawable.ic_favorite_selected)
        } else {
            favoriteMenu.setIcon(R.drawable.ic_favorite)
        }
    }
    private fun getHoroscopeluck (period: String) {
        CoroutineScope (Dispatchers.IO).launch {
            var urlConnection: HttpsURLConnection? =null
            try {
                val url = URL("https://horoscope-app-api.vercel.app/api/v1/get-horoscope/$period?sign=${horoscope.id}&day=TODAY" )
                urlConnection = url.openConnection() as HttpsURLConnection
                if(urlConnection.responseCode==200){
                    val rl = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    var line: String?
                    val stringBuilder = StringBuilder()
                    while (rl.readLine().also { line = it } != null) {
                        stringBuilder.append(line + "\n")
                    }
                    val result = stringBuilder.toString()
                    // instanciar un objeto json
                    val jsonObject = JSONObject(result)
                    jsonObject.getJSONObject("data").getString("horoscope_data")
                    val horoscopeLuck = jsonObject.getJSONObject("data").getString("horoscope_data")
                    CoroutineScope(Dispatchers.Main).launch {
                        descripcionTextView.text = horoscopeLuck
                        progressBar.visibility = ProgressBar.GONE
                    }

                }
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                urlConnection?.disconnect()
            }
        }
    }
}