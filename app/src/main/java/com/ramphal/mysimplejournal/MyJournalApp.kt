package com.ramphal.mysimplejournal

import android.app.Application
import com.ramphal.mysimplejournal.data.DailyJournalDatabase

class MyJournalApp: Application() {

    val db by lazy {
        DailyJournalDatabase.getInstance(this)
    }

}