package com.example.horoscope

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

class HoroscopeAdapter(val item: List<Horoscope>): Adapter <HoroscopeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope, parent, false)
        return HoroscopeViewHolder(view)

    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: HoroscopeViewHolder, position: Int) {
        val horoscope = item[position]
        holder.render(horoscope)


    }
}
class HoroscopeViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val iconTextView:ImageView =view.findViewById(R.id.iconImageView)
    val nameTextView: TextView =view.findViewById(R.id.nameTextView)
    val dateTextView:TextView =view.findViewById(R.id.dateTextView)

    fun render(horoscope: Horoscope) {
        iconTextView.setImageResource(horoscope.icon)
        nameTextView.setText(horoscope.name)
        dateTextView.setText(horoscope.date)
    }

}