package com.stevemuho.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.stevemuho.journalapp.ViewModels.JournalListViewModel;
import com.stevemuho.journalapp.adapters.RecyclerViewAdapter;
import com.stevemuho.journalapp.db.AppDatabase;
import com.stevemuho.journalapp.db.JournalModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rvJournalNotes)
    RecyclerView rvNotes;
    JournalListViewModel viewModel;
    RecyclerViewAdapter journalAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initView();

        viewModel = ViewModelProviders.of(this).get(JournalListViewModel.class);

        viewModel.getJournalNotesList().observe(MainActivity.this, new Observer<List<JournalModel>>() {
            @Override
            public void onChanged(@Nullable List<JournalModel> journals) {
                journalAdapter.addItems(journals);
            }
        });


    }

    private void initView(){

        rvNotes.setLayoutManager( new LinearLayoutManager(this));
        rvNotes.addItemDecoration( new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        journalAdapter= new RecyclerViewAdapter(new ArrayList<JournalModel>(), this);
        rvNotes.setAdapter(journalAdapter);

    }

    @OnClick(R.id.fab)
    public void createJournal(){
        startActivity( new Intent(MainActivity.this,AddJournalActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out) {

            signOut();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut(){

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(MainActivity.this, AuthenticateUser.class));
                        finish();
                    }
                });
    }

    @Override
    public boolean onLongClick(View view) {

        JournalModel bucketListModel = (JournalModel) view.getTag();
        viewModel.deleteItem(bucketListModel);
        return false;
    }
}
