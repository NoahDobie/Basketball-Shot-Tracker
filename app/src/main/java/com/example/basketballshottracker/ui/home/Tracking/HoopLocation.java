//COMP 3450: Saifullah Chandio T00657965, Jacob Harris T00657013, Noah Dobie T00661661
package com.example.basketballshottracker.ui.home.Tracking;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Point;
import org.opencv.core.Rect;

public class HoopLocation implements View.OnTouchListener {

    private final String TAG = "TOUCH";

    private boolean isHoopSet = false;
    private boolean isWidthHeightSet = false;
    private Rect hoopRect;

    private final int layoutWidth;
    private final int layoutHeight;
    private int frameWidth;
    private int frameHeight;

    public HoopLocation(CameraBridgeViewBase cameraView) {
        layoutWidth = cameraView.getWidth();
        layoutHeight = cameraView.getHeight();
        Log.d(TAG, "Layout Width = " + layoutWidth + " Layout Height = " + layoutHeight);

        int[] frameSize = cameraView.getPreviewFrameSize();
        frameHeight = frameSize[0];
        frameWidth = frameSize[1];
        setFrameWidthHeight(frameWidth, frameHeight);

        cameraView.setOnTouchListener(this);
    }

    public Rect getHoopRect() {
        return hoopRect;
    }

    public boolean isHoopSet() {
        return isHoopSet;
    }

    public boolean isFrameWidthHeightSet() {
        return isWidthHeightSet;
    }

    public void setFrameWidthHeight(int width, int height) {
        frameWidth = width;
        frameHeight = height;

        isWidthHeightSet = true;
        Log.d(TAG, "FRAME WIDTH = " + frameWidth + ", FRAME HEIGHT = " + frameHeight);
    }

    public void setHoopRect(double x, double y) {
        // Define hoop bounding box size - modify based on testing
        float hoopSize = 100;

        Point topLeft = new Point(x, y);
        Point bottomRight = new Point(x + hoopSize, y + hoopSize);

        hoopRect = new Rect(topLeft, bottomRight);

        Log.d(TAG, "BOX TL: " + topLeft + ", BOX BR: " + bottomRight);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isHoopSet) {
            return false;
        }

        isHoopSet = true;
        Log.d(TAG, "IS HOOP SET = " + isHoopSet());

        // Adjust coordinates to match OpenCV's coordinate system
        double x = event.getY();  // Swap X and Y, and reverse Y
        double y = event.getX();  // Swap X and Y

        Log.d(TAG, "Touch X =  " + x + ", Touch Y = " + y);

        // User taps the screen to set the location
        setHoopRect(x, y);

        v.performClick();

        return true;
    }
}
