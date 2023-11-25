//COMP 3450: Saifullah Chandio T00657965, Jacob Harris T00657013, Noah Dobie T00661661
package com.example.basketballshottracker.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.basketballshottracker.R;
import com.example.basketballshottracker.ui.home.Tracking.HoopLocation;
import com.example.basketballshottracker.ui.home.Tracking.ObjectDetection;


import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class HomeFragment extends Fragment {
    private CameraBridgeViewBase cameraView;
    private ObjectDetection objectDetection;
    private HoopLocation hoopLocation;
    private boolean sessionOn = false;
    private int frameWidth, frameHeight;

    private String TAG = "TOUCH";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cameraView = view.findViewById(R.id.camera_view);

        // Set up the start button click listener
        Button startButton = view.findViewById(R.id.btnStart);
        // Other home elements (start screen)
        TextView instructionsTextView = view.findViewById(R.id.textView_Instructions);
        TextView titleTextView = view.findViewById(R.id.textView_Title);
        View styleLine = view.findViewById(R.id.styleLine);
        // Elements for when user is shooting
        Button stopButton = view.findViewById(R.id.btnStop);
        TextView shotsMade = view.findViewById(R.id.shotsMade_home);
        shotsMade.setText("3");
        TextView shotsMissed = view.findViewById(R.id.shotsMissed_home);
        shotsMissed.setText("5");
        TextView madeLabel = view.findViewById(R.id.shotsMadeLabel_home);
        TextView missedLabel = view.findViewById(R.id.shotsMissedLabel_home);
        View stopSection = view.findViewById(R.id.view_shootingActive);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the visibility of the cameraView
                cameraView.setVisibility(SurfaceView.VISIBLE);
                // Make active elements visible
                stopButton.setVisibility(View.VISIBLE);
                shotsMade.setVisibility(View.VISIBLE);
                shotsMissed.setVisibility(View.VISIBLE);
                madeLabel.setVisibility(View.VISIBLE);
                missedLabel.setVisibility(View.VISIBLE);
                stopSection.setVisibility(View.VISIBLE);
                // Make other elements invisible
                startButton.setVisibility(View.GONE);
                instructionsTextView.setVisibility(View.GONE);
                titleTextView.setVisibility(View.GONE);
                styleLine.setVisibility(View.GONE);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the visibility of the cameraView
                cameraView.setVisibility(SurfaceView.GONE);
                // Make active elements visible
                stopButton.setVisibility(View.GONE);
                shotsMade.setVisibility(View.GONE);
                shotsMissed.setVisibility(View.GONE);
                madeLabel.setVisibility(View.GONE);
                missedLabel.setVisibility(View.GONE);
                stopSection.setVisibility(View.GONE);
                // Make other elements invisible
                startButton.setVisibility(View.VISIBLE);
                instructionsTextView.setVisibility(View.VISIBLE);
                titleTextView.setVisibility(View.VISIBLE);
                styleLine.setVisibility(View.VISIBLE);
            }
        });

        objectDetection = new ObjectDetection();

        cameraView.setCameraIndex(1);
        cameraView.setCameraPermissionGranted();
        cameraView.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
            @Override
            public void onCameraViewStarted(int width, int height) {
                hoopLocation = new HoopLocation(cameraView);
            }

            @Override
            public void onCameraViewStopped() {
            }

            @Override
            public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
                Mat frame = inputFrame.rgba();
                objectDetection.processFrame(frame);

                if (hoopLocation.isHoopSet()) {
                    Imgproc.rectangle(frame, hoopLocation.getHoopRect().tl(), hoopLocation.getHoopRect().br(), new Scalar(0, 255, 0), 4);
                }

                // Flips camera for mobile use
//                Core.rotate(frame, frame, Core.ROTATE_90_COUNTERCLOCKWISE);
//                Core.flip(frame, frame, 1);
                return frame;
            }
        });

        getCameraPermission();

        checkOpenCVLoad(); // Check that OpenCV has initialized

        return view;
    }

    // Obtain Camera Permissions from user (function)
    public void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        }
    }

    //Request Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            getCameraPermission();
        }
    }

    private void checkOpenCVLoad() {
        if(OpenCVLoader.initDebug()){
            Log.d("VALIDATION", "OPENCV LOADED!");
            cameraView.enableView();
        } else{
            Log.d("VALIDATION", "OPENCV NOT LOADED!");
        }
    }
}