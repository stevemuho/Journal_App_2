package com.stevemuho.journalapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.stevemuho.journalapp.db.AppDatabase;
import com.stevemuho.journalapp.db.JournalModel;

import java.util.List;

public class JournalListViewModel extends AndroidViewModel {

    private final LiveData<List<JournalModel>> journalNotesList;

    private AppDatabase appDatabase;

    public JournalListViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        journalNotesList = appDatabase.journalModel().getAllJournalItems();
    }

    public LiveData<List<JournalModel>> getJournalNotesList() {
        return journalNotesList;
    }

    public void deleteItem(JournalModel bucketListModel) {
        new deleteAsyncTask(appDatabase).execute(bucketListModel);
    }

    private static class deleteAsyncTask extends AsyncTask<JournalModel, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final JournalModel... params) {
            db.journalModel().deleteJournal(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
