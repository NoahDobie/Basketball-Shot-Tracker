package com.example.basketballshottracker.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.basketballshottracker.R;
import com.example.basketballshottracker.databinding.FragmentStatsBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class StatsFragment extends androidx.fragment.app.Fragment {

    private FragmentStatsBinding binding;
    TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatsViewModel viewModel =
                new ViewModelProvider(this).get(StatsViewModel.class);

        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Create chart for statistics screen
        PieChart statsChart = root.findViewById(R.id.statsChart);
        ChartManipulation.setupChart(getActivity(), statsChart);

        //Set textViews for stats display
        //Makes, Misses, Total
        TextView madeText = root.findViewById(R.id.shotsMade);
        TextView missText = root.findViewById(R.id.shotsMissed);
        TextView totalText = root.findViewById(R.id.totalShots);

        //SHOULD CREATE A METHOD TO CREATE THIS PIEENTRY ARRAYLIST
        //THAT GATHERS THE DATA ITSELF FROM THE TRACKING

        //Set tabLayout for the tabs
        tabLayout = root.findViewById(R.id.tabLayout);

        //New dictionary for testing, includes date as key value
        // the values will be in the order of shots made, shots missed, and shots taken
        Dictionary<Date, int[]> dict = new Hashtable<>();

        //Year is 1900 + the year you put in
        // Months is an ARRAY too
        // the values will be in the order of shots made, shots missed, and shots taken
        dict.put(new Date(123,10,5), new int[]{13, 14, 27});
        dict.put(new Date(123,9,5), new int[]{20, 10, 30});
        dict.put(new Date(122,10,5), new int[]{14, 1, 15});
        dict.put(new Date(123,10,10), new int[]{25, 20, 45});

        int millisec = 1000 * 60 * 60 * 24;

        int made=0, missed=0, taken = 0;
        Enumeration<Date> keys = dict.keys();
        Date today = new Date();
        while (keys.hasMoreElements()){
            Date k = keys.nextElement();
            if(((today.getTime() - k.getTime()) / millisec) < 1){
                int[] tempArr = dict.get(k);
                made += tempArr[0];
                missed += tempArr[1];
                taken += tempArr[2];
            }
        }
        ArrayList<PieEntry> shotStats1 = new ArrayList<>();
        shotStats1.add(new PieEntry(made, "Made"));
        shotStats1.add(new PieEntry(missed, "Missed"));
        ChartManipulation.setChartData(getActivity(), statsChart, shotStats1);

        //Get first position in array for makes
        madeText.setText(String.valueOf(made));
        //Get second position in array for misses
        missText.setText(String.valueOf(missed));
        //Combine both for total
        totalText.setText(String.valueOf(taken));

        //When the tabs are selected
        // For each tab position the date is filtered and the data is re-added to the
        // already existing elements, no difference in the code for each case except for the
        // date calculation.
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int made=0, missed=0, taken = 0;
                Enumeration<Date> keys = dict.keys();
                Date today = new Date();
                switch (tab.getPosition()) {
                    case 0:
                        while (keys.hasMoreElements()){
                            Date k = keys.nextElement();
                            if(((today.getTime() - k.getTime()) / millisec) < 1){
                                int[] tempArr = dict.get(k);
                                made += tempArr[0];
                                missed += tempArr[1];
                                taken += tempArr[2];
                            }
                        }
                        ArrayList<PieEntry> shotStats1 = new ArrayList<>();
                        shotStats1.add(new PieEntry(made, "Made"));
                        shotStats1.add(new PieEntry(missed, "Missed"));
                        ChartManipulation.setChartData(getActivity(), statsChart, shotStats1);

                        //Get first position in array for makes
                        madeText.setText(String.valueOf(made));
                        //Get second position in array for misses
                        missText.setText(String.valueOf(missed));
                        //Combine both for total
                        totalText.setText(String.valueOf(taken));

                        break;
                    case 1:
                        while (keys.hasMoreElements()){
                            Date k = keys.nextElement();
                            if(((today.getTime() - k.getTime()) / millisec) < 7){
                                int[] tempArr = dict.get(k);
                                made += tempArr[0];
                                missed += tempArr[1];
                                taken += tempArr[2];
                            }
                        }
                        ArrayList<PieEntry> shotStats2 = new ArrayList<>();
                        shotStats2.add(new PieEntry(made, "Made"));
                        shotStats2.add(new PieEntry(missed, "Missed"));
                        ChartManipulation.setChartData(getActivity(), statsChart, shotStats2);

                        //Get first position in array for makes
                        madeText.setText(String.valueOf(made));
                        //Get second position in array for misses
                        missText.setText(String.valueOf(missed));
                        //Combine both for total
                        totalText.setText(String.valueOf(taken));
                        break;
                    case 2:
                        while (keys.hasMoreElements()){
                            Date k = keys.nextElement();
                            if(((today.getTime() - k.getTime()) / millisec) < 30){
                                int[] tempArr = dict.get(k);
                                made += tempArr[0];
                                missed += tempArr[1];
                                taken += tempArr[2];
                            }
                        }
                        ArrayList<PieEntry> shotStats3 = new ArrayList<>();
                        shotStats3.add(new PieEntry(made, "Made"));
                        shotStats3.add(new PieEntry(missed, "Missed"));
                        ChartManipulation.setChartData(getActivity(), statsChart, shotStats3);

                        //Get first position in array for makes
                        madeText.setText(String.valueOf(made));
                        //Get second position in array for misses
                        missText.setText(String.valueOf(missed));
                        //Combine both for total
                        totalText.setText(String.valueOf(taken));
                        break;
                    case 3:
                        while (keys.hasMoreElements()){
                            Date k = keys.nextElement();
                                int[] tempArr = dict.get(k);
                                made += tempArr[0];
                                missed += tempArr[1];
                                taken += tempArr[2];
                        }
                        ArrayList<PieEntry> shotStats4 = new ArrayList<>();
                        shotStats4.add(new PieEntry(made, "Made"));
                        shotStats4.add(new PieEntry(missed, "Missed"));
                        ChartManipulation.setChartData(getActivity(), statsChart, shotStats4);

                        //Get first position in array for makes
                        madeText.setText(String.valueOf(made));
                        //Get second position in array for misses
                        missText.setText(String.valueOf(missed));
                        //Combine both for total
                        totalText.setText(String.valueOf(taken));
                        break;
                }
            }

            // I want to delete these but android studio doesn't like that
            // :-)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}