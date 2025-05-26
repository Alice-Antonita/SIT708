package deakin.example.studentlearningapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class HistoryFragment extends Fragment {
    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView rv = root.findViewById(R.id.rvHistory);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new HistoryAdapter(new ArrayList<>());
        rv.setAdapter(adapter);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<QuizHistory> list =
                    AppDatabase.getInstance(getContext()).historyDao().getAll();
            requireActivity().runOnUiThread(() -> adapter.updateData(list));
        });

        return root;
    }
}
