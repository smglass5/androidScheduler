package android.sgstudentscheduler.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "termTable")
public class TermEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "termId")
    private int termId;
    @ColumnInfo(name = "termName")
    private String termName;
    @ColumnInfo(name = "termStart")
    private String termStart;
    @ColumnInfo(name = "termEnd")
    private String termEnd;

    @Ignore
    public TermEntity(int termId, String termName, String termStart, String termEnd) {
        this.termId = termId;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }


    public TermEntity(String termName, String termStart, String termEnd) {
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    @Override
    public String toString() {
        return "TermEntity{" +
                "termId=" + termId +
                ", termName='" + termName + '\'' +
                ", termStart='" + termStart + '\'' +
                ", termEnd='" + termEnd + '\'' +
                '}';
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }
}
