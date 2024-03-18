package com.example.flixster2

import java.io.Serializable

data class TVShow(var dataImage: String, var dataTitle:String, var dataDesc: String, var dataAired: String, var dataLang: String, var dataRating: String)  :
    Serializable {

}