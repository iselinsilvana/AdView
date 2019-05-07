package com.example.adview.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseAd::class], version = 3, exportSchema = false)
abstract class FavouriteAdsDatabase : RoomDatabase() {

    abstract val favouriteAdsDao: FavouriteAdsDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteAdsDatabase? = null

        fun getDatabase(context: Context): FavouriteAdsDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavouriteAdsDatabase::class.java,
                        "favourite_ads_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}