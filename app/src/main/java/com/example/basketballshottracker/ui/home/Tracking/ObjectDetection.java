//COMP 3450: Saifullah Chandio T00657965, Jacob Harris T00657013, Noah Dobie T00661661
package com.example.basketballshottracker.ui.home.Tracking;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ObjectDetection {

    private float[] radius = new float[1]; // Declare radius as a class variable
    private Point previousCenter = null;

    // Finding basketball using colour (orange)
    // Creates 2 Mat - 1 for the camera view, 1 for the mask
    // Mask blocks other colours except defined range
    public Mat detectBasketball(Mat frame) {
        Mat hsvFrame = new Mat();
        Mat mask = new Mat();

        // Convert the frame to HSV color space
        Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_RGBA2BGR);
        Imgproc.cvtColor(hsvFrame, hsvFrame, Imgproc.COLOR_BGR2HSV);

        // Basketball Average Colour = RGB(222,88,0)

        Scalar lowerRange = new Scalar(0,75,150);
        Scalar upperRange = new Scalar(20,255,255);

        // Create a binary mask by thresholding the HSV frame
        Core.inRange(hsvFrame, lowerRange, upperRange, mask);

        return mask;
    }

    public void trackBasketball(Mat frame, Mat ballMask) {

        // Minimum contour tracking area size
        int minContourArea = 2000;

        // Apply operations to clean up the mask
        Imgproc.erode(ballMask, ballMask, new Mat());
        Imgproc.dilate(ballMask, ballMask, new Mat());

        // After pre-processing of mask
        // Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(ballMask, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Filter contour from unlikely contours
        List<MatOfPoint> filteredContours = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            if (Imgproc.contourArea(contour) > minContourArea) {
                filteredContours.add(contour);
            }
        }

        // Apply tracking algorithm to track the basketball
        if (filteredContours.size() > 0) {

            // Smoothing values - modify based on testing
            double smoothA = 0.8;
            double smoothB = 0.2;

            Point center = applyTracking(filteredContours.get(0));

            // Smooth the tracking by averaging previous position
            if (previousCenter != null) {
                center.x = smoothA * center.x + smoothB * previousCenter.x;
                center.y = smoothA * center.y + smoothB * previousCenter.y;
            }

            previousCenter = center;

            // Draw a circle around the basketball
            Imgproc.circle(frame, center, (int) radius[0], new Scalar(0, 255, 0), 4);

            // Calculate the center of the circle
            Point circleCenter = new Point(center.x, center.y);
            // Draw a point in the center
            Imgproc.circle(frame, circleCenter, 5, new Scalar(255, 0, 0), 6);
        } else {
            // If no basketball is detected, reset the previousCenter
            previousCenter = null;
        }
    }

    private Point applyTracking(MatOfPoint contour) {
        // Find the minimum enclosing circle around the contour
        Point center = new Point();
        Imgproc.minEnclosingCircle(new MatOfPoint2f(contour.toArray()), center, radius); // Use the class variable radius
        return center;
    }

    public void processFrame(Mat frame) {
        Mat ballMask = detectBasketball(frame);
        trackBasketball(frame, ballMask);
    }

}


