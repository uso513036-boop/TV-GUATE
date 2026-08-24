package com.example.service

import com.example.model.Channel

object GeoBlockDetector {

    // Guatemala National IP & User-Agent configurations for official stream endpoints
    const val GT_USER_AGENT = "Mozilla/5.0 (Linux; Android 14; Mobile; rv:128.0) Gecko/128.0 Firefox/128.0"
    const val GT_FORWARDED_IP = "181.174.128.10" // Guatemala Telecom / Claro / Tigo GT Range

    /**
     * Builds request headers for the native video player to ensure smooth playback
     */
    fun buildPlayerHeaders(channel: Channel): Map<String, String> {
        val headers = mutableMapOf<String, String>()
        headers["User-Agent"] = channel.customUserAgent ?: GT_USER_AGENT
        headers["X-Forwarded-For"] = GT_FORWARDED_IP
        headers["Client-IP"] = GT_FORWARDED_IP
        headers["Accept"] = "*/*"
        headers["Origin"] = channel.customReferer ?: "https://www.chapintv.com"
        channel.customReferer?.let {
            headers["Referer"] = it
        }
        return headers
    }
}
