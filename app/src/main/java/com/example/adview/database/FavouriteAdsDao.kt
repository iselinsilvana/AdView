package com.example.adview.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavouriteAdsDao{
    @Insert
    fun insert( favouriteAd : FavouriteAd)

    @Update
    fun update(favouriteAd: FavouriteAd)

    @Query("SELECT * from favourite_ads_table")
    fun getFavouriteAds() : FavouriteAd?
}