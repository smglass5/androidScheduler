package android.sgstudentscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.sgstudentscheduler.Entity.CourseEntity;
import android.sgstudentscheduler.R;
import android.sgstudentscheduler.ViewModel.CourseViewModel;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.sgstudentscheduler.Util.Constants.EXTRA_COURSE_END;
import static android.sgstudentscheduler.Util.Constants.EXTRA_COURSE_NAME;
import static android.sgstudentscheduler.Util.Constants.EXTRA_COURSE_NOTES;
import static android.sgstudentscheduler.Util.Constants.EXTRA_COURSE_START;
import static android.sgstudentscheduler.Util.Constants.EXTRA_COURSE_STATUS;
import static android.sgstudentscheduler.Util.Constants.EXTRA_MENTOR_EMAIL;
import static android.sgstudentscheduler.Util.Constants.EXTRA_MENTOR_NAME;
import static android.sgstudentscheduler.Util.Constants.EXTRA_MENTOR_PHONE;

public class CoursesActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID = "android.sgstudentscheduler.EXTRA_COURSE_ID";
    public static final String EXTRA_TERM_ID_FK = "android.sgstudentscheduler.EXTRA_TERM_ID";
    public static final String EXTRA_TERM_NAME = "android.sgstudentscheduler.EXTRA_TERM_NAME";
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;

    private CourseViewModel mViewModel;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        setTitle((getIntent().getStringExtra(EXTRA_TERM_NAME)) + " Courses");

        termId = getIntent().getIntExtra(TermDetails.EXTRA_TERM_ID, -1);

        RecyclerView recyclerView = findViewById(R.id.course_recyclerView);//may need to rename id
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CourseAdapter mAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(mAdapter);

        mViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mViewModel.getCourseList(termId).observe(this, mAdapter::setCourses);

        mAdapter.setOnItemClickListener(course -> {
            Intent intent = new Intent(CoursesActivity.this, CourseDetails.class);
            intent.putExtra(EXTRA_COURSE_ID, course.getCourseId());
            intent.putExtra(EXTRA_COURSE_NAME, course.getCourseName());
            intent.putExtra(EXTRA_COURSE_START, course.getCourseStart());
            intent.putExtra(EXTRA_COURSE_END, course.getCourseEnd());
            intent.putExtra(EXTRA_COURSE_STATUS, course.getStatus());
            intent.putExtra(EXTRA_MENTOR_NAME, course.getMentorName());
            intent.putExtra(EXTRA_MENTOR_PHONE, course.getMentorPhone());
            intent.putExtra(EXTRA_MENTOR_EMAIL, course.getMentorEmail());
            intent.putExtra(EXTRA_COURSE_NOTES, course.getCourseNotes());
            intent.putExtra(EXTRA_TERM_ID_FK, termId);
            startActivityForResult(intent, EDIT_COURSE_REQUEST);
        });
    }

    public void addCourse(View view) {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(EXTRA_TERM_ID_FK, termId);
        startActivityForResult(intent, ADD_COURSE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {

            CourseEntity course = new CourseEntity(
                    intent.getStringExtra(EXTRA_COURSE_NAME),
                    intent.getStringExtra(EXTRA_COURSE_START),
                    intent.getStringExtra(EXTRA_COURSE_END),
                    intent.getStringExtra(EXTRA_COURSE_STATUS),
                    intent.getStringExtra(EXTRA_MENTOR_NAME),
                    intent.getStringExtra(EXTRA_MENTOR_PHONE),
                    intent.getStringExtra(EXTRA_MENTOR_EMAIL),
                    intent.getStringExtra(EXTRA_COURSE_NOTES),
                    intent.getIntExtra(CourseDetails.EXTRA_TERM_ID_FK, -1));

            mViewModel.insertCourse(course);
        } else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            int mCourseId = intent.getIntExtra(CourseDetails.EXTRA_COURSE_ID, -1);

            CourseEntity course = new CourseEntity(
                    intent.getStringExtra(EXTRA_COURSE_NAME),
                    intent.getStringExtra(EXTRA_COURSE_START),
                    intent.getStringExtra(EXTRA_COURSE_END),
                    intent.getStringExtra(EXTRA_COURSE_STATUS),
                    intent.getStringExtra(EXTRA_MENTOR_NAME),
                    intent.getStringExtra(EXTRA_MENTOR_PHONE),
                    intent.getStringExtra(EXTRA_MENTOR_EMAIL),
                    intent.getStringExtra(EXTRA_COURSE_NOTES),
                    intent.getIntExtra(CourseDetails.EXTRA_TERM_ID_FK, -1));
            course.setCourseId(mCourseId);
            mViewModel.updateCourse(course);
        }
    }
}