package com.example.basketballshottracker.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basketballshottracker.Data;
import com.example.basketballshottracker.R;
import com.example.basketballshottracker.databinding.FragmentHistoryBinding;

import java.util.Dictionary;
import java.util.List;

public class HistoryFragment extends Fragment {

    private static final String TAG = "HistoryFragment";
    RecyclerView recyclerView;
    Dictionary<String, int[]> dict;
    List<previousSession> previousSessionsList;
    private FragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HistoryViewModel historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.historyRecyclerView);

        initData();
        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        historyAdapter historyAdapter = new historyAdapter(previousSessionsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(historyAdapter);
    }

    private void initData() {
        dict = Data.initDict();
        previousSessionsList = Data.convertDictionaryToList(dict);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}