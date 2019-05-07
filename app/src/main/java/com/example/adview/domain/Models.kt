package com.example.adview.domain

import androidx.lifecycle.Transformations.map
import com.example.adview.database.DatabaseAd

data class Ad(val id: Long,
              val description: String?,
              val location: String?,
              val price: Int?,
              val image: String?,
              val isFavourite: Int)

fun Ad.asDatabaseModel(): DatabaseAd {
    return DatabaseAd(
            id = id,
            description = description,
            location = location,
            price = price,
            image = image,
            isFavourite = isFavourite)
}