package com.example.adview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.adview.database.FavouriteAd
import com.example.adview.model.Ad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AdViewModel(adsRepository: AdsRepository, application: Application) : AndroidViewModel(application) {

    // viewModelJob allows us to cancel all coroutines started by this ViewModel.
    private var viewModelJob = Job()
    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //private val currentAd = Ad?
    private val favouriteAds = adsRepository.getAllFavouriteAds()
    private val favouriteIds = adsRepository.getAllFavouriteIds()
    private val allAds = adsRepository.getAllAds()

    fun addToFavourites(currentAd: Ad, adsRepository: AdsRepository) {
        val newFavourite = FavouriteAd(currentAd.id, currentAd.description, currentAd.location, currentAd.price?.value)
        adsRepository.addToFavourites(newFavourite)
    }

    fun removeFromFavourites(currentAd: Ad, adsRepository: AdsRepository) {
        adsRepository.removeFromFavourites(currentAd.id)
    }

}

/*
tglHeart.setOnCheckedChangeListener { _, isChecked ->
    if (isChecked) {
        val newFavourite = FavouriteAd(currentAd.id, currentAd.description, currentAd.location, currentAd.price?.value)
        // The toggle is enabled
        AdsRepository.addToFavourites(newFavourite)
        //val database = FavouriteAdsDatabase.getInstance(itemView.context)
        */
/* CoroutineScope(Dispatchers.IO).launch {
             database.favouriteAdsDao.insert(newFavourite) }*//*

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
*/
