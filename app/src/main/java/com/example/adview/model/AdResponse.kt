package com.example.adview.model

import com.example.adview.database.DatabaseAd
import com.example.adview.domain.Ad
import com.squareup.moshi.Json

data class AdResponse(
    @field:Json(name = "items") val adList: List<NetworkAd>
)

data class NetworkAd(@field:Json(name = "id") val id: Long,
              @field:Json(name = "description") val description: String?,
              @field:Json(name = "location") val location: String?,
              @field:Json(name = "price") val price: Price?,
              @field:Json(name = "image") val image: Image?)

data class Image(
    @field:Json(name = "url") val url: String     //bruke string, eller noko anna?
)

data class Price(
    @field:Json(name = "value") val value: Int        //har eg aldri desimal? kan holde opp til 9 nuller, bør bruke long for mulighet til å holde fleire?
)


fun AdResponse.asDomainModel() : List<Ad> {
    return adList.map{
        Ad(
            id = it.id,
            description = it.description,
            location = it.location,
            price = it.price?.value,
            image = it.image?.url
        )
    }
}

fun AdResponse.asDatabaseModel() : Array<DatabaseAd> {
    return adList.map{
        DatabaseAd(
            id = it.id,
            description = it.description,
            location = it.location,
            price = it.price?.value,
            image = it.image?.url)
    }.toTypedArray()
}
