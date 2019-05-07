package com.example.adview.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteAdsDao{
    // Add an ad to favourites database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(databaseAd : DatabaseAd)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( vararg databaseAds: DatabaseAd) // TODO: Endre dette til å bli kun Ads, eller adsFromWeb, elns når eg har fiksa det andre

    // Updates an ad already in the database
    @Update
    fun update(databaseAd: DatabaseAd)

    // Fetch all the ads in the favourites database
    @Query("SELECT * from favourite_ads_table")
    fun getAllAds() : LiveData<List<DatabaseAd>?>

    // Delete an ad from the database, might not be used
    @Delete
    fun delete(databaseAd: DatabaseAd)

    // Deletes an ad from the favourites database based on the id number of the ad.
    @Query("DELETE from favourite_ads_table where id = :adId")
    fun delete( adId: Long)

    // Returns a list of the id for all the favourite ads
    @Query("SELECT id from favourite_ads_table")
    fun getAllIds() : LiveData<List<Long>?>
}