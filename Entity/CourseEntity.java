package android.sgstudentscheduler.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "courseTable",
        foreignKeys = {
                @ForeignKey(
                        entity = TermEntity.class,
                        parentColumns = "termId",
                        childColumns = "termId_fk",
                onDelete = CASCADE)}
        )

public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "courseId")
    private int courseId;
    @ColumnInfo(name = "courseName")
    private String courseName;
    @ColumnInfo(name = "courseStart")
    private String courseStart;
    @ColumnInfo(name = "courseEnd")
    private String courseEnd;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "mentorName")
    private String mentorName;
    @ColumnInfo(name = "mentorPhone")
    private String mentorPhone;
    @ColumnInfo(name = "mentorEmail")
    private String mentorEmail;
    @ColumnInfo(name = "courseNotes")
    private String courseNotes;
    @ColumnInfo(name = "termId_fk")
    private int termId_fk;


    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseStart='" + courseStart + '\'' +
                ", courseEnd='" + courseEnd + '\'' +
                ", status='" + status + '\'' +
                ", mentorName='" + mentorName + '\'' +
                ", mentorPhone='" + mentorPhone + '\'' +
                ", mentorEmail='" + mentorEmail + '\'' +
                ", courseNotes='" + courseNotes + '\'' +
                '}';
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    @Ignore
    public CourseEntity(int courseId, String courseName, String courseStart, String courseEnd, String status,
                        String mentorName, String mentorPhone, String mentorEmail, String courseNotes, int termId_fk) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.courseNotes = courseNotes;
        this.termId_fk = termId_fk;
    }

    public CourseEntity() {

    }

    public CourseEntity(String courseName, String courseStart, String courseEnd, String status,
                        String mentorName, String mentorPhone, String mentorEmail, String courseNotes, int termId_fk) {
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.courseNotes = courseNotes;
        this.termId_fk = termId_fk;

    }


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }


    public int getTermId_fk() {
        return termId_fk;
    }

    public void setTermId_fk(int termId_fk) {
        this.termId_fk = termId_fk;
    }

}