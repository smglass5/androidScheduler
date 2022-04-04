package android.sgstudentscheduler.DAO;

import android.sgstudentscheduler.Entity.CourseEntity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface CourseDAO {

    @Query("SELECT * FROM courseTable WHERE termId_fk = :termId")
    LiveData<List<CourseEntity>> getCourseList(int termId);

    @Query("SELECT * FROM courseTable WHERE courseId = :courseId")
    CourseEntity getCourseById(int courseId);

    @Query("SELECT * FROM courseTable")
    LiveData<List<CourseEntity>> getAllCourses();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCourse(CourseEntity course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity course);

    @Delete
    void deleteCourse(CourseEntity course);

    @Query("DELETE FROM courseTable")
    void deleteAllCourses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CourseEntity> courses);
}
