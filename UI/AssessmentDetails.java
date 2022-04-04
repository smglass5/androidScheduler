package android.sgstudentscheduler.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sgstudentscheduler.Entity.AssessmentEntity;
import android.sgstudentscheduler.R;
import android.sgstudentscheduler.Util.ReminderBroadcast;
import android.sgstudentscheduler.ViewModel.AssessmentViewModel;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.sgstudentscheduler.Util.Constants.EXTRA_ASSESSMENT_DATE;
import static android.sgstudentscheduler.Util.Constants.EXTRA_ASSESSMENT_NAME;
import static android.sgstudentscheduler.Util.Constants.EXTRA_ASSESSMENT_STATUS;
import static android.sgstudentscheduler.Util.Constants.EXTRA_ASSESSMENT_TYPE;

public class AssessmentDetails extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID_FK = "android.sgstudentscheduler.EXTRA_COURSE_ID";
    public static final String EXTRA_ASSESSMENT_ID = "android.sgstudentscheduler.EXTRA_ASSESSMENT_ID";

    final Calendar myCalendar = Calendar.getInstance();
    private EditText editAssessmentName;
    private Spinner editAssessmentType;
    private EditText editAssessmentDate;
    private Spinner editAssessmentStatus;
    private AssessmentEntity currentAssessment;
    private boolean mAddAssessment;
    private DatePickerDialog.OnDateSetListener assessmentDateListener;
    private AssessmentViewModel mAssessmentViewModel;
    int assessmentId;
    String assessmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        setTitle((getIntent().getStringExtra(EXTRA_ASSESSMENT_NAME)) + " Details");

        editAssessmentName = findViewById(R.id.textAssessmentName);
        editAssessmentType = findViewById(R.id.assessment_type_spinner);
        editAssessmentDate = findViewById(R.id.textAssessmentDate);
        editAssessmentStatus = findViewById(R.id.assessment_status_spinner);
        assessmentId = getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1);
        assessmentName = getIntent().getStringExtra(EXTRA_ASSESSMENT_NAME);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mAssessmentViewModel.loadAssessment(assessmentId);
        mAssessmentViewModel.mLiveAssessment.observe(this, assessment -> currentAssessment = assessment);
        setAssessmentStatusAdapter();
        setTypeAdapter();
        setAssessmentDate();

        Intent intent = getIntent();
        if (intent.hasExtra(AssessmentsActivity.EXTRA_ASSESSMENT_ID)) {
            editAssessmentName.setText(getIntent().getStringExtra(EXTRA_ASSESSMENT_NAME));
            editAssessmentType.setSelection(spinnerTypeToString(intent.getStringExtra(EXTRA_ASSESSMENT_TYPE)));
            editAssessmentDate.setText(getIntent().getStringExtra(EXTRA_ASSESSMENT_DATE));
            editAssessmentStatus.setSelection(spinnerStatusToString(intent.getStringExtra(EXTRA_ASSESSMENT_STATUS)));
        } else {
            setTitle("Add Assessment");
            mAddAssessment = true;
        }
    }

    private void setTypeAdapter() {
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_type_spinner,
                android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editAssessmentType.setAdapter(typeAdapter);
    }

    private void setAssessmentStatusAdapter() {
        ArrayAdapter<CharSequence> assessmentStatusAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_status_spinner,
                android.R.layout.simple_spinner_item);
        assessmentStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editAssessmentStatus.setAdapter(assessmentStatusAdapter);
    }

    private void setAssessmentDate() {
        editAssessmentDate.setOnClickListener(view -> {
            int year = myCalendar.get(Calendar.YEAR);
            int month = myCalendar.get(Calendar.MONTH);
            int day = myCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog startDialog = new DatePickerDialog(AssessmentDetails.this, assessmentDateListener, year, month, day);
            startDialog.getWindow();
            startDialog.show();
        });

        assessmentDateListener = (view, year, month, day) -> {
            month = month + 1;
            String date = month + "/" + day + "/" + year;
            editAssessmentDate.setText(date);
        };
    }

    private void saveAssessment() {
        String assessmentName = editAssessmentName.getText().toString();
        String assessmentType = editAssessmentType.getSelectedItem().toString();
        String assessmentDate = editAssessmentDate.getText().toString();
        String assessmentStatus = editAssessmentStatus.getSelectedItem().toString();
        int courseId = getIntent().getIntExtra(EXTRA_COURSE_ID_FK, -1);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_ASSESSMENT_NAME, assessmentName);
        intent.putExtra(EXTRA_ASSESSMENT_TYPE, assessmentType);
        intent.putExtra(EXTRA_ASSESSMENT_DATE, assessmentDate);
        intent.putExtra(EXTRA_ASSESSMENT_STATUS, assessmentStatus);
        intent.putExtra(EXTRA_COURSE_ID_FK, courseId);

        int assessmentId = getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1);
        if (assessmentId != -1) {
            intent.putExtra(EXTRA_ASSESSMENT_ID, assessmentId);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setAlarm() {
        AlarmManager assessmentAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Calendar reminderCalendar = Calendar.getInstance();
        String assessmentDate = editAssessmentDate.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

            try {
                Date date = format.parse(assessmentDate);
                if (date != null) {
                    reminderCalendar.setTime(date);
                }
                Toast toast = Toast.makeText(AssessmentDetails.this, "Reminder set for "
                        + editAssessmentName.getText().toString(), Toast.LENGTH_LONG);
                toast.show();
            } catch (Exception e) {
                Toast toast = Toast.makeText(AssessmentDetails.this, "Invalid date format", Toast.LENGTH_SHORT);
                toast.show();
                Date date = Calendar.getInstance().getTime();
                reminderCalendar.setTime(date);
            }

        Intent intent = new Intent(getApplicationContext(), ReminderBroadcast.class);
        intent.putExtra("message", editAssessmentName.getText().toString() + " is due");
        int assessmentId = getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1);
        PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 1003 + assessmentId, intent, 0);
        assessmentAlarm.set(AlarmManager.RTC_WAKEUP, reminderCalendar.getTimeInMillis(), broadcast);
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mAddAssessment) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.save_bar, menu);
            return true;
        } else {
            MenuInflater mInflate = getMenuInflater();
            mInflate.inflate(R.menu.assessment_menu_bar, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            saveAssessment();
            return true;
        } else if (id == R.id.action_delete) {
            mAssessmentViewModel.deleteAssessment(currentAssessment);
            finish();
            return true;
        } else if (id == R.id.set_assessment_alert) {
            setAlarm();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int spinnerTypeToString(String stringType) { //?????
        switch (stringType) {
            case "Performance":
                return 0;
            case "Objective":
                return 1;
            default:
                throw new IllegalStateException("Exception: " + stringType);
        }
    }

    private int spinnerStatusToString(String stringStatus) {
        switch (stringStatus) {
            case "Not Taken":
                return 0;
            case "Passed":
                return 1;
            case "Failed":
                return 2;
            default:
                throw new IllegalStateException("Exception: " + stringStatus);
        }
    }
}