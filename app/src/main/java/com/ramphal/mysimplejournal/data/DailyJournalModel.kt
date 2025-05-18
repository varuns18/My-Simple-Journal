package com.ramphal.mysimplejournal.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "daily_journal_table")
data class DailyJournalModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: String,
    val content: String,
    val image: String? = null,
    val color: Int
)
