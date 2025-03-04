package com.example.horoscope

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detailActivity : AppCompatActivity() {

    companion object{
        const val HOROSCOPE_ID = "HOROSCOPE_ID"
    }
    lateinit var nameTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var iconImageView: ImageView
    lateinit var horoscope: Horoscope


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id =intent.getStringExtra(HOROSCOPE_ID)
        val horoscope = Horoscope.findByID(id!!)
        iniciarComponentes()
        loadData()

    }
    private fun loadData(){
        nameTextView.setText(horoscope.name)
        dateTextView.setText(horoscope.date)
        iconImageView.setImageResource(horoscope.icon)
    }
    fun iniciarComponentes(){
        nameTextView = findViewById(R.id.nameTextView)
        dateTextView = findViewById(R.id.dateTextView)
        iconImageView = findViewById(R.id.iconImageView)
    }
}