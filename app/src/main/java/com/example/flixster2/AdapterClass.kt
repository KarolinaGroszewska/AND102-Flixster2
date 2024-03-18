package com.example.flixster2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AdapterClass(private val dataList: ArrayList<TVShow>): RecyclerView.Adapter<AdapterClass.ViewHolderClass>() {

    var onItemClick: ((TVShow) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        val imageUrl = "https://image.tmdb.org/t/p/w500" + currentItem.dataImage
        Picasso.get().load(imageUrl).into(holder.rvImage)
        holder.rvTitle.text = currentItem.dataTitle
        holder.rvDescription.text = currentItem.dataDesc
        holder.itemView.setOnClickListener{v ->
            val intent = Intent(v.context, DetailActivity::class.java)
            intent.putExtra("show", currentItem)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvImage:ImageView = itemView.findViewById(R.id.image)
        val rvTitle:TextView = itemView.findViewById(R.id.title)
        val rvDescription:TextView = itemView.findViewById(R.id.description)
    }
}