package com.example.adview.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteAdsDao{

    // Fetch all the ads in the favourites database
    @Query("SELECT * FROM favourite_ads_table")
    fun getAllAds() : LiveData<List<DatabaseAd>?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll( vararg databaseAds: DatabaseAd)

    @Query("SELECT * FROM favourite_ads_table WHERE isFavourite = 1")
    fun getAllFavourites() : LiveData<List<DatabaseAd>?>

    // Updates an ad already in the database
    @Update
    fun update(databaseAd: DatabaseAd)


}