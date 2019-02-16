package com.sensetime.liveness.motion;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import com.sensetime.liveness.motion.ui.camera.SenseCamera;
import com.sensetime.liveness.motion.ui.camera.SenseCameraPreview;
import com.sensetime.liveness.motion.util.MediaController;
import com.sensetime.liveness.motion.view.CircleTimeView;
import com.sensetime.liveness.motion.view.FixedSpeedScroller;
import com.sensetime.liveness.motion.view.MotionPagerAdapter;
import com.sensetime.liveness.motion.view.TimeViewContoller;
import com.sensetime.sample.common.R;
import com.sensetime.senseid.sdk.liveness.interactive.InteractiveLivenessApi;
import com.sensetime.senseid.sdk.liveness.interactive.MotionComplexity;
import com.sensetime.senseid.sdk.liveness.interactive.NativeMotion;
import com.sensetime.senseid.sdk.liveness.interactive.common.type.PixelFormat;
import com.sensetime.senseid.sdk.liveness.interactive.common.util.FileUtil;
import com.sensetime.senseid.sdk.liveness.interactive.type.BoundInfo;

abstract class AbstractCommonMotionLivingActivity extends Activity implements Camera.PreviewCallback {

    public static final String EXTRA_DIFFICULTY = "extra_difficulty";
    public static final String EXTRA_VOICE = "extra_voice";
    public static final String EXTRA_SEQUENCES = "extra_sequences";

    public static final String FILES_PATH = Environment.getExternalStorageDirectory().getPath() + "/sensetime/";
    public static final String RESULT_PATH = FILES_PATH + "interactive_liveness/";
    protected static final String FILE_NAME = "motionLivenessResult";
    protected static final String MODEL_FILE_NAME = "SenseID_Motion_Liveness.model";
    protected static final String LICENSE_FILE_NAME = "SenseID_Liveness_Interactive.lic";

    protected static final int[] STEP_PIC_NORMAL = {
            R.drawable.common_step_1_normal, R.drawable.common_step_2_normal, R.drawable.common_step_3_normal,
            R.drawable.common_step_4_normal, R.drawable.common_step_5_normal, R.drawable.common_step_6_normal,
            R.drawable.common_step_7_normal, R.drawable.common_step_8_normal, R.drawable.common_step_9_normal,
            R.drawable.common_step_10_normal
    };
    protected static final int[] STEP_PIC_SELECTED = {
            R.drawable.common_step_1_selected, R.drawable.common_step_2_selected, R.drawable.common_step_3_selected,
            R.drawable.common_step_4_selected, R.drawable.common_step_5_selected, R.drawable.common_step_6_selected,
            R.drawable.common_step_7_selected, R.drawable.common_step_8_selected, R.drawable.common_step_9_selected,
            R.drawable.common_step_10_selected
    };

    protected boolean mIsVoiceOn = true;
    protected int mDifficulty = MotionComplexity.NORMAL;
    protected int[] mSequences = new int[] {
            NativeMotion.CV_LIVENESS_BLINK, NativeMotion.CV_LIVENESS_MOUTH, NativeMotion.CV_LIVENESS_HEADNOD,
            NativeMotion.CV_LIVENESS_HEADYAW
    };
    protected int mCurrentMotionIndex = -1;

    protected TextView mNoteTextView = null;
    protected View mDetectLayout = null;
    protected View mLoadingView = null;
    protected ViewPager mAnimationView = null;
    protected ViewGroup mStepsView = null;

    protected SenseCameraPreview mCameraPreviewView = null;
    protected SenseCamera mSenseCamera = null;

    protected TimeViewContoller mTimerViewContoller = null;
    protected ImageView background = null;
    protected boolean startInputData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            this.mCameraPreviewView.start(this.mSenseCamera);
            this.mSenseCamera.setOnPreviewFrameCallback(this);
        } catch (Exception e) {
            this.setResult(ActivityUtils.RESULT_CODE_CAMERA_ERROR);
            this.finish();
        }
    }

    @Override
    protected void onPause() {
        startInputData = false;
        mLoadingView.setVisibility(View.GONE);
        InteractiveLivenessApi.cancel();
        if (mTimerViewContoller != null) {
            mTimerViewContoller.stop();
            mTimerViewContoller.setCallBack(null);
            mTimerViewContoller = null;
        }
        MediaController.getInstance().release();

        this.mCameraPreviewView.stop();
        this.mCameraPreviewView.release();

        setResult(RESULT_CANCELED);
        if (!isFinishing()) {
            finish();
        }

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        int parWidth = mCameraPreviewView.getWidth();
        int parHeight = mCameraPreviewView.getHeight();
        ViewParent parent = mCameraPreviewView.getParent();

        if (parent != null) {
            parWidth = ((View) parent).getWidth();
            parHeight = ((View) parent).getHeight();
        }

        if (startInputData) {

            Rect containerRect = this.mCameraPreviewView.convertViewRectToPicture(new Rect(0, 0, parWidth, parHeight));
            BoundInfo boundInfo = this.mCameraPreviewView.convertBoundInfoToPicture(
                    new BoundInfo(parWidth / 2, parHeight / 2, parWidth / 3));

            InteractiveLivenessApi.inputData(data, PixelFormat.NV21, this.mSenseCamera.getPreviewSize(), containerRect,
                    true, mSenseCamera.getRotationDegrees(), boundInfo);
        }
    }

    protected void initDetectLayout() {
        // init step view.
        mStepsView = (ViewGroup) mDetectLayout.findViewById(R.id.layout_steps);
        for (int i = 0; i < mSequences.length; i++) {
            ImageView imageView =
                    (ImageView) getLayoutInflater().inflate(R.layout.common_item_motion_step, mStepsView, false);
            imageView.setImageResource(STEP_PIC_NORMAL[i]);
            mStepsView.addView(imageView);
        }

        // init anim view.
        mAnimationView = (ViewPager) findViewById(R.id.pager_action);
        mAnimationView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });// return true prevent touch event
        mAnimationView.setAdapter(new MotionPagerAdapter(mSequences));
        try {
            // FixedSpeedScroller control change time
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            FixedSpeedScroller fixedSpeedScroller = new FixedSpeedScroller(mAnimationView.getContext());
            scroller.set(mAnimationView, fixedSpeedScroller);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        // init timer view.
        CircleTimeView timerView = (CircleTimeView) findViewById(R.id.time_view);
        mTimerViewContoller = new TimeViewContoller(timerView);
        mTimerViewContoller.setCallBack(new TimeViewContoller.CallBack() {
            @Override
            public void onTimeEnd() {
                mTimerViewContoller.stop();
            }
        });
    }

    protected void saveImages(List<byte[]> imageResult, String folderPath) {
        if (imageResult != null && !imageResult.isEmpty()) {
            File imagesFolder = new File(folderPath);

            if (!imagesFolder.exists()) {
                imagesFolder.mkdirs();
            }

            for (int index = 0; index < imageResult.size(); index++) {
                FileUtil.saveDataToFile(imageResult.get(index), folderPath + index + ".jpg");
            }
        }
    }

    protected boolean checkPermission(String... permissions) {
        if (permissions == null || permissions.length < 1) {
            return true;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
