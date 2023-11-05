package com.example.basketballshottracker.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.basketballshottracker.R;
import com.example.basketballshottracker.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryFragment extends Fragment {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> mCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    private FragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistoryViewModel historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Context thiscontext = container.getContext();

        createGroupList();
        createCollection();
        expandableListView = root.findViewById(R.id.listStats);
        expandableListAdapter = new MyExpandableListAdapter(thiscontext, groupList, mCollection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        final TextView textView = binding.textHistory;
        historyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;


    }

    private void createCollection() {
        String [] session1stat = {"60"};
        String [] session2stat = {"40"};
        mCollection = new HashMap<String, List<String>>();
        for (String group : groupList) {
            if (group.equals("Session 1")) {
                loadChild(session1stat);
            }
            else if (group.equals("Session 2")) {
                loadChild(session2stat);
            }
            mCollection.put(group, childList);
        }
    }

    private void loadChild(String[] sessionstats) {
        childList = new ArrayList<>();
        for (String model : sessionstats) {
            childList.add(model);
        }
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("Session 1");
        groupList.add("Session 2");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}