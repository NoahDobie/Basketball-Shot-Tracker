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

import java.util.ArrayList;

public class StatsFragment extends androidx.fragment.app.Fragment {

    private FragmentStatsBinding binding;

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

        // !!!!! TEMPORARY JUST FOR PROTOTYPING !!!!!
        //Create fake chart data
        ArrayList<PieEntry> shotStats = new ArrayList<>();
        //Add entries (makes, misses)
        shotStats.add(new PieEntry(60, "Made"));
        shotStats.add(new PieEntry(40, "Missed"));
        ChartManipulation.setChartData(getActivity(), statsChart, shotStats);

        //Set textview data
        int makes = Math.round(shotStats.get(0).getValue());
        int misses = Math.round(shotStats.get(1).getValue());
        int total = makes + misses;
        //Get first position in array for makes
        madeText.setText(String.valueOf(makes));
        //Get second position in array for misses
        missText.setText(String.valueOf(misses));
        //Combine both for total
        totalText.setText(String.valueOf(total));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}