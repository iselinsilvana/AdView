package com.example.adview

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.adview.database.FavouriteAdsDatabase
import com.example.adview.database.asDomainModel
import com.example.adview.domain.Ad
import com.example.adview.model.asDatabaseModel
import com.example.adview.network.RetrofitFactory
import com.example.adview.network.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdsRepository(private val database: FavouriteAdsDatabase) {

    private val webservice: RetrofitService = RetrofitFactory.makeRetrofitService()
    //private val webResponse = List<NetworkAd>()

    //private val databaseDao: FavouriteAdsDao = FavouriteAdsDatabase.getInstance(application).favouriteAdsDao
    //private val favouriteAdListLiveData: LiveData<List<DatabaseAd>?> = databaseDao.getAllAds()
   // private val favouriteIdListLiveData: LiveData<List<Long>?> = databaseDao.getAllIds()


    val ads: LiveData<List<Ad>> = Transformations.map(database.favouriteAdsDao.getAllAds()) {
        it?.asDomainModel()
    }

    suspend fun refreshAds() {
        withContext(Dispatchers.IO) {
            var allAdsRefreshed = webservice.getAds().await().body()
            database.favouriteAdsDao.insertAll(*allAdsRefreshed!!.asDatabaseModel())
        }
    }
/*
    init {
        webservice = RetrofitFactory.makeRetrofitService()
        webResponse = getAdsFromWeb()

        val database = FavouriteAdsDatabase.getInstance(application).favouriteAdsDao
        databaseDao = database.favouriteAdsDao
        // Bør desse to vere i dispatchers.IO, eller blir det gjort når ein kaller det i VM?
        favouriteAdListLiveData = databaseDao.getAllAds()
        favouriteIdListLiveData = databaseDao.getAllIds()
        }
*/


 /*   fun getAllAds() : List<NetworkAd>?{
        // TODO: må endre dette. kall denne funksjonen ein annen plass
        getAdsFromWeb()
        return webResponse
    }*/
/*

    fun getAllFavouriteAds() : LiveData<List<DatabaseAd>?>{
        return favouriteAdListLiveData
    }

    fun getAllFavouriteIds() : LiveData<List<Long>?> {
        return favouriteIdListLiveData
    }
*/
/*
    fun addToFavourites(newFavourite : DatabaseAd) {
        // bør eg ha coroutines her, eller bør eg ikkje?
        CoroutineScope(Dispatchers.IO).launch {
            databaseDao.insert(newFavourite) }
    }

    fun removeFromFavourites(idToDelete: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            databaseDao.delete(idToDelete)
        }
    }
*/


/*
    private fun getAdsFromWeb() : AdResponse? {
       // webResponse.value = emptyList()
        var tempList : AdResponse? = emptyList<NetworkAd>()
        CoroutineScope(Dispatchers.IO).launch {
            val request = webservice.getAds()
            try {
                val response = request.await()
                if (response.isSuccessful) {
                    // Do something
                    tempList = (response.body())
                } else {
                    Log.e("Repository", "Error: ${response.code()}")
                }
            } catch (e: HttpException) {
                Log.e("REQUEST", "Exception ${e.message}")
            } catch (e: Throwable) {
                Log.e("REQUEST", "Exception ${e.message}")
            }
        }
        //webResponse.value = tempList
        return tempList
    }
*/

}


/*
private fun getAdsFromWeb() {
    val request = webservice.getAds()
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
