package com.example.basketballshottracker.ui.statistics;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.example.basketballshottracker.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class ChartManipulation {

    //Just needed to setup the chart, once setup does not need to be called again
    public static void setupChart(Context context, PieChart chart) {
        //General Format
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setDrawRoundedSlices(false);
        chart.getLegend().setEnabled(false);
        //Center Hole
        chart.setDrawHoleEnabled(true);
        chart.setCenterText("Accuracy");
        chart.setCenterTextSize(24);
        chart.setCenterTextColor(Color.WHITE);
        chart.setTransparentCircleRadius(0);
        chart.setHoleRadius(40);
        chart.setHoleColor(0);
        //Entries
        chart.setEntryLabelTextSize(20);
        chart.setEntryLabelColor(Color.WHITE);
    }

    //Can be modified to be public
    //Then we can create a ArrayList that stores the current session info
    //To then update this dynamically as the user progresses
    //Then we store all previous data in Arraylists as well
    public static void setChartData(Context context, PieChart chart, ArrayList<PieEntry> shotStats) {

        //Setting colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(context, R.color.accent));
        colors.add(ContextCompat.getColor(context, R.color.darkSecondary));
        PieDataSet dataSet = new PieDataSet(shotStats, "");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(26f);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();

        chart.animateY(1000, Easing.EaseInOutCirc);
    }

}