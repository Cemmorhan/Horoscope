package com.example.horoscope

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val horoscopeList = listoOf(
        Horoscope("aries", R.drawable.aries_icon, "Aries", "March 21 - April 19"),
        Horoscope("taurus", R.drawable.taurus_icon, "Taurus", "April 20 - May 20"),
        Horoscope("gemini", R.drawable.gemini_icon, "Gemini", "May 21 - June 20"),
        Horoscope("cancer", R.drawable.cancer_icon, "Cancer", "June 21 - July 22"),
        Horoscope("leo", R.drawable.leo_icon, "Leo", "July 23 - August 22"),
        Horoscope("virgo", R.drawable.virgo_icon, "Virgo", "August 23 - September 22"),
        Horoscope("libra", R.drawable.libra_icon, "Libra", "September 23 - October 22"),
        Horoscope("scorpio", R.drawable.scorpio_icon, "Scorpio", "October 23 - November 21"),
        Horoscope("sagittarius", R.drawable.sagittarius_icon, "Sagittarius", "November 22 - December 21"),
        Horoscope("capricorn", R.drawable.capricorn_icon, "Capricorn", "December 22 - January 19"),
        Horoscope("aquarius", R.drawable.aquarius_icon, "Aquarius", "January 20 - February 18"),
        Horoscope("pisces", R.drawable.pisces_icon, "Pisces", "February 19 - March 20"),

    )

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView= findViewById(R.id.recyclerView)

        val adapter = HoroscopeAdapter(horoscopeList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}