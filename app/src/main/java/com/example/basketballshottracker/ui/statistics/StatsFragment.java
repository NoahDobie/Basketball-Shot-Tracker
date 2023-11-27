//COMP 3450: Saifullah Chandio T00657965, Jacob Harris T00657013, Noah Dobie T00661661
package com.example.basketballshottracker.ui.statistics;

import static com.example.basketballshottracker.Data.formatDate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.basketballshottracker.ChartManipulation;
import com.example.basketballshottracker.Data;
import com.example.basketballshottracker.R;
import com.example.basketballshottracker.databinding.FragmentStatsBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

public class StatsFragment extends androidx.fragment.app.Fragment {

    private FragmentStatsBinding binding;
    TabLayout tabLayout;
    TextView madeText, missText, totalText, noEntriesText;
    PieChart statsChart;
    Dictionary<String, int[]> dict;
    String noEntiresString;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatsViewModel viewModel =
                new ViewModelProvider(this).get(StatsViewModel.class);

        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize dictionary from Data
        dict = Data.initDict();

        //Create chart for statistics screen
        statsChart = root.findViewById(R.id.statsChart);
        ChartManipulation.setupChart(getActivity(), statsChart);

        //Set textViews for stats display
        //Makes, Misses, Total
        madeText = root.findViewById(R.id.shotsMade);
        missText = root.findViewById(R.id.shotsMissed);
        totalText = root.findViewById(R.id.totalShots);

        // TextView for if there are no entries for the chart.
        // Default is daily
        noEntriesText = root.findViewById(R.id.noEntriesTextView);
        noEntiresString = getResources().getString(R.string.No_Entries);

        //SHOULD CREATE A METHOD TO CREATE THIS PIEENTRY ARRAYLIST
        //THAT GATHERS THE DATA ITSELF FROM THE TRACKING

        //Set tabLayout for the tabs
        tabLayout = root.findViewById(R.id.tabLayout);

        // Set for first tab, which is daily
        // Value to pass: DAILY = 1, WEEKLY = 2, MONTHLY = 30, LIFETIME = 10000+
        setChartData(1);

        //When the tabs are selected
        // For each tab position the date is filtered and the data is re-added to the
        // already existing elements, no difference in the code for each case except for the
        // date calculation.
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setChartData(1);
                        break;
                    case 1:
                        setChartData(7);
                        break;
                    case 2:
                        setChartData(30);
                        break;
                    case 3:
                        setChartData(10000);
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

    private void setChartData(int timeSpan) {
        int made = 0, missed = 0, total = 0;
        int[] tempArr = {};

        Enumeration<String> keys = dict.keys();
        String todayString = formatDate(new Date());
        String timeSpanText;

        switch (timeSpan) {
            case 1:
                timeSpanText = " today!";
                break;
            case 7:
                timeSpanText = " this week!";
                break;
            case 30:
                timeSpanText = " this month!";
                break;
            default:
                timeSpanText = "!"; // You might want to handle other cases or provide a default value
                break;
        }

        noEntriesText.setText(noEntiresString + timeSpanText);

        while (keys.hasMoreElements()) {
            String date = keys.nextElement();
            if (daysBetweenDates(date, todayString) < timeSpan) {
                tempArr = dict.get(date);
                made += tempArr[0];
                missed += tempArr[1];
                total += (tempArr[0] + tempArr[1]);
            }
        }

        if (tempArr.length == 0) {
            statsChart.setVisibility(View.GONE);
            noEntriesText.setVisibility(View.VISIBLE);
        } else {
            statsChart.setVisibility(View.VISIBLE);
            noEntriesText.setVisibility(View.GONE);
            ArrayList<PieEntry> shotStats = new ArrayList<>();
            shotStats.add(new PieEntry(made, "Made"));
            shotStats.add(new PieEntry(missed, "Missed"));
            ChartManipulation.setChartData(getActivity(), statsChart, shotStats);
        }
        // Get first position in array for makes
        madeText.setText(String.valueOf(made));
        // Get second position in array for misses
        missText.setText(String.valueOf(missed));
        // Combine both for total
        totalText.setText(String.valueOf(total));
    }

    private int daysBetweenDates(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE - MMMM d, yyyy");
        try {
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            long diffInMillies = Math.abs(d2.getTime() - d1.getTime());
            return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}