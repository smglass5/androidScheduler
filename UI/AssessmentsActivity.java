package android.sgstudentscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.sgstudentscheduler.Entity.AssessmentEntity;
import android.sgstudentscheduler.R;
import android.sgstudentscheduler.ViewModel.AssessmentViewModel;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.sgstudentscheduler.Util.Constants.EXTRA_ASSESSMENT_DATE;
import static android.sgstudentscheduler.Util.Constants.EXTRA_ASSESSMENT_NAME;
import static android.sgstudentscheduler.Util.Constants.EXTRA_COURSE_ID;

public class AssessmentsActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID_FK = "android.sgstudentscheduler.EXTRA_COURSE_ID";
    public static final String EXTRA_ASSESSMENT_ID = "android.sgstudentscheduler.EXTRA_ASSESSMENT_ID";
    public static final String EXTRA_ASSESSMENT_STATUS = "android.sgstudentscheduler.EXTRA_ASSESSMENT_STATUS";
    public static final String EXTRA_ASSESSMENT_TYPE = "android.sgstudentscheduler.EXTRA_ASSESSMENT_TYPE";
    public static final String EXTRA_COURSE_NAME = "android.sgstudentscheduler.EXTRA_COURSE_NAME";
    public static final int ADD_ASSESSMENT_REQUEST = 1;
    public static final int EDIT_ASSESSMENT_REQUEST = 2;

    private AssessmentViewModel mAssessmentViewModel;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        setTitle((getIntent().getStringExtra(EXTRA_COURSE_NAME)) + " Assessments");

        courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);

        RecyclerView recyclerView = findViewById(R.id.assessment_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AssessmentAdapter mAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(mAdapter);

        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mAssessmentViewModel.getAssessmentList(courseId).observe(this, mAdapter::setAssessments);

        mAdapter.setOnItemClickListener(assessment -> {
            Intent intent = new Intent(AssessmentsActivity.this, AssessmentDetails.class);
            intent.putExtra(EXTRA_ASSESSMENT_ID, assessment.getAssessmentId());
            intent.putExtra(EXTRA_ASSESSMENT_NAME, assessment.getAssessmentName());
            intent.putExtra(EXTRA_ASSESSMENT_TYPE, assessment.getAssessmentType());
            intent.putExtra(EXTRA_ASSESSMENT_DATE, assessment.getAssessmentDate());
            intent.putExtra(EXTRA_ASSESSMENT_STATUS, assessment.getAssessmentStatus());
            intent.putExtra(EXTRA_COURSE_ID_FK, courseId);
            startActivityForResult(intent, EDIT_ASSESSMENT_REQUEST);
        });
    }

    public void addAssessment(View view) {
        Intent intent = new Intent(AssessmentsActivity.this, AssessmentDetails.class);
        intent.putExtra(EXTRA_COURSE_ID_FK, courseId);
        startActivityForResult(intent, ADD_ASSESSMENT_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == ADD_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {

            AssessmentEntity assessment = new AssessmentEntity(
                    intent.getStringExtra(EXTRA_ASSESSMENT_NAME),
                    intent.getStringExtra(EXTRA_ASSESSMENT_TYPE),
                    intent.getStringExtra(EXTRA_ASSESSMENT_DATE),
                    intent.getStringExtra(EXTRA_ASSESSMENT_STATUS),
                    intent.getIntExtra(AssessmentDetails.EXTRA_COURSE_ID_FK, -1));

            mAssessmentViewModel.insertAssessment(assessment);
        } else if (requestCode == EDIT_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
            int mAssessmentId = intent.getIntExtra(AssessmentDetails.EXTRA_ASSESSMENT_ID, -1);

            AssessmentEntity assessment = new AssessmentEntity(
                    intent.getStringExtra(EXTRA_ASSESSMENT_NAME),
                    intent.getStringExtra(EXTRA_ASSESSMENT_TYPE),
                    intent.getStringExtra(EXTRA_ASSESSMENT_DATE),
                    intent.getStringExtra(EXTRA_ASSESSMENT_STATUS),

                    intent.getIntExtra(AssessmentDetails.EXTRA_COURSE_ID_FK, -1));
            assessment.setAssessmentId(mAssessmentId);
            mAssessmentViewModel.updateAssessment(assessment);
        }
    }
}