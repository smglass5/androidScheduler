package android.sgstudentscheduler.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessmentTable",
        foreignKeys = @ForeignKey(
                entity = CourseEntity.class,
                parentColumns = "courseId",
                childColumns = "courseId_fk",
                onDelete = CASCADE
        )
)

public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessmentId")
    private int assessmentId;
    @ColumnInfo(name = "assessmentName")
    private String assessmentName;
    @ColumnInfo(name = "assessmentType")
    private String assessmentType;
    @ColumnInfo(name = "assessmentDate")
    private String assessmentDate;
    @ColumnInfo(name = "assessmentStatus")
    private String assessmentStatus;
    @ColumnInfo(name = "courseId_fk")
    private int courseId_fk;

    @Ignore
    public AssessmentEntity(int assessmentId, String assessmentName, String assessmentType,
                            String assessmentDate, String assessmentStatus, int courseId_fk) {
        this.assessmentId = assessmentId;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentDate = assessmentDate;
        this.assessmentStatus = assessmentStatus;
        this.courseId_fk = courseId_fk;
    }

    public AssessmentEntity(String assessmentName, String assessmentType, String assessmentDate,
                            String assessmentStatus, int courseId_fk) {
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentDate = assessmentDate;
        this.assessmentStatus = assessmentStatus;
        this.courseId_fk = courseId_fk;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(String assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public int getCourseId_fk() {
        return courseId_fk;
    }

    public void setCourseId_fk(int courseId_fk) {
        this.courseId_fk = courseId_fk;
    }


    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "assessmentId=" + assessmentId +
                ", assessmentName='" + assessmentName + '\'' +
                ", assessmentType='" + assessmentType + '\'' +
                ", assessmentDate='" + assessmentDate + '\'' +
                ", assessmentStatus='" + assessmentStatus + '\'' +
                '}';
    }
}
