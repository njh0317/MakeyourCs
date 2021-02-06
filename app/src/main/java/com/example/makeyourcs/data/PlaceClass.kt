package com.example.makeyourcs.data

import com.google.type.LatLng

data class PlaceClass(
    var place_name: String,
    var place_info: String,
    var latitude: Float,
    var longitude:Float
)

//var coord = LatLng(37.5670135, 126.9783740)
//Latlng(latitude, longitude)