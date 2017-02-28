package com.yezi.testcge;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import org.wysaid.camera.CameraInstance;
import org.wysaid.myUtils.ImageUtil;
import org.wysaid.myUtils.MsgUtil;
import org.wysaid.view.CameraRecordGLSurfaceView;

public class FullscreenActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "TEST_CGE";
    private CameraRecordGLSurfaceView mCameraRecordGLSurfaceView;
    private Button mBtnPicture;
    private String mCameraConfig;
    private Button mBtnLight;
    private Button mBtnSwitch;
    private int mScreenWidth;
    private int mScreenHeight;
    private boolean mIsRecording = false;
    private Button mBtnRecord;
    private String mCurrentCameraFlash = Camera.Parameters.FLASH_MODE_OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen);

        mCameraRecordGLSurfaceView = (CameraRecordGLSurfaceView) findViewById(R.id.camera_record_view);
        mBtnPicture = (Button) findViewById(R.id.btn_picture);
        mBtnLight = (Button) findViewById(R.id.btn_light);
        mBtnSwitch = (Button) findViewById(R.id.btn_switch);
        mBtnRecord = (Button) findViewById(R.id.btn_record);

        mBtnLight.setOnClickListener(this);
        mBtnSwitch.setOnClickListener(this);
        mBtnPicture.setOnClickListener(this);
        mBtnRecord.setOnClickListener(this);

        initConfig();
        initCameraGLSurfaceView();
    }

    private void initConfig() {
        mScreenWidth = ScreenUtil.getScreenWidth(this);
        mScreenHeight = ScreenUtil.getScreenHeight(this);
//        mScreenWidth = 720;
//        mScreenHeight = 1080;
        mCameraConfig = "@beautify face 1 " + mScreenWidth + " " + mScreenHeight
                + " @beautify bilateral 10 4 1";
//                + "@style haze -0.5 -0.5 1 1 1 @curve RGB(0, 0)(94, 20)(160, 168)(255, 255) @curve R(0, 0)(129, 119)(255, 255)B(0, 0)(135, 151)(255, 255)RGB(0, 0)(146, 116)(255, 255)";
        //10 4 1
        /*
           bilateral (双边滤波): 后接 三个参数 模糊半径(blurScale) 范围[-100, 100],
           色彩容差(distanceFactor) 范围[1, 20] 和 重复次数(repeat times) 范围 >= 1 其中 重复次数为可选参数， 如果不填， 则默认为 1

           face (美颜): 后接一个或三个参数 美颜强度(intensity) 范围 (0, 1], 为0无效果, 相片宽(width), 相片高(height)
           其中 相片宽高不填的情况下默认 720x1280 例: "@beautify face 1 720 1280"*/
    }

    private void initCameraGLSurfaceView() {
        mCameraRecordGLSurfaceView.presetRecordingSize(mScreenWidth, mScreenHeight);
        mCameraRecordGLSurfaceView.setZOrderOnTop(false);
        mCameraRecordGLSurfaceView.setZOrderMediaOverlay(true);
        mCameraRecordGLSurfaceView.presetCameraForward(false);
        mCameraRecordGLSurfaceView.setPictureSize(mScreenWidth, mScreenHeight, true);

        mCameraRecordGLSurfaceView.setOnCreateCallback(new CameraRecordGLSurfaceView.OnCreateCallback() {
            @Override
            public void createOver(boolean success) {
                if (success) {
                    mCameraRecordGLSurfaceView.setFilterWithConfig(mCameraConfig);
                    mCameraRecordGLSurfaceView.setFilterIntensity(1.3f);
                    Log.i(LOG_TAG, "view create OK");
                } else {
                    Log.e(LOG_TAG, "view create failed!");
                }
            }
        });
        mCameraRecordGLSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i(LOG_TAG, String.format("Tap to focus: %g, %g", event.getX(), event.getY()));
                        final float focusX = event.getX() / mCameraRecordGLSurfaceView.getWidth();
                        final float focusY = event.getY() / mCameraRecordGLSurfaceView.getHeight();

                        mCameraRecordGLSurfaceView.focusAtPoint(focusX, focusY, new Camera.AutoFocusCallback() {
                            @Override
                            public void onAutoFocus(boolean success, Camera camera) {
                                if (success) {
                                    Log.e(LOG_TAG, String.format("Focus OK, pos: %g, %g", focusX, focusY));
                                } else {
                                    Log.e(LOG_TAG, String.format("Focus failed, pos: %g, %g", focusX, focusY));
                                    CameraInstance.getInstance().setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                                }
                            }
                        });
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        CameraInstance.getInstance().stopCamera();
        mCameraRecordGLSurfaceView.release(null);
        mCameraRecordGLSurfaceView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCameraRecordGLSurfaceView.onResume();
    }

    private void showText(final String s) {
        mCameraRecordGLSurfaceView.post(new Runnable() {
            @Override
            public void run() {
                MsgUtil.toastMsg(FullscreenActivity.this, s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_light:
                toggleFlash();
                break;
            case R.id.btn_record:
                takeVideo();
                break;
            case R.id.btn_switch:
                switchCamera();
                break;
            case R.id.btn_picture:
                takePicture();
                break;
            default:
        }
    }

    private void toggleFlash() {
        mCurrentCameraFlash = Camera.Parameters.FLASH_MODE_TORCH.equals(mCurrentCameraFlash)
                ? Camera.Parameters.FLASH_MODE_OFF : Camera.Parameters.FLASH_MODE_TORCH;
        mCameraRecordGLSurfaceView.setFlashLightMode(mCurrentCameraFlash);
    }

    private void takeVideo() {
        mIsRecording = !mIsRecording;
        if (mIsRecording) {
            mBtnRecord.setText("Recording");
            Log.i(LOG_TAG, "Start recording...");
            mCameraRecordGLSurfaceView.setClearColor(1.0f, 0.0f, 0.0f, 0.3f);
            String recordFilename = ImageUtil.getPath() + "/rec_" + System.currentTimeMillis() + ".mp4";
            mCameraRecordGLSurfaceView.startRecording(recordFilename, new CameraRecordGLSurfaceView.StartRecordingCallback() {
                @Override
                public void startRecordingOver(boolean success) {
                    if (success) {
                        showText("Start recording OK");
                    } else {
                        showText("Start recording failed");
                    }
                }
            });
        } else {
            showText("End recording OK");
            mBtnRecord.setText("Recorded");
            Log.i(LOG_TAG, "End recording...");
            mCameraRecordGLSurfaceView.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            mCameraRecordGLSurfaceView.endRecording(new CameraRecordGLSurfaceView.EndRecordingCallback() {
                @Override
                public void endRecordingOK() {
                    Log.i(LOG_TAG, "End recording OK");
                }
            });
        }
    }

    private void switchCamera() {
        mCameraRecordGLSurfaceView.switchCamera();
    }

    private void takePicture() {
        mCameraRecordGLSurfaceView.takePicture(new CameraRecordGLSurfaceView.TakePictureCallback() {
            @Override
            public void takePictureOK(Bitmap bmp) {
                if (bmp != null) {
                    ImageUtil.saveBitmap(bmp);
                    bmp.recycle();
                    showText("Take picture success!");
                } else
                    showText("Take picture failed!");
            }
        }, null, mCameraConfig, 1.0f, true);
    }
}
