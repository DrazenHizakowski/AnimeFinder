package com.dhizak.animefinder.model.api.retrofit.adapters

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {
    private val format = "yyyy-MM-dd'T'HH:mm:ss+00:00"

    @FromJson
    public fun fromString(value: String): Date? {
        //"1998-04-03T00:00:00+00:00"
        val simpleDateFormat = SimpleDateFormat(format)
        return simpleDateFormat.parse(value)
    }

    @ToJson
    public fun fromDate(date : Date) : String {
        val simpleDateFormat = SimpleDateFormat(format)
        return simpleDateFormat.format(date)
    }

}