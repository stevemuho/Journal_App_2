package com.stevemuho.journalapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.stevemuho.journalapp.util.DateConverter;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface JournalDao {

    @Query("select * from JournalModel")
    LiveData<List<JournalModel>> getAllJournalItems();

    @Query("select * from JournalModel where id = :id")
    JournalModel getJournalById(String id);

    @Insert(onConflict = REPLACE)
    void addJournal(JournalModel journal);

    @Delete
    void deleteJournal(JournalModel journal);

    @Update
    void editJournal(JournalModel journal);

}
