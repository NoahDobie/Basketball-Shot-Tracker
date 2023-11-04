package com.example.basketballshottracker.ui.statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.basketballshottracker.R;
import com.example.basketballshottracker.databinding.FragmentStatsBinding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

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
        PieChart statsChart = (PieChart) root.findViewById(R.id.statsChart);
        setupChart(statsChart);
        setChartData(statsChart);

        return root;
    }

    //Just needed to setup the chart, once setup does not need to be called again
    private void setupChart(PieChart chart) {
        chart.setDrawHoleEnabled(true);
        chart.setUsePercentValues(true);
        chart.setEntryLabelTextSize(16);
        chart.setEntryLabelColor(Color.BLACK);
        chart.setCenterText("Accuracy");
        chart.setCenterTextSize(24);
        chart.setCenterTextColor(Color.BLACK);
        chart.getDescription().setEnabled(false);
    }
    //Can be modified to be public
    //Then we can create a ArrayList that stores the current session info
    //To then update this dynamically as the user progresses
    //Then we store all previous data in Arraylists as well
    private void setChartData(PieChart chart) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(60, "Made"));
        entries.add(new PieEntry(40, "Missed"));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(getContext(), R.color.accent));
        colors.add(ContextCompat.getColor(getContext(), R.color.darkSecondary));

        PieDataSet dataSet = new PieDataSet(entries, "Accuracy");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(22f);
        data.setValueTextColor(Color.BLACK);

        chart.setData(data);
        chart.invalidate();

        chart.animateY(1000, Easing.EaseInOutQuad);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}