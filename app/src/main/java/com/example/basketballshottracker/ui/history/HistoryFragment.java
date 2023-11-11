//COMP 3450: Saifullah Chandio T00657965, Jacob Harris T00657013, Noah Dobie T00661661
package com.example.basketballshottracker.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.basketballshottracker.R;
import com.example.basketballshottracker.databinding.FragmentHistoryBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        Dictionary<Date, int[]> dict1 = new Hashtable<>();
        dict1.put(new Date(123,10,2), new int[]{13, 14, 27});
        dict1.put(new Date(123,10,6), new int[]{5, 10, 15});
        dict1.put(new Date(123,10,7), new int[]{1, 2, 3});
        dict1.put(new Date(123,10,12), new int[]{21, 12, 33});

        //Still not in order
        // Changes dates from date type to string because I'm too scared to change
        // how the rest of the functionality works.
        Enumeration<Date> enu = dict1.keys();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d, yyyy");

        Dictionary<String, int[]> dict = new Hashtable<>();
        while (enu.hasMoreElements()){
            Date key = enu.nextElement();
            dict.put(dateFormat.format(key), dict1.get(key));
        }

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
                if (group.equals(key)) {
                    loadChild(Arrays.toString((int[]) dict.get(key)).split("[\\[\\]]")[1].split(", "));
                }
                mCollection.put(group, childList);
            }

        }

    }

    // Find a way to label where the data is from??
    // Goofy ass labels
    private void loadChild(String[] sessionstats) {
        childList = new ArrayList<>();
        int i = 0;
        while (i < 3){
        for (String model : sessionstats) {
                if (i == 0){
                    childList.add("Shots Made: " + model);
                }
                else if (i == 1){
                    childList.add("Shots Missed: " + model);
                }
                else if (i == 2){
                    childList.add("Shots Taken: " + model);
                }
            i++;
            }
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