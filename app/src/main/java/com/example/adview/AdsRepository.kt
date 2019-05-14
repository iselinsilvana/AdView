package com.example.adview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.adview.database.FavouriteAdsDatabase
import com.example.adview.database.asDomainModel
import com.example.adview.domain.Ad
import com.example.adview.domain.asDatabaseModel
import com.example.adview.model.asDatabaseModel
import com.example.adview.network.RetrofitFactory
import com.example.adview.network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdsRepository(private val database: FavouriteAdsDatabase) {

    private val webservice: RetrofitService = RetrofitFactory.makeRetrofitService()


    val favouriteAds: LiveData<List<Ad>?> = Transformations.map(database.favouriteAdsDao.getAllFavourites()) {
        it?.asDomainModel()
    }
    val ads: LiveData<List<Ad>?> = Transformations.map(database.favouriteAdsDao.getAllAds()) {
        it?.asDomainModel()
    }



    fun refreshAds() {
        CoroutineScope(Dispatchers.IO).launch {
            val allAdsRefreshed = webservice.getAds().await().body()
            database.favouriteAdsDao.insertAll(*allAdsRefreshed!!.asDatabaseModel())
        }
    }


    fun addToFavourites(newFavourite : Ad) {
        CoroutineScope(Dispatchers.IO).launch {
            database.favouriteAdsDao.update(newFavourite.asDatabaseModel())
        }
    }

    fun removeFromFavourites(adToRemove : Ad) {
        CoroutineScope(Dispatchers.IO).launch {
            database.favouriteAdsDao.update(adToRemove.asDatabaseModel())
        }
    }

}