package android.sgstudentscheduler.ViewModel;

import android.app.Application;
import android.sgstudentscheduler.Database.SchoolRepository;
import android.sgstudentscheduler.Entity.AssessmentEntity;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentViewModel extends AndroidViewModel {
    private final SchoolRepository mRepository;
    public LiveData<List<AssessmentEntity>> allAssessments;
    public MutableLiveData<AssessmentEntity> mLiveAssessment = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public AssessmentViewModel(Application application) {
        super(application);
        mRepository = new SchoolRepository(application);
        allAssessments = mRepository.getAllAssessments();
    }

    public LiveData<List<AssessmentEntity>> getAssessmentList(int courseId_fk) {
        return mRepository.getAssessmentList(courseId_fk);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return allAssessments;
    }

    public void insertAssessment(AssessmentEntity assessment) {
        mRepository.insertAssessment(assessment);
    }

    public void updateAssessment(AssessmentEntity assessment) {
        mRepository.updateAssessment(assessment);
    }

    public void deleteAssessment(AssessmentEntity assessment) {
        mRepository.deleteAssessment(assessment);
    }

    public void loadAssessment(int assessmentId) {
        executor.execute(() -> {
            AssessmentEntity assessment = mRepository.getAssessmentById(assessmentId);
            mLiveAssessment.postValue(assessment);
        });
    }
}
