package android.sgstudentscheduler.ViewModel;

import android.app.Application;
import android.sgstudentscheduler.Database.SchoolRepository;
import android.sgstudentscheduler.Entity.CourseEntity;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseViewModel extends AndroidViewModel {
    private final SchoolRepository mRepository;
    public LiveData<List<CourseEntity>> allCourses;
    public MutableLiveData<CourseEntity> mLiveCourse = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CourseViewModel(Application application) {
        super(application);
        mRepository = new SchoolRepository(application);
        allCourses = mRepository.getAllCourses();

    }

    public LiveData<List<CourseEntity>> getCourseList(int termId_fk) {
        return mRepository.getCourseList(termId_fk);
    }

    public LiveData<List<CourseEntity>> getAllCourses() {
        return allCourses;
    }

    public void insertCourse(CourseEntity course) {
        mRepository.insertCourse(course);
    }

    public void updateCourse(CourseEntity course) {
        mRepository.updateCourse(course);
    }

    public void deleteCourse(CourseEntity course) {
        mRepository.deleteCourse(course);
    }

    public void loadCourse(int courseId) {
        executor.execute(() -> {
            CourseEntity course = mRepository.getCourseById(courseId);
            mLiveCourse.postValue(course);
        });
    }
}
