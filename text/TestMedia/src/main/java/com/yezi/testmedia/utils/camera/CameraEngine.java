package com.yezi.testmedia.utils.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;

import java.io.IOException;

public class CameraEngine {
    private static Camera sCamera = null;
    private static int sCameraID = 0;
    private static SurfaceTexture sSurfaceTexture;

    public static Camera getCamera() {
        return sCamera;
    }

    public static boolean openCamera() {
        if (sCamera == null) {
            try {
                sCamera = Camera.open(sCameraID);
                setDefaultParameters();
                return true;
            } catch (RuntimeException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean openCamera(int id) {
        if (sCamera == null) {
            try {
                sCamera = Camera.open(id);
                sCameraID = id;
                setDefaultParameters();
                return true;
            } catch (RuntimeException e) {
                return false;
            }
        }
        return false;
    }

    public static void releaseCamera() {
        if (sCamera != null) {
            sCamera.setPreviewCallback(null);
            sCamera.stopPreview();
            sCamera.release();
            sCamera = null;
        }
    }

    public void resumeCamera() {
        openCamera();
    }

    public void setParameters(Parameters parameters) {
        sCamera.setParameters(parameters);
    }

    public Parameters getParameters() {
        if (sCamera != null)
            sCamera.getParameters();
        return null;
    }

    public static void switchCamera() {
        releaseCamera();
        sCameraID = sCameraID == 0 ? 1 : 0;
        openCamera(sCameraID);
        startPreview(sSurfaceTexture);
    }

    private static void setDefaultParameters() {
        Parameters parameters = sCamera.getParameters();
        if (parameters.getSupportedFocusModes().contains(
                Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        Size previewSize = CameraUtils.getLargePreviewSize(sCamera);
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        Size pictureSize = CameraUtils.getLargePictureSize(sCamera);
        parameters.setPictureSize(pictureSize.width, pictureSize.height);
        sCamera.setParameters(parameters);
    }

    public static boolean isFrontCamera() {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(sCameraID, info);
        return info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT;
    }

    public static Size getPreviewSize() {
        return sCamera.getParameters().getPreviewSize();
    }

    private static Size getPictureSize() {
        return sCamera.getParameters().getPictureSize();
    }

    public static void startPreview(SurfaceTexture surfaceTexture) {
        if (sCamera != null)
            try {
                sCamera.setPreviewTexture(surfaceTexture);
                CameraEngine.sSurfaceTexture = surfaceTexture;
                sCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void startPreview() {
        if (sCamera != null)
            sCamera.startPreview();
    }

    public static void stopPreview() {
        sCamera.stopPreview();
    }

    public static void takePicture(Camera.ShutterCallback shutterCallback, Camera.PictureCallback rawCallback,
                                   Camera.PictureCallback jpegCallback) {
        sCamera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    public static CameraInfo getCameraInfo() {
        CameraInfo info = new CameraInfo();
        Size size = getPreviewSize();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(sCameraID, cameraInfo);
        info.previewWidth = size.width;
        info.previewHeight = size.height;
        info.orientation = cameraInfo.orientation;
        info.isFront = sCameraID == 1 ? true : false;
        size = getPictureSize();
        info.pictureWidth = size.width;
        info.pictureHeight = size.height;
        return info;
    }
}