package com.example.adview.model

import android.accounts.AuthenticatorDescription
import android.util.Log
import com.squareup.moshi.Json
import java.text.DecimalFormat
import java.util.*

data class AdResponse(
    @field:Json(name = "items") val adList: List<Ad>
)

data class Ad(val id: Long,
              val description: String?,
              val location: String?,
              val price: Price?,
              val image: Image?)

data class Image(
    val url: String     //bruke string, eller noko anna?
)

data class Price(
    val value: Int        //har eg aldri desimal?

)
