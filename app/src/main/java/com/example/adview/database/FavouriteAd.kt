package com.example.adview.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_ads_table")
data class FavouriteAd(@PrimaryKey val id: Long,
                       @ColumnInfo(name = "ad_description") val description: String?,
                       @ColumnInfo(name = "ad_location") val location: String?,
                       @ColumnInfo val price: Int?
              // val image: Image? String
)

