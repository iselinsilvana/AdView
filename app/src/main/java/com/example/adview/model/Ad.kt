package com.example.adview.model

data class Ad(val id: Int,
              val photoUrl: String,     //bruke string, eller noko anna?
              val price: Double,        //kan ikkje bruke int i tilfelle det er desimaltall. er double lurest?
              val place: String,
              val title: String)