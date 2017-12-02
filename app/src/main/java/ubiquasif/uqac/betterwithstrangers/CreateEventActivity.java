package ubiquasif.uqac.betterwithstrangers;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ubiquasif.uqac.betterwithstrangers.Fragments.AutoCompleteFragment;
import ubiquasif.uqac.betterwithstrangers.Models.Event;

public class CreateEventActivity
        extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private CoordinatorLayout layout;

    private TextView dateView;
    private TextView timeView;
    private EditText nameEdit;
    private EditText locationEdit;
    private Switch privateSwitch;
    private NachoTextView tagsView;
    private AutoCompleteFragment autoCompleteFragment;

    private Calendar pickedDateTime;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        layout = findViewById(R.id.create_event_layout);

        pickedDateTime = new GregorianCalendar();
        dateView = findViewById(R.id.event_date);
        timeView = findViewById(R.id.event_time);
        updateDateView();
        updateTimeView();

        tagsView = findViewById(R.id.event_tags);
        tagsView.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);

        nameEdit = findViewById(R.id.event_name);
        locationEdit = findViewById(R.id.event_location);

        autoCompleteFragment = (AutoCompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        privateSwitch = findViewById(R.id.event_private);

        database = FirebaseFirestore.getInstance();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_event:
                saveEvent();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        pickedDateTime.set(year, month, dayOfMonth);
        updateDateView();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        pickedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        pickedDateTime.set(Calendar.MINUTE, minute);
        pickedDateTime.set(Calendar.SECOND, 0);
        updateTimeView();
    }

    public void onDateClicked(View v) {
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                this,
                pickedDateTime.get(Calendar.YEAR),
                pickedDateTime.get(Calendar.MONTH),
                pickedDateTime.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    public void onTimeClicked(View v) {
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                this,
                pickedDateTime.get(Calendar.HOUR_OF_DAY),
                pickedDateTime.get(Calendar.MINUTE),
                true
        );

        dialog.show();
    }

    private void saveEvent() {
        tagsView.chipifyAllUnterminatedTokens();

        Event event = new Event(
                FirebaseAuth.getInstance().getUid(),
                nameEdit.getText().toString(),
                privateSwitch.isChecked(),
                tagsView.getChipValues(),
                pickedDateTime.getTime(),
                locationEdit.getText().toString()
        );

        Snackbar.make(layout, R.string.event_pending, Snackbar.LENGTH_SHORT).show();

        database.collection("events")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(layout, R.string.event_failure, Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    private void updateDateView() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        String date = dateFormat.format(pickedDateTime.getTime());
        dateView.setText(date);
    }

    private void updateTimeView() {
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        String time = timeFormat.format(pickedDateTime.getTime());
        timeView.setText(time);
    }
}
