package com.example.adview.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adview.AdsRepository
import com.example.adview.R
import com.example.adview.database.FavouriteAd
import com.example.adview.database.FavouriteAdsDao
import com.example.adview.database.FavouriteAdsDao_Impl
import com.example.adview.database.FavouriteAdsDatabase
import com.example.adview.model.Ad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    class AdViewHolder( v: View) : RecyclerView.ViewHolder(v)  {
        val tvPrice: TextView = v.findViewById(R.id.tv_item_price)
        val tvPlace: TextView = v.findViewById(R.id.tv_item_place)
        val tvTitle: TextView = v.findViewById(R.id.tv_item_title)
        val ivImage: ImageView = v.findViewById(R.id.iv_item_photo)
        val tglHeart: ToggleButton = v.findViewById(R.id.btn_favourite)

        //val database = FavouriteAdsDatabase.getInstance(itemView.context)

        fun bind(currentAd: Ad, holder: AdViewHolder) {
            val dec = DecimalFormat("#,###.##")
            val price = if (currentAd.price == null) {
                "no price info"
            } else {
                "${dec.format(currentAd.price.value)} kr"
            }
            tvPrice.text = price
            tvPlace.text = currentAd.location ?: "no location"
            tvTitle.text = currentAd.description ?: "no description"
            Glide.with(holder.ivImage.context)
                .load("https://images.finncdn.no/dynamic/480x360c/" + currentAd.image?.url).centerCrop().into(ivImage)

/*            var isAdInDb = false
            // Check if item is in favourites database or not
            CoroutineScope(Dispatchers.IO).launch {
                isAdInDb = ( database.favouriteAdsDao.isAdInDatabase(currentAd.id) > 0)
                Log.i("TEST!", "ad nr ${currentAd.id} is favourite: $isAdInDb")
            }
            tglHeart.isChecked = isAdInDb*/

            tglHeart.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val newFavourite = FavouriteAd(currentAd.id, currentAd.description, currentAd.location, currentAd.price?.value)
                    // The toggle is enabled
                    AdsRepository.addToFavourites(newFavourite)
                    //val database = FavouriteAdsDatabase.getInstance(itemView.context)
                   /* CoroutineScope(Dispatchers.IO).launch {
                        database.favouriteAdsDao.insert(newFavourite) }*/
                    Toast.makeText(itemView.context, "Favourite", Toast.LENGTH_SHORT).show()
                } else {
                    // The toggle is disabled
                    //TODO Når eg r i vis-kun-favoritter-modus, delete favouriteAd, eller er currentAd då eit objekt av klassen Favourite add?(trur det. trenger ikkje å endre noko)
                    AdsRepository.removeFromFavourites(currentAd)
                    CoroutineScope(Dispatchers.IO).launch {
                        database.favouriteAdsDao.delete(currentAd.id)
                    }
                    Toast.makeText(itemView.context, "Not favourite", Toast.LENGTH_SHORT).show()
                }
            }


            /*
        // usikker på om eg kjem til å trenge denne
        companion object {
            private val ITEM_KEY = "ITEM"
        }*/
        }
    }

}