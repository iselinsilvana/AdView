package com.example.adview.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.adview.domain.Ad

@Entity(tableName = "favourite_ads_table")
data class DatabaseAd constructor(@PrimaryKey val id: Long,
                      @ColumnInfo(name = "ad_description") val description: String?,
                      @ColumnInfo(name = "ad_location") val location: String?,
                      @ColumnInfo val price: Int?,
                      @ColumnInfo val image: String?)

fun List<DatabaseAd>.asDomainModel(): List<Ad> {
    return map {
        Ad(
            id = it.id,
            description = it.description,
            location = it.location,
            price = it.price,
            image = it.image)
    }
}

