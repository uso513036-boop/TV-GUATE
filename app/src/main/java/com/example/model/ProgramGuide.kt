package com.example.model

data class ProgramShow(
    val id: String,
    val channelId: String,
    val title: String,
    val startHourMin: String, // e.g. "13:00"
    val endHourMin: String,   // e.g. "14:30"
    val synopsis: String,
    val category: String,
    val rating: String = "TP",
    val isLiveNow: Boolean = false,
    val progressPercent: Float = 0f, // 0.0 to 1.0
    val presenterOrCast: String = "",
    val hasReminderSet: Boolean = false
)

data class ChannelWithGuide(
    val channel: Channel,
    val programs: List<ProgramShow>,
    val currentShow: ProgramShow?,
    val nextShow: ProgramShow?
)
