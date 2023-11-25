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

    private double reverseMap(double value, double inMin, double inMax, double outMin, double outMax) {
        return (value - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

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

        double touchX = event.getX();
        double touchY = event.getY();
        Log.d(TAG, "Touch X =  " + touchX + ", Touch Y = " + touchY);

        // Reverse mapping touch coordinates to frame dimensions with an adjustment
        double frameX = reverseMap(touchX, 0, layoutWidth, frameWidth, 0);
        double frameY = reverseMap(touchY, 0, layoutHeight, 0, frameHeight);

        // Correct for the hoop size and adjust for the top-left position of the hoop
        double hoopSize = 100;
        double x = frameX - hoopSize / 2; // Adjusting for hoop size
        double y = frameY - hoopSize / 2; // Adjusting for hoop size

        // User taps the screen to set the location
        setHoopRect(x, y);

        v.performClick();

        return true;
    }
}
