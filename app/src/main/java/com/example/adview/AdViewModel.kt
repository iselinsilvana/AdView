package com.example.adview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    init {
        viewModelScope.launch {
            adsRepository.refreshAds()
        }
    }

    val allAds = adsRepository.ads

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // ka trenger eg denne til ?
    //private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //private val currentAd = Ad?
    //Henter repository
    lateinit var favouriteAds : LiveData<List<DatabaseAd?>>
    lateinit var favouriteIds : LiveData<List<Long>?>
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

/*    fun addToFavourites(currentAd: Ad) {
        val newFavourite = DatabaseAd(currentAd.id, currentAd.description, currentAd.location, currentAd.price, currentAd.image)
        adsRepository.addToFavourites(newFavourite)
    }

    fun removeFromFavourites(currentAd: Ad) {
        adsRepository.removeFromFavourites(currentAd.id)
    }*/

}

