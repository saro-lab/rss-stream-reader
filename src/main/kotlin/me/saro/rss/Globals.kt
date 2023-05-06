package me.saro.rss

import java.io.InputStream
import java.net.URL

const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36"

fun URL.openStreamWithAgent(): InputStream =
    this.openConnection().apply {
        setRequestProperty("User-Agent", USER_AGENT)
    }.getInputStream()