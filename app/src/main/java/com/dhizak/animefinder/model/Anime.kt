package com.dhizak.animefinder.model

data class Anime (val mal_id : Int,
                  val link_canonical : String,
                  val title : String,
                  val title_english : String,
                  val title_japanese : String,
                  val image_url : String,
                  val trailer_url : String?,
                  val type : String,
                  val source : String,
                  val episodes : Int,
                  val status : String,
                  val airing : Boolean,
                  val aired_string : String,
                  val aired : Aired,
                  val duration : String,
                  val rating : String,
                  val score : Float,
                  val scored_by : Int,
                  val rank : Int,
                  val popularity : Int,
                  val members : Int,
                  val favorites : Int,
                  val synopsis : String,
                  val background : String,
                  val premiered : String,
                  val broadcast : String,
                  val genre : List<Genre>)