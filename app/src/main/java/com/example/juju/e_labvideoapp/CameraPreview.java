package com.example.juju.e_labvideoapp;


import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.YuvImage;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import java.io.ByteArrayOutputStream;
import java.io.*;
import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    public String timeStampFile;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        // Install a SurfaceHolder.Callback so we get notified when the underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            // create the surface and start camera preview
            if (mCamera == null) {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            }
        } catch (IOException e) {
            Log.d(VIEW_LOG_TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void refreshCamera(Camera camera) {
        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }
        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        setCamera(camera);
        try {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewFrameRate(24);
            //List<int[]> tmp = parameters.getSupportedPreviewFpsRange();
            //mCamera.getParameters().flatten();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            //parameters.setPreviewSize(720, 480);
            //parameters.setPreviewFpsRange(24000,24000);
            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(mHolder);
            //List<Camera.Size> tmp = parameters.getSupportedPreviewSizes();
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    // TODO Auto-generated method stub
                    Size size = camera.getParameters().getPreviewSize();
                    /*
                    YuvImage yuvImage = new YuvImage(data, ImageFormat.NV21,
                            size.width, size.height, null);

                    String timeStamp = String.valueOf(System.nanoTime());
                    try {
                        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getPath()+"/elab/"+"checkPreviewRate/png");
                        wallpaperDirectory.mkdirs();
                        FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/elab/" + "checkPreviewRate/png" + "/" + timeStamp + ".jpg");
                        yuvImage.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, fos);
                        yuvImage.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, fos);
                        yuvImage.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, fos);
                        yuvImage.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, fos);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("hxiong","SaveFileError");
                    }

                    try{
                        FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/elab/" + "checkPreviewRate/png" + "/" + timeStamp + ".txt");
                        fos.write(data);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("hxiong","SaveFileError");
                    }
                    */

                    /*
                    YuvImage yuvImage = new YuvImage(data, ImageFormat.NV21,
                            size.width, size.height, null);
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    yuvImage.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, os);
                    byte[] jpegByteArray = os.toByteArray();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(jpegByteArray, 0, jpegByteArray.length);
                    String timeStamp = String.valueOf(System.nanoTime());
                    try {
                        //FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/elab/" + timeStampFile + "/" + timeStamp + ".png");
                        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getPath()+"/elab/"+"checkPreviewRate");
                        wallpaperDirectory.mkdirs();
                        FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/elab/" + "checkPreviewRate" + "/" + timeStamp + ".png");
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("hxiong","SaveFileError");
                    }
                    */
                    try {
                        String timeStamp = String.valueOf(System.nanoTime());
                        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getPath() + "/elab/" + "checkPreviewRate");
                        wallpaperDirectory.mkdirs();
                        File wallpaperDirectory1 = new File(Environment.getExternalStorageDirectory().getPath() + "/elab/" + "checkPreviewRate/png");
                        wallpaperDirectory1.mkdirs();
                        FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/elab/" + "checkPreviewRate/png" + "/" + timeStamp + ".txt");
                        fos.write(data);
                        fos.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                        Log.e("hxiong", "SaveFileError");
                    }
                }
            });
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        refreshCamera(mCamera);
    }

    public void setCamera(Camera camera) {
        //method to set a camera instance
        mCamera = camera;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        // mCamera.release();

    }
}