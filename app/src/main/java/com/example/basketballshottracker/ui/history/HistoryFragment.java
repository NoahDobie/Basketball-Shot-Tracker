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
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
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

        // Creating a dictionary to hold dummy data
        // contains key value pairs where the key is the date and the integer array contains the values
        // the values will be in the order of shots made, shots missed, and shots taken
        Dictionary<String, int[]> dict = new Hashtable<>();
        dict.put("November 1st", new int[]{13, 14, 14});
        dict.put("November 3rd", new int[]{5, 10, 20});
        dict.put("November 4th", new int[]{1, 2, 3});

        createGroupList(dict);
        createCollection(dict);
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

    // Hopefully we can read from a dictionary now
    // Successfully grabs from dictionary and gets correct string data
    // Not in order though hmm
    private void createCollection(Dictionary dict) {
        mCollection = new HashMap<String, List<String>>();
        Enumeration<String> keys = dict.keys();
        while (keys.hasMoreElements()){
            for (String group : groupList) {
                String key = keys.nextElement();
                if (group.equals(key))
                    loadChild(Arrays.toString((int[]) dict.get(key)).split("[\\[\\]]")[1].split(", "));
                //if (group.equals("Session 1")) {
                //    loadChild(session1stat);
                mCollection.put(group, childList);
            }
            //else if (group.equals("Session 2")) {
            //    loadChild(session2stat);
            //}

        }

    }

    // Find a way to label where the data is from??
    private void loadChild(String[] sessionstats) {
        childList = new ArrayList<>();
        for (String model : sessionstats) {
            childList.add(model);
        }
    }

    // Losing my mind
    private void createGroupList(Dictionary dict) {
        groupList = new ArrayList<>();
        Enumeration<String> keys = dict.keys();
        while (keys.hasMoreElements()) {
            groupList.add(keys.nextElement());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}