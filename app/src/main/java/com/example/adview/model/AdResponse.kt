package com.example.adview.model

import com.squareup.moshi.Json

data class AdResponse(
    @field:Json(name = "items") val adList: List<Ad>
)

data class Ad(@field:Json(name = "id") val id: Long,
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
