package com.dhizak.animefinder.model

import android.accounts.AuthenticatorDescription

class Top (val mal_id : Int,
           val rank : Int,
           val url : String,
           val image_url : String,
           val title : String,
           val type : String,
           val score : Float?,
           val description: String?,
           val members : Int,
           val airing_start : String,
           val airing_end : String,
           val episodes : Int)