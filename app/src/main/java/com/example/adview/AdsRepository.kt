package com.example.adview

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.adview.database.FavouriteAd
import com.example.adview.database.FavouriteAdsDao
import com.example.adview.database.FavouriteAdsDatabase
import com.example.adview.model.Ad
import com.example.adview.network.RetrofitFactory
import com.example.adview.network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AdsRepository(application: Application) {

    private val webservice: RetrofitService = RetrofitFactory.makeRetrofitService()
    private val webResponse: List<Ad>? = getAdsFromWeb()

    private val databaseDao: FavouriteAdsDao = FavouriteAdsDatabase.getInstance(application).favouriteAdsDao
    private val favouriteAdListLiveData: LiveData<List<FavouriteAd?>> = databaseDao.getFavouriteAds()
    private val favouriteIdListLiveData: LiveData<List<Long>?> = databaseDao.getAllIds()

/*
    init {
        webservice = RetrofitFactory.makeRetrofitService()
        webResponse = getAdsFromWeb()

        val database = FavouriteAdsDatabase.getInstance(application).favouriteAdsDao
        databaseDao = database.favouriteAdsDao
        // Bør desse to vere i dispatchers.IO, eller blir det gjort når ein kaller det i VM?
        favouriteAdListLiveData = databaseDao.getFavouriteAds()
        favouriteIdListLiveData = databaseDao.getAllIds()
        }
*/


    fun getAllAds() : List<Ad>?{
        return webResponse
    }

    fun getAllFavouriteAds() : LiveData<List<FavouriteAd?>>{
        return favouriteAdListLiveData
    }

    fun getAllFavouriteIds() : LiveData<List<Long>?> {
        return favouriteIdListLiveData
    }

    fun addToFavourites(newFavourite : FavouriteAd) {
        // bør eg ha coroutines her, eller bør eg ikkje?
        CoroutineScope(Dispatchers.IO).launch {
            databaseDao.insert(newFavourite) }
    }

    fun removeFromFavourites(idToDelete: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            databaseDao.delete(idToDelete)
        }
    }

    private fun getAdsFromWeb(): List<Ad>? {
        var tempList : List<Ad>? = emptyList<Ad>()
        CoroutineScope(Dispatchers.IO).launch {
            val request = webservice.getAds()
            try {
                val response = request.await()
                if (response.isSuccessful) {
                    // Do something
                    tempList = response.body()?.adList
                } else {
                    Log.e("Repository", "Error: ${response.code()}")
                }
            } catch (e: HttpException) {
                Log.e("REQUEST", "Exception ${e.message}")
            } catch (e: Throwable) {
                Log.e("REQUEST", "Exception ${e.message}")
            }
        }
        return tempList
    }
}

/*
            val request = service.getAds()
            try {
                val response = request.await()
                //Sette det under ein annen plass?
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let { initRecyclerView(it.adList) }
                    } else {
                        // toast("Error network operation failed with ${response.code()}") <-- finn ut koffor toast ikkje blei godkjent
                        Log.e("REQUEST","Error network operation failed with ${response.code()}" )
                    }
                }
            } catch (e: HttpException) {
                Log.e("REQUEST", "Exception ${e.message}")
            } catch (e: Throwable) {
                Log.e("REQUEST", "Exception ${e.message}")
            }
        }*/