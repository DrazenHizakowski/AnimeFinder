package com.dhizak.animefinder.model.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.dhizak.animefinder.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.coroutines.CoroutineContext

class YoutubeThumbFetcher(val okHttpClient: OkHttpClient) : CoroutineScope{

    val youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/"
    val videoIdRegex = arrayOf("\\?vi?=([^&]*)", "watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)")
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun getThumbWithOkHttp(videoId: String, numberOfThumb: Int) : Deferred<Bitmap> = async {
        val response = okHttpClient.newCall(Request.Builder().url("https://img.youtube.com/vi/$videoId/hqdefault.jpg").build())
        BitmapFactory.decodeStream(response.execute().body()?.byteStream())
    }

    fun getVideoId(url: String): String? {
        val youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url)

        for (regex in videoIdRegex) {
            val compiledPattern = Pattern.compile(regex)
            val matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain)

            if (matcher.find()) {
                return matcher.group(1)
            }
        }

        return null
    }

    private fun youTubeLinkWithoutProtocolAndDomain(url: String): String {
        val compiledPattern = Pattern.compile(youTubeUrlRegEx)
        val matcher = compiledPattern.matcher(url)

        return if (matcher.find()) {
            url.replace(matcher.group(), "")
        } else url
    }


}