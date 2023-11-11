package com.example.basketballshottracker.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.basketballshottracker.R;
import com.example.basketballshottracker.ui.home.Tracking.HoopLocation;
import com.example.basketballshottracker.ui.home.Tracking.ObjectDetection;


import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class HomeFragment extends Fragment {

    private CameraBridgeViewBase cameraView;
    private ObjectDetection objectDetection;
    private HoopLocation hoopLocation;
    private int frameWidth, frameHeight;

    private String TAG = "TOUCH";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cameraView = view.findViewById(R.id.camera_view);

        objectDetection = new ObjectDetection();

        cameraView.setCameraIndex(1);
        cameraView.setVisibility(SurfaceView.VISIBLE);
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