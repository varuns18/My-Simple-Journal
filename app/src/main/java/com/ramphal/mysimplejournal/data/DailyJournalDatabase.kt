package com.ramphal.mysimplejournal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DailyJournalModel::class], version = 1, exportSchema = false)
abstract class DailyJournalDatabase: RoomDatabase() {

    abstract fun dailyJournalDao(): DailyJournalDao

    companion object {
        @Volatile
        private var INSTANCE: DailyJournalDatabase? = null

        const val DATABASE_NAME = "daily_journal_database"


        fun getInstance(context: Context): DailyJournalDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DailyJournalDatabase::class.java,
                        DATABASE_NAME
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}