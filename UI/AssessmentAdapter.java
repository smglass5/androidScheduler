package android.sgstudentscheduler.UI;

import android.content.Context;
import android.sgstudentscheduler.Entity.AssessmentEntity;
import android.sgstudentscheduler.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private LayoutInflater mInflater;
    private final Context context;
    private List<AssessmentEntity> mAssessments;
    private OnItemClickListener listener;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mAssessments.get(position));
                    }
                }
            });
        }

    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {

        if (mAssessments != null) {
            AssessmentEntity current = mAssessments.get(position);
            holder.assessmentItemView.setText(current.getAssessmentName());

        } else {
            // Covers the case of data not being ready yet.
            holder.assessmentItemView.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }

    public void setAssessments(List<AssessmentEntity> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(AssessmentEntity assessment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
