package com.example.basketballshottracker;

import com.example.basketballshottracker.ui.history.previousSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class Data {

    private static Dictionary<String, int[]> dict;

    public static Dictionary<String, int[]> initDict() {
        dict = new Hashtable<>();
        dict.put(formatDate(new Date(123, 2, 15)), new int[]{10, 5});
        dict.put(formatDate(new Date(123, 4, 3)), new int[]{8, 6});
        dict.put(formatDate(new Date(123, 7, 21)), new int[]{15, 8});
        dict.put(formatDate(new Date(123, 8, 10)), new int[]{12, 10});
        dict.put(formatDate(new Date(123, 1, 5)), new int[]{18, 7});
        dict.put(formatDate(new Date(123, 3, 12)), new int[]{9, 3});
        dict.put(formatDate(new Date(123, 9, 17)), new int[]{11, 9});
        dict.put(formatDate(new Date(123, 5, 28)), new int[]{14, 11});
        dict.put(formatDate(new Date(123, 0, 23)), new int[]{7, 4});
        dict.put(formatDate(new Date(123, 10, 7)), new int[]{23, 17});
        dict.put(formatDate(new Date(123, 10, 20)), new int[]{16, 15});
        dict.put(formatDate(new Date(123, 10, 22)), new int[]{33, 26});

        return dict;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE - MMMM d, yyyy");
        return sdf.format(date);
    }

    public static List<previousSession> convertDictionaryToList(Dictionary<String, int[]> dictionary) {
        List<previousSession> previousSessionsList = new ArrayList<>();
        previousSession highScore = null;
        double highestPercentage = 0.00;

        Enumeration<String> keys = dictionary.keys();
        while (keys.hasMoreElements()) {
            String date = keys.nextElement();
            int[] values = dictionary.get(date);

            // Convert int array to strings
            String makes = String.valueOf(values[0]);
            String misses = String.valueOf(values[1]);
            String total = String.valueOf(values[0] + values[1]);

            // Calculate shot percentage
            double percentage = (double) values[0] / (values[0] + values[1]);

            // Create a previousSession object and add it to the list
            previousSession session = new previousSession(date, makes, misses, total);
            session.setHighScore(false); // Reset highScore for each session

            if (percentage > highestPercentage) {
                highestPercentage = percentage;
                highScore = session;
            }

            previousSessionsList.add(session);
        }

        // Set the highScore property for the session with the highest average
        if (highScore != null) {
            highScore.setHighScore(true);
        }

        // Sort the list by date in descending order
        Collections.sort(previousSessionsList, new Comparator<previousSession>() {
            @Override
            public int compare(previousSession session1, previousSession session2) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE - MMMM d, yyyy");
                    Date date1 = sdf.parse(session1.getDate());
                    Date date2 = sdf.parse(session2.getDate());
                    // Reverse the order to get most recent first
                    return date2.compareTo(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

        return previousSessionsList;
    }

}
