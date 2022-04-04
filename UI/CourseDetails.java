package android.sgstudentscheduler.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sgstudentscheduler.Entity.CourseEntity;
import android.sgstudentscheduler.R;
import android.sgstudentscheduler.Util.ReminderBroadcast;
import android.sgstudentscheduler.ViewModel.CourseViewModel;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.sgstudentscheduler.Util.Constants.EXTRA_COURSE_END;
import static android.sgstudentscheduler.Util.Constants.EXTRA_COURSE_NAME;
import static android.sgstudentscheduler.Util.Constants.EXTRA_COURSE_NOTES;
import static android.sgstudentscheduler.Util.Constants.EXTRA_COURSE_START;
import static android.sgstudentscheduler.Util.Constants.EXTRA_COURSE_STATUS;
import static android.sgstudentscheduler.Util.Constants.EXTRA_MENTOR_EMAIL;
import static android.sgstudentscheduler.Util.Constants.EXTRA_MENTOR_NAME;
import static android.sgstudentscheduler.Util.Constants.EXTRA_MENTOR_PHONE;

public class CourseDetails extends AppCompatActivity {

    public static final String EXTRA_TERM_ID_FK = "android.sgstudentscheduler.EXTRA_TERM_ID";
    public static final String EXTRA_COURSE_ID = "android.sgstudentscheduler.EXTRA_COURSE_ID";
    final Calendar myCalendar = Calendar.getInstance();
    private EditText editCourseName;
    private EditText editCourseStart;
    private EditText editCourseEnd;
    private Spinner editCourseStatus;
    private EditText editMentorName;
    private EditText editMentorPhone;
    private EditText editMentorEmail;
    private EditText editCourseNotes;
    private CourseEntity currentCourse;
    private boolean mAddCourse;
    private DatePickerDialog.OnDateSetListener startDateListener;
    private DatePickerDialog.OnDateSetListener endDateListener;
    private CourseViewModel mCourseViewModel;
    int courseId;
    String courseName;
    ImageView viewAssessments;
    TextView assessmentsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        setTitle((getIntent().getStringExtra(EXTRA_COURSE_NAME)) + " Details");
        editCourseName = findViewById(R.id.textCourseName);
        editCourseStart = findViewById(R.id.textStartDate);
        editCourseEnd = findViewById(R.id.textEndDate);
        editCourseStatus = findViewById(R.id.course_spinner);
        editMentorName = findViewById(R.id.textMentorName);
        editMentorPhone = findViewById(R.id.textMentorPhone);
        editMentorEmail = findViewById(R.id.textMentorEmail);
        editCourseNotes = findViewById(R.id.textCourseNotes);
        assessmentsLabel = findViewById(R.id.labelAssessments);
        viewAssessments = findViewById(R.id.imageViewAssessments);
        courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
        courseName = getIntent().getStringExtra(EXTRA_COURSE_NAME);

        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mCourseViewModel.loadCourse(courseId);
        mCourseViewModel.mLiveCourse.observe(this, course -> currentCourse = course);
        setStatusAdapter();
        setStartCalendar();
        setEndCalendar();

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_COURSE_ID)) {
            editCourseName.setText(getIntent().getStringExtra(EXTRA_COURSE_NAME));
            editCourseStart.setText(getIntent().getStringExtra(EXTRA_COURSE_START));
            editCourseEnd.setText(getIntent().getStringExtra(EXTRA_COURSE_END));
            editCourseStatus.setSelection(spinnerToString(intent.getStringExtra(EXTRA_COURSE_STATUS)));
            editMentorName.setText(getIntent().getStringExtra(EXTRA_MENTOR_NAME));
            editMentorPhone.setText(getIntent().getStringExtra(EXTRA_MENTOR_PHONE));
            editMentorEmail.setText(getIntent().getStringExtra(EXTRA_MENTOR_EMAIL));
            editCourseNotes.setText(getIntent().getStringExtra(EXTRA_COURSE_NOTES));
        } else {
            setTitle("Add Course");
            viewAssessments.setVisibility(View.GONE);
            assessmentsLabel.setVisibility(View.GONE);
            mAddCourse = true;
        }
    }


    private void setStatusAdapter() {
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.course_spinner,
                android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCourseStatus.setAdapter(statusAdapter);
    }


    private void setStartCalendar() {
        editCourseStart.setOnClickListener(view -> {
            int year = myCalendar.get(Calendar.YEAR);
            int month = myCalendar.get(Calendar.MONTH);
            int day = myCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog startDialog = new DatePickerDialog(CourseDetails.this, startDateListener, year, month, day);
            startDialog.getWindow();
            startDialog.show();
        });

        startDateListener = (view, year, month, day) -> {
            month = month + 1;
            String date = month + "/" + day + "/" + year;
            editCourseStart.setText(date);
        };
    }


    private void setEndCalendar() {
        editCourseEnd.setOnClickListener(view -> {
            int year = myCalendar.get(Calendar.YEAR);
            int month = myCalendar.get(Calendar.MONTH);
            int day = myCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog endDialog = new DatePickerDialog(CourseDetails.this, endDateListener, year, month, day);
            endDialog.getWindow();
            endDialog.show();
        });

        endDateListener = (view, year, month, day) -> {
            month = month + 1;
            String date = month + "/" + day + "/" + year;
            editCourseEnd.setText(date);
        };
    }


    public void ViewAssessments(View view) {
        Intent intentAssessments = new Intent(this, AssessmentsActivity.class);
        intentAssessments.putExtra(EXTRA_COURSE_ID, courseId);
        intentAssessments.putExtra(EXTRA_COURSE_NAME, courseName);
        startActivity(intentAssessments);
    }


    private void saveCourse() {
        String courseName = editCourseName.getText().toString();
        String courseStart = editCourseStart.getText().toString();
        String courseEnd = editCourseEnd.getText().toString();
        String courseStatus = editCourseStatus.getSelectedItem().toString();
        String mentorName = editMentorName.getText().toString();
        String mentorPhone = editMentorPhone.getText().toString();
        String mentorEmail = editMentorEmail.getText().toString();
        String courseNotes = editCourseNotes.getText().toString();
        int termId = getIntent().getIntExtra(EXTRA_TERM_ID_FK, -1);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_COURSE_NAME, courseName);
        intent.putExtra(EXTRA_COURSE_START, courseStart);
        intent.putExtra(EXTRA_COURSE_END, courseEnd);
        intent.putExtra(EXTRA_COURSE_STATUS, courseStatus);
        intent.putExtra(EXTRA_MENTOR_NAME, mentorName);
        intent.putExtra(EXTRA_MENTOR_PHONE, mentorPhone);
        intent.putExtra(EXTRA_MENTOR_EMAIL, mentorEmail);
        intent.putExtra(EXTRA_COURSE_NOTES, courseNotes);
        intent.putExtra(EXTRA_TERM_ID_FK, termId);

        int courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
        if (courseId != -1) {
            intent.putExtra(EXTRA_COURSE_ID, courseId);
        }
        setResult(RESULT_OK, intent);
        finish();
    }


    private void setStartAlarm() {
        AlarmManager courseAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar reminderCalendar = Calendar.getInstance();
        String startDate = editCourseStart.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        try {
            Date date = format.parse(startDate);
            if (date != null) {
                reminderCalendar.setTime(date);
            }
            Toast toast = Toast.makeText(CourseDetails.this, "Start Reminder set for "
                    + editCourseName.getText().toString(), Toast.LENGTH_LONG);
            toast.show();
        } catch (Exception e) {
            Toast toast = Toast.makeText(CourseDetails.this, "Invalid date format", Toast.LENGTH_SHORT);
            toast.show();
            Date date = Calendar.getInstance().getTime();
            reminderCalendar.setTime(date);
        }

        Intent intent = new Intent(getApplicationContext(), ReminderBroadcast.class);
        intent.putExtra("message", editCourseName.getText().toString() + "has activated");
        int courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
        PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 1001 + courseId, intent, 0);
        courseAlarm.set(AlarmManager.RTC_WAKEUP, reminderCalendar.getTimeInMillis(), broadcast);
    }


    private void setEndAlarm() {
        AlarmManager courseAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar reminderCalendar = Calendar.getInstance();
        String endDate = editCourseEnd.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        try {
            Date date = format.parse(endDate);
            if (date != null) {
                reminderCalendar.setTime(date);
            }
            Toast toast = Toast.makeText(CourseDetails.this, "End Reminder set for "
                    + editCourseName.getText().toString(), Toast.LENGTH_LONG);
            toast.show();
        } catch (Exception e) {
            Toast toast = Toast.makeText(CourseDetails.this, "Invalid date format", Toast.LENGTH_SHORT);
            toast.show();
            Date date = Calendar.getInstance().getTime();
            reminderCalendar.setTime(date);
        }

        Intent intent = new Intent(getApplicationContext(), ReminderBroadcast.class);
        intent.putExtra("message", editCourseName.getText().toString() + "has ended");
        int courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
        PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 1002 + courseId, intent, 0);
        courseAlarm.set(AlarmManager.RTC_WAKEUP, reminderCalendar.getTimeInMillis(), broadcast);
    }


    public void shareNotes() {
        Intent notesIntent = new Intent();
        notesIntent.setAction(Intent.ACTION_SEND);
        notesIntent.putExtra(Intent.EXTRA_TEXT, editCourseNotes.getText().toString());
        notesIntent.setType("text/plain");

        Intent shareNotesIntent = Intent.createChooser(notesIntent, null);
        startActivity(shareNotesIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mAddCourse) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.save_bar, menu);
            return true;
        } else {
            MenuInflater mInflate = getMenuInflater();
            mInflate.inflate(R.menu.course_menu_bar, menu);
            return true;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_save) {
            saveCourse();
            return true;
        } else if (itemId == R.id.action_delete) {
            mCourseViewModel.deleteCourse(currentCourse);
            finish();
            return true;
        } else if (itemId == R.id.set_start_alert) {
            setStartAlarm();
            return true;
        } else if (itemId == R.id.set_end_alert) {
            setEndAlarm();
            return true;
        } else if (itemId == R.id.share_notes) {
            shareNotes();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private int spinnerToString(String stringStatus) { //?????
        switch (stringStatus) {
            case "Not Started":
                return 0;
            case "In Progress":
                return 1;
            case "Completed":
                return 2;
            case "Dropped":
                return 3;
            default:
                throw new IllegalStateException("Exception: " + stringStatus);
        }
    }


    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }
}
