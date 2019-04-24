package com.example.adview.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteAdsDao{
    // Add an ad to favourites database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( favouriteAd : FavouriteAd)

    // Updates an ad already in the database
    @Update
    fun update(favouriteAd: FavouriteAd)

    // Fetch all the ads in the favourites database
    @Query("SELECT * from favourite_ads_table")
    fun getFavouriteAds() : LiveData<List<FavouriteAd?>>

    // Delete an ad from the database, might not be used
    @Delete
    fun delete(favouriteAd: FavouriteAd)

    // Deletes an ad from the favourites database based on the id number of the ad.
    @Query("DELETE from favourite_ads_table where id = :adId")
    fun delete( adId: Long)

    // Returns a list of the id for all the favourite ads
    @Query("SELECT id from favourite_ads_table")
    fun getAllIds() : LiveData<List<Long>?>
}