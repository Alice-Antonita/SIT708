package deakin.example.studentlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.concurrent.Executors;

public class ShareFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        root.findViewById(R.id.btnAndroidShare).setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                int count = AppDatabase.getInstance(getContext())
                        .historyDao().getAll().size();
                String summary = "I just completed " + count + " quiz questions!";
                requireActivity().runOnUiThread(() -> {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Quiz Profile");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, summary);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                });
            });
        });

        return root;
    }
}