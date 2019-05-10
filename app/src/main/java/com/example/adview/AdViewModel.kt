package com.example.adview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.adview.adapters.AdAdapter
import com.example.adview.database.DatabaseAd
import com.example.adview.database.FavouriteAdsDatabase.Companion.getDatabase
import com.example.adview.domain.Ad
import kotlinx.coroutines.*

class AdViewModel(application: Application) : AndroidViewModel(application) {

    // viewModelJob allows us to cancel all coroutines started by this ViewModel.
    private var viewModelJob = SupervisorJob()
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

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val adsRepository = AdsRepository(database)

    val allAds:LiveData<List<Ad>?> = adsRepository.ads
    val favouriteAdsList = adsRepository.favouriteAds
    var currentAdList = MutableLiveData<List<Ad>?>()

    init {
        viewModelScope.launch {
            adsRepository.refreshAds(
            )
        }
    }


    fun changeAdList(isAllAdsCurrentlyShowing : Boolean) {
         if (!isAllAdsCurrentlyShowing) {
             currentAdList.postValue(favouriteAdsList.value)
         }
        else {
             currentAdList.postValue(allAds.value) }
        //Log.i("TEST!", "changeAdList called. current ad list size is ${currentAdList?.size}.")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // ka trenger eg denne til ?
    //private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //private val currentAd = Ad?
    //Henter repository
    //lateinit var favouriteAds : LiveData<List<DatabaseAd?>>
    //lateinit var favouriteIds : LiveData<List<Long>?>
   // lateinit var allAds : MutableLiveData<List<Ad>?>

/*    fun vievModelConnectToDataSource() {
        //adsRepository = AdsRepository(getApplication())
        favouriteAds = adsRepository.getAllFavouriteAds()
        favouriteIds = adsRepository.getAllFavouriteIds()
       // allAds = adsRepository.getAllAds()
    }
    fun viewModelConnectToAllAds () : MutableLiveData<List<Ad>?>{
        adsRepository = AdsRepository(getApplication())
        return adsRepository.getAllAds()
    }*/

    fun addToFavourites(currentAd: Ad) {
        val newFavourite = Ad(currentAd.id, currentAd.description, currentAd.location, currentAd.price, currentAd.image, isFavourite = 1)
        adsRepository.addToFavourites(newFavourite)
    }

    fun removeFromFavourites(currentAd: Ad) {
        val noLongerFavourite = Ad(currentAd.id, currentAd.description, currentAd.location, currentAd.price, currentAd.image, isFavourite = 0)
        adsRepository.removeFromFavourites(noLongerFavourite)
    }

}

