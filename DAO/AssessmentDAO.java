package android.sgstudentscheduler.DAO;

import android.sgstudentscheduler.Entity.AssessmentEntity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Query("SELECT * FROM assessmentTable WHERE courseId_fk = :courseId")
    LiveData<List<AssessmentEntity>> getAssessmentList(int courseId);

    @Query("SELECT * FROM assessmentTable WHERE assessmentId = :assessmentId")
    AssessmentEntity getAssessmentById(int assessmentId);

    @Query("SELECT * FROM assessmentTable")
    LiveData<List<AssessmentEntity>> getAllAssessments();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAssessment(AssessmentEntity assessment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(AssessmentEntity assessment);

    @Delete
    void deleteAssessment(AssessmentEntity assessment);

    @Query("DELETE FROM assessmentTable")
    void deleteAllAssessments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssessmentEntity> assessments);
}
