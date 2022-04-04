package android.sgstudentscheduler.DAO;

import android.sgstudentscheduler.Entity.TermEntity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface TermDAO {

    @Query("SELECT * FROM termTable WHERE termId = :termId")
    TermEntity getTerm(int termId);

    @Query("SELECT * FROM termTable")
    LiveData<List<TermEntity>> getAllTerms();

    @Query("SELECT COUNT(*) FROM courseTable WHERE termId_fk = :termId")
    int getTermCourses(int termId);

    @Update
    void updateTerm(TermEntity term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity term);

    @Query("DELETE FROM termTable")
    void deleteAllTerms();

    @Delete
    void deleteTerm(TermEntity term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TermEntity> terms);
}
