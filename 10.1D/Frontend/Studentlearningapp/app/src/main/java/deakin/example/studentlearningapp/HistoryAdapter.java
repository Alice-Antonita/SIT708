package deakin.example.studentlearningapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HVH> {
    private final List<QuizHistory> data;
    public HistoryAdapter(List<QuizHistory> data) { this.data = data; }

    public void updateData(List<QuizHistory> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new HVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HVH holder, int position) {
        QuizHistory h = data.get(position);
        holder.tvQ.setText(h.question);
        holder.tvYour.setText(h.yourAnswer);
        holder.tvCorrect.setText(h.correctAnswer);
        holder.tvTime.setText(
                DateFormat.getDateTimeInstance().format(new Date(h.timestamp))
        );
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class HVH extends RecyclerView.ViewHolder {
        TextView tvQ, tvYour, tvCorrect, tvTime;
        HVH(@NonNull View itemView) {
            super(itemView);
            tvQ = itemView.findViewById(R.id.tvHistQuestion);
            tvYour = itemView.findViewById(R.id.tvHistYour);
            tvCorrect = itemView.findViewById(R.id.tvHistCorrect);
            tvTime = itemView.findViewById(R.id.tvHistTime);
        }
    }
}
