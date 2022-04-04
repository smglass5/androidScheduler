package android.sgstudentscheduler.ViewModel;

import android.app.Application;
import android.sgstudentscheduler.Database.SchoolRepository;
import android.sgstudentscheduler.Entity.TermEntity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermViewModel extends AndroidViewModel {
    private final SchoolRepository mRepository;
    public LiveData<List<TermEntity>> allTerms;
    public MutableLiveData<TermEntity>mLiveTerm = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public TermViewModel(@NonNull Application application) {
        super(application);
        mRepository = SchoolRepository.getInstance(application.getApplicationContext());
        allTerms = mRepository.getAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }

    public int getTermCourses(int termId) {
        return mRepository.getTermCourses(termId);
    }

    public void insertTerm(TermEntity term) {
        mRepository.insertTerm(term);
    }

    public void updateTerm(TermEntity term) {
        mRepository.updateTerm(term);
    }

    public void deleteTerm(TermEntity term) {
        mRepository.deleteTerm(term);
    }

    public void populateData() {
        mRepository.populateData();
    }

    public void clearData() {
        mRepository.clearData();
    }

    public void loadTerm(int termId) {
        executor.execute(() -> {
            TermEntity term = mRepository.getTermById(termId);
            mLiveTerm.postValue(term);
        });
    }
}
