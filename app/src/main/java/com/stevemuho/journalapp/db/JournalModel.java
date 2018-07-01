package com.stevemuho.journalapp.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.stevemuho.journalapp.util.DateConverter;

import java.util.Date;

@Entity
public class JournalModel {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String itemTitle;
    private String itemDesc;
    @TypeConverters(DateConverter.class)
    private Date date;

    public JournalModel(String itemTitle, String itemDesc, Date date) {
        this.itemTitle = itemTitle;
        this.itemDesc = itemDesc;
        this.date = date;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
