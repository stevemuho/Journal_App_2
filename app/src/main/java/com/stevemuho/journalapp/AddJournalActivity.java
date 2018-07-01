package com.stevemuho.journalapp;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stevemuho.journalapp.ViewModels.AddJournalViewModel;
import com.stevemuho.journalapp.db.JournalModel;

import java.util.Calendar;
import java.util.Date;
import java.util.function.ToLongBiFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddJournalActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {
    @BindView(R.id.fab_save)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnSetDate)
    Button btnSetDate;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.journalDesc)
    EditText edtJournalDesc;
    @BindView(R.id.journalTitle)
    EditText edtJournalTitle;


    private Date date;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    AddJournalViewModel journalViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //instance of calendar object
        calendar = Calendar.getInstance();

        //instance of date picker
        datePickerDialog = new DatePickerDialog(this,
                AddJournalActivity.this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));


        journalViewModel = ViewModelProviders.of(this).get(AddJournalViewModel.class);

    }


    //activates the date picker
    @OnClick(R.id.btnSetDate)
    public void setTheDate(){
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = calendar.getTime();

        //set the date on my text view
        txtDate.setText("Penned on : " +dayOfMonth +"/" + month +"/" +year);
    }

    //save the thought to db
    @OnClick(R.id.fab_save)
    public void saveToDb(){

        // we have used if else control statements to check for users input
        if (edtJournalTitle.getText() == null || edtJournalDesc.getText() == null || date == null)
            Toast.makeText(AddJournalActivity.this, "Missing fields", Toast.LENGTH_SHORT).show();
        else {

            journalViewModel.addJournal(new JournalModel(
                    edtJournalTitle.getText().toString(),
                    edtJournalDesc.getText().toString(),
                    date
            ));
            finish();
        }
    }
}
