package com.example.adview.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adview.AdViewModel
import com.example.adview.R
import com.example.adview.domain.Ad
import java.text.DecimalFormat

class AdAdapter(val viewModel: AdViewModel) :
        RecyclerView.Adapter<AdAdapter.AdViewHolder>() {

    // var adList: List<Ad>? = emptyList()
    var adList: List<Ad>? = emptyList()

    fun loadAds(newAds: List<Ad>?) {
        adList = newAds
        notifyDataSetChanged()
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return AdViewHolder(v)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = adList?.size ?: 0

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // TODO: må fikse at denne kan vere null, for det kan den jo
        val currentAd = adList!![position]
        holder.bind(currentAd, holder)

        holder.tglHeart.setOnClickListener {
            var state = holder.tglHeart.isChecked
            //savedAdsArray.put(idOfCurrentAd, state)
            if (state) {viewModel.addToFavourites(currentAd)
                Toast.makeText(it.context, "added to favourites", Toast.LENGTH_SHORT).show()
            }
            else {viewModel.removeFromFavourites(currentAd)
                Toast.makeText(it.context, "removed from favourites", Toast.LENGTH_SHORT).show()
            }
        }

/*        holder.tglHeart.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The toggle is enabled
                // val newFavourite = DatabaseAd(currentAd.id, currentAd.description, currentAd.location, currentAd.price?.value)
                viewModel.addToFavourites(currentAd)
                savedAdsArray.put(idOfCurrentAd, true)
                Log.i("TEST", "$idOfCurrentAd was added to the array.")
                // Toast.makeText(itemView.context, "Favourite", Toast.LENGTH_SHORT).show()
            } else {
                // The toggle is disabled
                viewModel.removeFromFavourites(currentAd)
                savedAdsArray.put(idOfCurrentAd, false)
                Log.i("TEST", "$idOfCurrentAd was removed from the array.")
                // Toast.makeText(itemView.context, "Not favourite", Toast.LENGTH_SHORT).show()
            }
        }*/
    }

    inner class AdViewHolder( v: View) : RecyclerView.ViewHolder(v)  {
        val tvPrice: TextView = v.findViewById(R.id.tv_item_price)
        val tvPlace: TextView = v.findViewById(R.id.tv_item_place)
        val tvTitle: TextView = v.findViewById(R.id.tv_item_title)
        val ivImage: ImageView = v.findViewById(R.id.iv_item_photo)
        val tglHeart: ToggleButton = v.findViewById(R.id.btn_favourite)


        fun bind(currentAd: Ad, holder: AdViewHolder) {
            val dec = DecimalFormat("#,###.##")
            val price = if (currentAd.price == null) {
                "no price info"
            } else {
                "${dec.format(currentAd.price)} kr"
            }
            tvPrice.text = price
            tvPlace.text = currentAd.location ?: "no location"
            tvTitle.text = currentAd.description ?: "no description"
            // TODO: finn ut kva som skjer om det ikkje er eit bilde
            Glide.with(holder.ivImage.context)
                .load("https://images.finncdn.no/dynamic/480x360c/" + currentAd.image).centerCrop().into(ivImage)
            tglHeart.isChecked = (currentAd.isFavourite != 0)


/*            var isAdInDb = false
            // Check if item is in favourites database or not
            CoroutineScope(Dispatchers.IO).launch {
                isAdInDb = ( database.favouriteAdsDao.isAdInDatabase(currentAd.id) > 0)
                Log.i("TEST!", "ad nr ${currentAd.id} is favourite: $isAdInDb")
            }
            tglHeart.isChecked = isAdInDb*/
/*

            tglHeart.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    addToFavourites(currentAd)
                    val newFavourite = DatabaseAd(currentAd.id, currentAd.description, currentAd.location, currentAd.price?.value)
                    // The toggle is enabled
                    Toast.makeText(itemView.context, "Favourite", Toast.LENGTH_SHORT).show()
                } else {
                    // The toggle is disabled
                     Toast.makeText(itemView.context, "Not favourite", Toast.LENGTH_SHORT).show()
                }
            }
*/


            /*
        // usikker på om eg kjem til å trenge denne
        companion object {
            private val ITEM_KEY = "ITEM"
        }*/
        }
    }
}