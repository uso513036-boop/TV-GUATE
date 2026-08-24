package com.example.model

enum class ChannelCategory(val displayName: String, val iconName: String) {
    TODOS("Todos", "GridView"),
    NACIONALES("Nacionales", "Tv"),
    NOTICIAS("Noticias", "Feed"),
    ENTRETENIMIENTO("Entretenimiento", "Movie"),
    DEPORTES("Deportes", "SportsSoccer"),
    CULTURA_EDUCACION("Cultura & Estado", "AccountBalance"),
    REGIONALES("Regionales", "Public")
}

enum class StreamType {
    HLS_NATIVE,      // Direct HLS (.m3u8) / Media3 stream
    CLEAN_SANDBOX    // Clean internal embedded player without external redirects or popups
}

data class Channel(
    val id: String,
    val number: Int,
    val name: String,
    val alias: String,
    val category: ChannelCategory,
    val description: String,
    val logoText: String,
    val accentColorHex: Long = 0xFF0284C7,
    val streamUrl: String,
    val fallbackStreamUrls: List<String> = emptyList(),
    val streamType: StreamType = StreamType.HLS_NATIVE,
    val webPlayerUrl: String? = null,
    val customUserAgent: String? = "Mozilla/5.0 (Linux; Android 14; Mobile; rv:128.0) Gecko/128.0 Firefox/128.0",
    val customReferer: String? = null,
    val hasGeoRestriction: Boolean = false,
    val geoOrigin: String = "Guatemala / CA",
    val resolution: String = "1080p Full HD",
    val isLiveBroadcast: Boolean = true,
    val currentShowTitle: String = "",
    val currentShowTime: String = "",
    val nextShowTitle: String = ""
)
