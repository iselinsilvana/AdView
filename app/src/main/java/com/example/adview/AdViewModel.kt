package com.example.adview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.adview.database.FavouriteAd
import com.example.adview.model.Ad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AdViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var adsRepository: AdsRepository
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

    // ka trenger eg denne til ?
    //private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //private val currentAd = Ad?
    //Henter repository
    lateinit var favouriteAds : LiveData<List<FavouriteAd?>>
    lateinit var favouriteIds : LiveData<List<Long>?>
    lateinit var allAds : MutableLiveData<List<Ad>?>

    fun vievModelConnectToDataSource() {
        adsRepository = AdsRepository(getApplication())
        favouriteAds = adsRepository.getAllFavouriteAds()
        favouriteIds = adsRepository.getAllFavouriteIds()
        allAds = adsRepository.getAllAds()
    }
    fun viewModelConnectToAllAds () : MutableLiveData<List<Ad>?>{
        adsRepository = AdsRepository(getApplication())
        return adsRepository.getAllAds()
    }

    fun addToFavourites(currentAd: Ad) {
        val newFavourite = FavouriteAd(currentAd.id, currentAd.description, currentAd.location, currentAd.price?.value)
        adsRepository.addToFavourites(newFavourite)
    }

    fun removeFromFavourites(currentAd: Ad) {
        adsRepository.removeFromFavourites(currentAd.id)
    }

}

