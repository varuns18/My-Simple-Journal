package com.ramphal.mysimplejournal

data class DailyJournalModel(
    val id: Int,
    val title: String,
    val date: String,
    val content: String,
    val image: Int?,
    val color: Int
)
