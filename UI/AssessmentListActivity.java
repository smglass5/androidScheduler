package android.sgstudentscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.sgstudentscheduler.Entity.AssessmentEntity;
import android.sgstudentscheduler.R;
import android.sgstudentscheduler.ViewModel.AssessmentViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.sgstudentscheduler.Util.Constants.EXTRA_ASSESSMENT_DATE;
import static android.sgstudentscheduler.Util.Constants.EXTRA_ASSESSMENT_NAME;
import static android.sgstudentscheduler.Util.Constants.EXTRA_ASSESSMENT_STATUS;
import static android.sgstudentscheduler.Util.Constants.EXTRA_ASSESSMENT_TYPE;

public class AssessmentListActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID_FK = "android.sgstudentscheduler.EXTRA_COURSE_ID";
    public static final String EXTRA_ASSESSMENT_ID = "android.sgstudentscheduler.EXTRA_ASSESSMENT_ID";
    public static final int EDIT_ASSESSMENT_REQUEST = 2;

    private AssessmentViewModel mAssessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        RecyclerView recyclerView = findViewById(R.id.assessmentList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AssessmentAdapter mAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(mAdapter);

        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mAssessmentViewModel.getAllAssessments().observe(this, mAdapter::setAssessments);

        mAdapter.setOnItemClickListener(assessment -> {
            Intent intent = new Intent(this, AssessmentDetails.class);
            intent.putExtra(EXTRA_ASSESSMENT_ID, assessment.getAssessmentId());
            intent.putExtra(EXTRA_ASSESSMENT_NAME, assessment.getAssessmentName());
            intent.putExtra(EXTRA_ASSESSMENT_TYPE, assessment.getAssessmentType());
            intent.putExtra(EXTRA_ASSESSMENT_DATE, assessment.getAssessmentDate());
            intent.putExtra(EXTRA_ASSESSMENT_STATUS, assessment.getAssessmentStatus());
            intent.putExtra(EXTRA_COURSE_ID_FK, assessment.getCourseId_fk());
            startActivityForResult(intent, EDIT_ASSESSMENT_REQUEST);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

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