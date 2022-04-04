package android.sgstudentscheduler.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.sgstudentscheduler.Entity.TermEntity;
import android.sgstudentscheduler.R;
import android.sgstudentscheduler.ViewModel.CourseViewModel;
import android.sgstudentscheduler.ViewModel.TermViewModel;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

import static android.sgstudentscheduler.Util.Constants.EXTRA_TERM_END;
import static android.sgstudentscheduler.Util.Constants.EXTRA_TERM_NAME;
import static android.sgstudentscheduler.Util.Constants.EXTRA_TERM_START;

public class TermDetails extends AppCompatActivity {

    public static final String EXTRA_TERM_ID = "android.sgstudentscheduler.EXTRA_TERM_ID";
    final Calendar myCalendar = Calendar.getInstance();
    private TermEntity currentTerm;
    private EditText editName;
    private EditText editStartDate;
    private EditText editEndDate;
    private boolean mAddTerm;
    private DatePickerDialog.OnDateSetListener startDateListener;
    private DatePickerDialog.OnDateSetListener endDateListener;
    public TermViewModel mTermViewModel;
    public CourseViewModel mCourseViewModel;
    TextView coursesLabel;
    String termName;
    ImageView viewCourses;
    int termId;
    int associatedCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        setTitle((getIntent().getStringExtra(EXTRA_TERM_NAME)) + " Details");
        editName = findViewById(R.id.textTermName);
        editStartDate = findViewById(R.id.textStartDate);
        editEndDate = findViewById(R.id.textEndDate);
        viewCourses = findViewById(R.id.imageViewCourses);
        coursesLabel = findViewById(R.id.labelCourses);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        termId = getIntent().getIntExtra(EXTRA_TERM_ID, -1);
        termName = getIntent().getStringExtra(EXTRA_TERM_NAME);

        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        mTermViewModel.loadTerm(termId);
        mTermViewModel.mLiveTerm.observe(this, term -> currentTerm = term);
        associatedCourses = mTermViewModel.getTermCourses(termId);
        setStartCalendar();
        setEndCalendar();

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_TERM_ID)) {
            editName.setText(getIntent().getStringExtra(EXTRA_TERM_NAME));
            editStartDate.setText(getIntent().getStringExtra(EXTRA_TERM_START));
            editEndDate.setText(getIntent().getStringExtra(EXTRA_TERM_END));
        } else {
            setTitle("Add Term");
            viewCourses.setVisibility(View.GONE);
            coursesLabel.setVisibility(View.GONE);
            mAddTerm = true;
        }
    }

    private void setStartCalendar() {
        editStartDate.setOnClickListener(view -> {
            int year = myCalendar.get(Calendar.YEAR);
            int month = myCalendar.get(Calendar.MONTH);
            int day = myCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog startDialog = new DatePickerDialog(TermDetails.this, startDateListener, year, month, day);
            startDialog.getWindow();
            startDialog.show();
        });

        startDateListener = (view, year, month, day) -> {
            month = month + 1;
            String date = month + "/" + day + "/" + year;
            editStartDate.setText(date);
        };

    }

    private void setEndCalendar() {
        editEndDate.setOnClickListener(view -> {
            int year = myCalendar.get(Calendar.YEAR);
            int month = myCalendar.get(Calendar.MONTH);
            int day = myCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog endDialog = new DatePickerDialog(TermDetails.this, endDateListener, year, month, day);
            endDialog.getWindow();
            endDialog.show();
        });

        endDateListener = (view, year, month, day) -> {
            month = month + 1;
            String date = month + "/" + day + "/" + year;
            editEndDate.setText(date);
        };


    }

    public void viewCourses(View view) {
        Intent intentCourses = new Intent(TermDetails.this, CoursesActivity.class);
        intentCourses.putExtra(EXTRA_TERM_ID, termId);
        intentCourses.putExtra(EXTRA_TERM_NAME, termName);
        startActivity(intentCourses);
    }


    private void saveTerm() {
        String name = editName.getText().toString();
        String startDate = editStartDate.getText().toString();
        String endDate = editEndDate.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TERM_NAME, name);
        intent.putExtra(EXTRA_TERM_START, startDate);
        intent.putExtra(EXTRA_TERM_END, endDate);

        int termId = getIntent().getIntExtra(EXTRA_TERM_ID, -1);
        if (termId != -1) {
            intent.putExtra(EXTRA_TERM_ID, termId);
        }
        setResult(RESULT_OK, intent);
        finish();
    }


    private void deleteTerm() {

        //int associatedCourses = mTermViewModel.getTermCourses(termId);


        if (associatedCourses != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Term has associated courses and cannot be deleted!")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, i) -> finish());
            AlertDialog deleteTermAlert = builder.show();
            deleteTermAlert.setTitle("Term has associated courses!");
            deleteTermAlert.show();
        } else {
            mTermViewModel.deleteTerm(currentTerm);
            finish();
        }
    }


    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mAddTerm) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.save_bar, menu);
            return true;
        } else {
            MenuInflater mInflate = getMenuInflater();
            mInflate.inflate(R.menu.menu_bar, menu);
            return true;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            saveTerm();
            return true;
        } else if
        (id == R.id.action_delete) {
            deleteTerm();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





