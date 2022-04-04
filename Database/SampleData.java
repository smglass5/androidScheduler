package android.sgstudentscheduler.Database;

import android.sgstudentscheduler.Entity.AssessmentEntity;
import android.sgstudentscheduler.Entity.CourseEntity;
import android.sgstudentscheduler.Entity.TermEntity;

import java.util.ArrayList;
import java.util.List;

public class SampleData {
    public static List<TermEntity> getTerms() {
        List<TermEntity> terms = new ArrayList<>();
        terms.add(new TermEntity(1, "Term 1", "02/01/2018", "07/31/2018"));
        terms.add(new TermEntity(2, "Term 2", "08/01/2018", "02/31/2019"));
        terms.add(new TermEntity(3, "Term 3", "02/01/2019", "07/31/2019"));
        terms.add(new TermEntity(4, "Term 4", "09/30/2019", "02/29/2020"));
        return terms;
    }
    public static List<CourseEntity> getCourses() {
        List<CourseEntity> courses = new ArrayList<>();
        courses.add(new CourseEntity(1, "College Algebra", "02/02/2018", "04/28/2018", "Completed", "Greg Doe",
                "555-428-4807", "gd@wgu.edu", "These are algebra notes", 1));
        courses.add(new CourseEntity(2, "Software I", "08/01/2018", "10/01/2018", "Dropped", "Jane Doe",
                "555-428-4808", "janed@wgu.edu", "These are Software I notes", 2));
        courses.add(new CourseEntity(3, "Tech Comm", "02/01/2019", "04/31/2019", "In Progress", "Steve Doe",
                "555-428-4809", "sd@wgu.edu", "These are Tech Comm notes", 3));
        courses.add(new CourseEntity(4, "Software II", "09/30/2019", "10/31/2019", "Not Started", "Tom Doe",
                "555-428-4810", "td@wgu.edu", "These are Software II notes", 4));
        return courses;
    }

    public static List<AssessmentEntity> getAssessments() {
        List<AssessmentEntity> assessments = new ArrayList<>();
        assessments.add(new AssessmentEntity(1, "Algebra Exam", "Objective",
                "04/21/2018", "Passed", 1));
        assessments.add(new AssessmentEntity(2, "Software I Project", "Performance",
                "09/30/2018", "Passed", 2));
        assessments.add(new AssessmentEntity(3, "Communications Exam", "Objective",
                "04/15/2019", "Failed", 3));
        assessments.add(new AssessmentEntity(4, "Software II Project", "Performance",
                "09/30/2019", "Not Taken", 4));
        return assessments;
    }

}
