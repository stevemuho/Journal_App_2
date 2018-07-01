package com.stevemuho.journalapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.stevemuho.journalapp.db.AppDatabase;
import com.stevemuho.journalapp.db.JournalModel;

public class AddJournalViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddJournalViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void addJournal(final JournalModel journalListModel) {

        new addAsyncTask(appDatabase).execute(journalListModel);
    }

    private static class addAsyncTask extends AsyncTask<JournalModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final JournalModel... params) {
            db.journalModel().addJournal(params[0]);
            return null;
        }

    }
}
