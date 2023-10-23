package com.example.harrypottercharacterdisplay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class PotterAdapter(val potterList: MutableList<PotterCharacters>) : RecyclerView.Adapter<PotterAdapter.PotterViewHolder>() {
    class PotterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val potterImage : ImageView
        val nameTextView : TextView
        val houseTextView : TextView

        init {
            potterImage = itemView.findViewById<ImageView>(R.id.charImg)
            nameTextView = itemView.findViewById<TextView>(R.id.name)
            houseTextView = itemView.findViewById<TextView>(R.id.house)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PotterAdapter.PotterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.harry_potter_item, parent, false)

        return PotterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return potterList.size
    }

    override fun onBindViewHolder(holder: PotterAdapter.PotterViewHolder, position: Int) {
        val potty = potterList[position]
        holder.potterImage.setOnClickListener {
            Toast.makeText(
                holder.itemView.context, "Harry Potter Character at position $position is clicked",
                Toast.LENGTH_SHORT
            ).show()
        }

        Glide.with(holder.itemView)
            .load(potty.image)
            .centerCrop()
            .into(holder.potterImage)
        holder.nameTextView.text = potty.name
        holder.houseTextView.text = potty.house

    }
}