package android.sgstudentscheduler.UI;

import android.content.Context;
import android.sgstudentscheduler.Entity.TermEntity;
import android.sgstudentscheduler.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    private final LayoutInflater mInflater;
    private List<TermEntity> mTerms;
    private OnItemClickListener listener;


    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termTextView);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(mTerms.get(position));
                }
            });
        }
    }


    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {

        if (mTerms != null) {
            TermEntity current = mTerms.get(position);
            holder.termItemView.setText(current.getTermName());
        } else {
            holder.termItemView.setText(R.string.no_term);
        }
    }


    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }


    public void setTerms(List<TermEntity> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(TermEntity term);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
