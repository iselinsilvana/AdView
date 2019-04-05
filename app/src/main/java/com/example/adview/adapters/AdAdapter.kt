package com.example.adview.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.adview.R
import com.example.adview.model.Ad

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
        holder.bind(currentAd)
    }

    class AdViewHolder( v: View) : RecyclerView.ViewHolder(v) {
        val tvPrice : TextView? = v.findViewById(R.id.tv_item_price)
        val tvPlace : TextView? = v.findViewById(R.id.tv_item_place)
        val tvTitle : TextView? = v.findViewById(R.id.tv_item_title)
        val ivImage : ImageView? = v.findViewById(R.id.iv_item_photo)

        fun bind(currentAd : Ad) {
            tvPrice?.text = currentAd.price.toString()
            tvPlace?.text = currentAd.place
            tvTitle?.text = currentAd.title
            // ivImage?.image (TODO) Finn ut korleis ein skal gjere dette
        }
        // usikker på om eg kjem til å trenge denne
        companion object {
            private val ITEM_KEY = "ITEM"
        }
    }


}