package android.sgstudentscheduler.Database;

import android.content.Context;
import android.sgstudentscheduler.Entity.AssessmentEntity;
import android.sgstudentscheduler.Entity.CourseEntity;
import android.sgstudentscheduler.Entity.TermEntity;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SchoolRepository {

    public static final int NUM_OF_THREADS = 4;
    private static SchoolRepository instance;
    private final SchoolDatabase db;
    private final Executor executor = Executors.newFixedThreadPool(NUM_OF_THREADS);
    int count;
    private LiveData<List<TermEntity>> mAllTerms;
    private LiveData<List<CourseEntity>> mAllCourses;
    private LiveData<List<AssessmentEntity>> mAllAssessments;

    public SchoolRepository(Context context) {
        db = SchoolDatabase.getInstance(context);
        mAllTerms = getAllTerms();
        mAllCourses = getAllCourses();
        mAllAssessments = getAllAssessments();
    }

    public static SchoolRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SchoolRepository(context);
        }
        return instance;
    }


    public void populateData() {
        executor.execute(() -> {
            db.termDAO().insertAll(SampleData.getTerms());
            db.courseDAO().insertAll(SampleData.getCourses());
            db.assessmentDAO().insertAll(SampleData.getAssessments());
        });
    }


    public void clearData() {
        executor.execute(() -> {
            db.assessmentDAO().deleteAllAssessments();
            db.courseDAO().deleteAllCourses();
            db.termDAO().deleteAllTerms();
        });
    }


    public int getTermCourses(int termId) {
        executor.execute(() -> count = db.termDAO().getTermCourses(termId));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }


    public LiveData<List<TermEntity>> getAllTerms() {
        return db.termDAO().getAllTerms();
    }

    public LiveData<List<CourseEntity>> getAllCourses() {
        return db.courseDAO().getAllCourses();
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return db.assessmentDAO().getAllAssessments();
    }

    public LiveData<List<CourseEntity>> getCourseList(int termId_fk) {
        return db.courseDAO().getCourseList(termId_fk);
    }

    public LiveData<List<AssessmentEntity>> getAssessmentList(int courseId_fk) {
        return db.assessmentDAO().getAssessmentList(courseId_fk);
    }

    public TermEntity getTermById(int termId) {
        return db.termDAO().getTerm(termId);
    }

    public CourseEntity getCourseById(int courseId) {
        return db.courseDAO().getCourseById(courseId);
    }

    public AssessmentEntity getAssessmentById(int assessmentId) {
        return db.assessmentDAO().getAssessmentById(assessmentId);
    }

    public void insertTerm(final TermEntity term) {
        executor.execute(() -> db.termDAO().insertTerm(term));
    }

    public void insertCourse(final CourseEntity course) {
        executor.execute(() -> db.courseDAO().insertCourse(course));
    }

    public void insertAssessment(final AssessmentEntity assessment) {
        executor.execute(() -> db.assessmentDAO().insertAssessment(assessment));
    }

    public void updateTerm(TermEntity term) {
        executor.execute(() -> db.termDAO().updateTerm(term));
    }

    public void updateCourse(CourseEntity course) {
        executor.execute(() -> db.courseDAO().updateCourse(course));
    }

    public void updateAssessment(AssessmentEntity assessment) {
        executor.execute(() -> db.assessmentDAO().updateAssessment(assessment));
    }

    public void deleteTerm(TermEntity term) {
        executor.execute(() -> db.termDAO().deleteTerm(term));
    }

    public void deleteCourse(CourseEntity course) {
        executor.execute(() -> db.courseDAO().deleteCourse(course));
    }

    public void deleteAssessment(AssessmentEntity assessment) {
        executor.execute(() -> db.assessmentDAO().deleteAssessment(assessment));
    }
}
