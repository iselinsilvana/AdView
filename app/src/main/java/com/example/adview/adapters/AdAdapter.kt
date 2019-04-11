package com.example.adview.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adview.R
import com.example.adview.model.Ad
import java.text.DecimalFormat

class AdAdapter(private val adList: List<Ad>) :
        RecyclerView.Adapter<AdAdapter.AdViewHolder>() {


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return AdViewHolder(v)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = adList.size

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val currentAd = adList[position]
        holder.bind(currentAd, holder)
    }

    class AdViewHolder( v: View) : RecyclerView.ViewHolder(v) {
        val tvPrice : TextView = v.findViewById(R.id.tv_item_price)
        val tvPlace : TextView = v.findViewById(R.id.tv_item_place)
        val tvTitle : TextView = v.findViewById(R.id.tv_item_title)
        val ivImage : ImageView = v.findViewById(R.id.iv_item_photo)

        fun bind(currentAd : Ad,  holder: AdViewHolder) {
            val dec = DecimalFormat("#,###.##")
            val price = if (currentAd.price == null) {
                "no price info"
            }
            else {
               "${dec.format( currentAd.price.value )} kr"
            }
            tvPrice.text = price
            tvPlace.text = currentAd.location ?: "no location"
            tvTitle.text = currentAd.description ?: "no description"
            Glide.with(holder.ivImage.context).load("https://images.finncdn.no/dynamic/480x360c/" + currentAd.image?.url).centerCrop().into(ivImage)
        }
        /*
        // usikker på om eg kjem til å trenge denne
        companion object {
            private val ITEM_KEY = "ITEM"
        }*/
    }

}