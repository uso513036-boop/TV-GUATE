package com.example.model

enum class GeoHealthStatus {
    ONLINE_OPTIMIZED,      // Direct playback available & verified
    GEO_BYPASSED,          // Geo-shield active with proxy headers / fallback mirror
    TESTING_SIGNAL,        // Checking ping and header response
    GEO_RESTRICTED_WARNING // Needs fallback mirror
}

data class ChannelGeoDiagnostic(
    val channelId: String,
    val channelName: String,
    val status: GeoHealthStatus,
    val pingLatencyMs: Long,
    val httpStatusCode: Int,
    val bypassHeadersApplied: Boolean,
    val streamActiveUrl: String,
    val regionOrigin: String,
    val diagnosticMessage: String,
    val timestamp: Long = System.currentTimeMillis()
)
