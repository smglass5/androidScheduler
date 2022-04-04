package android.sgstudentscheduler.Database;

import android.content.Context;
import android.sgstudentscheduler.DAO.AssessmentDAO;
import android.sgstudentscheduler.DAO.CourseDAO;
import android.sgstudentscheduler.DAO.TermDAO;
import android.sgstudentscheduler.Entity.AssessmentEntity;
import android.sgstudentscheduler.Entity.CourseEntity;
import android.sgstudentscheduler.Entity.TermEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 4, exportSchema = false)
public abstract class SchoolDatabase extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    private static volatile SchoolDatabase INSTANCE;
    public static final String DB_NAME = "scheduler.db";
    private static final Object LOCK = new Object();

    public static SchoolDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchoolDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

