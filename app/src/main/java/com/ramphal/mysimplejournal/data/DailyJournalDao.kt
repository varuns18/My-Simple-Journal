package com.ramphal.mysimplejournal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyJournalDao {

    @Insert
    suspend fun insert(dailyJournalModel: DailyJournalModel)

    @Update
    suspend fun update(dailyJournalModel: DailyJournalModel)

    @Delete
    suspend fun delete(dailyJournalModel: DailyJournalModel)

    @Query("SELECT * FROM daily_journal_table")
    fun getAll(): Flow<List<DailyJournalModel>>

    @Query("SELECT * FROM daily_journal_table WHERE id = :id")
    fun getById(id: Int): Flow<DailyJournalModel?>

}