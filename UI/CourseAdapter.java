package android.sgstudentscheduler.UI;

import android.content.Context;
import android.sgstudentscheduler.Entity.CourseEntity;
import android.sgstudentscheduler.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final LayoutInflater mInflater;
    private List<CourseEntity> mCourses;
    private OnItemClickListener listener;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mCourses.get(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {

        if (mCourses != null) {
            CourseEntity current = mCourses.get(position);
            holder.courseItemView.setText(current.getCourseName());

        } else {
            holder.courseItemView.setText(R.string.no_course);
        }
    }


    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }

    public void setCourses(List<CourseEntity> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(CourseEntity course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
