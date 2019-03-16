package com.sensetime.liveness.motion;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import com.sensetime.liveness.motion.ui.camera.SenseCamera;
import com.sensetime.liveness.motion.ui.camera.SenseCameraPreview;
import com.sensetime.liveness.motion.util.MediaController;
import com.sensetime.sample.common.R;
import com.sensetime.senseid.sdk.liveness.interactive.InteractiveLivenessApi;
import com.sensetime.senseid.sdk.liveness.interactive.MotionComplexity;
import com.sensetime.senseid.sdk.liveness.interactive.OnLivenessListener;
import com.sensetime.senseid.sdk.liveness.interactive.common.type.ResultCode;
import com.sensetime.senseid.sdk.liveness.interactive.common.util.FileUtil;
import com.sensetime.senseid.sdk.liveness.interactive.type.FaceDistance;
import com.sensetime.senseid.sdk.liveness.interactive.type.FaceOcclusion;
import com.sensetime.senseid.sdk.liveness.interactive.type.FaceState;
import com.sensetime.senseid.sdk.liveness.interactive.type.OcclusionState;

public class MotionLivenessActivity extends AbstractCommonMotionLivingActivity {

    //请将账户信息补全，然后删除此行。Fill in your account info below, and delete this line.
    private static final String API_KEY = "5a5e84acb7d64383b364f134d8a1d05e";
    private static final String API_SECRET = "8dd212e851ae4c9c997c52837b7de496";

    private OnLivenessListener mLivenessListener = new OnLivenessListener() {
        private long mLastStatusUpdateTime;

        @Override
        public void onInitialized() {
            startInputData = true;
        }

        @Override
        public void onStatusUpdate(final int faceState, final FaceOcclusion faceOcclusion, final int faceDistance) {
            if (SystemClock.elapsedRealtime() - mLastStatusUpdateTime < 300 && faceState != FaceState.NORMAL) {
                return;
            }

            if (FaceState.OCCLUSION == faceState) {
                StringBuffer occlusionPart = new StringBuffer();
                boolean needComma = false;

                if (faceOcclusion.getBrowOcclusionState() == OcclusionState.OCCLUSION) {
                    occlusionPart.append(getString(R.string.common_covered_brow));
                    needComma = true;
                }

                if (faceOcclusion.getEyeOcclusionState() == OcclusionState.OCCLUSION) {
                    occlusionPart.append(getString(R.string.common_covered_eye));
                    needComma = true;
                }

                if (faceOcclusion.getNoseOcclusionState() == OcclusionState.OCCLUSION) {
                    occlusionPart.append(needComma ? "、" : "");
                    occlusionPart.append(getString(R.string.common_covered_nose));
                    needComma = true;
                }

                if (faceOcclusion.getMouthOcclusionState() == OcclusionState.OCCLUSION) {
                    occlusionPart.append(needComma ? "、" : "");
                    occlusionPart.append(getString(R.string.common_covered_mouth));
                }

                mNoteTextView.setText(getString(R.string.common_face_covered, occlusionPart.toString()));
            } else if (faceDistance == FaceDistance.TOO_CLOSE) {
                mNoteTextView.setText(R.string.common_face_too_close);
            } else if (faceDistance == FaceDistance.TOO_FAR) {
                mNoteTextView.setText(R.string.common_face_too_far);
            } else if (faceState == FaceState.NORMAL) {
                mNoteTextView.setText(R.string.common_detecting);
            } else {
                mNoteTextView.setText(R.string.common_tracking_missed);
            }

            mLastStatusUpdateTime = SystemClock.elapsedRealtime();
        }

        @Override
        public void onError(ResultCode resultCode) {
            startInputData = false;
            setResult(ActivityUtils.convertResultCode(resultCode));
            finish();
        }

        @Override
        public void onDetectOver(ResultCode code, String requestId, String protobufId, byte[] result, List imageData) {
            startInputData = false;
            if (result != null && result.length > 0) {
                FileUtil.saveDataToFile(result, RESULT_PATH + FILE_NAME);
            }

            List<byte[]> imageResult = (List<byte[]>) imageData;
            switch (code) {
                case OK:
                    if (mTimerViewContoller != null) {
                        mTimerViewContoller.setCallBack(null);
                        mTimerViewContoller.hide();
                        mTimerViewContoller = null;
                    }
                    saveImages(imageResult, RESULT_PATH);
//                    setResult(RESULT_OK);
                    Intent intent = new Intent();
                    Log.e("--1111-", "upLoadFace: "+protobufId);
                    intent.putExtra("livenessId",protobufId);
                    setResult(RESULT_OK,intent);
                    break;
                default:
                    saveImages(imageResult, RESULT_PATH);
                    setResult(ActivityUtils.convertResultCode(code));
                    break;
            }
            finish();
        }

        @Override
        public void onOnlineCheckBegin() {
            startInputData = false;
            mLoadingView.setVisibility(View.VISIBLE);
            mDetectLayout.setVisibility(View.INVISIBLE);
            if (mTimerViewContoller != null) {
                mTimerViewContoller.setCallBack(null);
                mTimerViewContoller.hide();
            }
            if (mIsVoiceOn) {
                MediaController.getInstance().release();
            }
        }

        @Override
        public void onAligned() {
            background.setImageDrawable(getResources().getDrawable(R.drawable.common_background_success));
            startInputData = false;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    startInputData = true;
                    background.setImageDrawable(getResources().getDrawable(R.drawable.common_background));
                }
            }, 500);

            mNoteTextView.setVisibility(View.GONE);
            mDetectLayout.setVisibility(View.VISIBLE);

            if (mCurrentMotionIndex > -1) {
                ((ImageView) mStepsView.getChildAt(mCurrentMotionIndex)).setImageResource(
                        STEP_PIC_SELECTED[mCurrentMotionIndex]);
            }

            InteractiveLivenessApi.start(mSequences, mDifficulty);
        }

        @Override
        public void onMotionSet(final int index, int motion) {
            mCurrentMotionIndex = index;
            mAnimationView.setCurrentItem(index, true);

            if (index > 0) {
                ((ImageView) mStepsView.getChildAt(index - 1)).setImageResource(STEP_PIC_NORMAL[index - 1]);
            }
            ((ImageView) mStepsView.getChildAt(index)).setImageResource(STEP_PIC_SELECTED[index]);

            if (mTimerViewContoller != null) {
                mTimerViewContoller.start(true);
            }

            if (mIsVoiceOn) {
                MediaController.getInstance().playNotice(MotionLivenessActivity.this, mSequences[mCurrentMotionIndex]);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileUtil.deleteResultDir(RESULT_PATH);
        if (!checkPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            setResult(ActivityUtils.RESULT_CODE_NO_PERMISSIONS);
            finish();
            return;
        }

        setContentView(R.layout.common_activity_liveness_motion);
        background = (ImageView) findViewById(R.id.background);
        background.setImageDrawable(getResources().getDrawable(R.drawable.common_background));
        // init setting values.
        mIsVoiceOn = getIntent().getBooleanExtra(EXTRA_VOICE, true);
        mDifficulty = getIntent().getIntExtra(EXTRA_DIFFICULTY, MotionComplexity.NORMAL);
        int[] sequences = getIntent().getIntArrayExtra(EXTRA_SEQUENCES);
        if (sequences != null && sequences.length > 0) {
            mSequences = sequences;
        }

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        View voiceView = findViewById(R.id.btn_voice);
        voiceView.setBackgroundResource(mIsVoiceOn ? R.drawable.common_ic_voice : R.drawable.common_ic_mute);
        voiceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsVoiceOn = !mIsVoiceOn;
                v.setBackgroundResource(mIsVoiceOn ? R.drawable.common_ic_voice : R.drawable.common_ic_mute);
                if (mCurrentMotionIndex > -1 && mSequences != null && mCurrentMotionIndex < mSequences.length) {
                    if (mIsVoiceOn) {
                        MediaController.getInstance()
                                .playNotice(MotionLivenessActivity.this, mSequences[mCurrentMotionIndex]);
                    } else {
                        MediaController.getInstance().release();
                    }
                }
            }
        });

        mNoteTextView = (TextView) findViewById(R.id.txt_note);

        mLoadingView = findViewById(R.id.pb_loading);
        mCameraPreviewView = (SenseCameraPreview) findViewById(R.id.camera_preview);

        mDetectLayout = findViewById(R.id.layout_detect);
        initDetectLayout();

        File dir = new File(FILES_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileUtil.copyAssetsToFile(MotionLivenessActivity.this, MODEL_FILE_NAME, FILES_PATH + MODEL_FILE_NAME);
        FileUtil.copyAssetsToFile(MotionLivenessActivity.this, LICENSE_FILE_NAME, FILES_PATH + LICENSE_FILE_NAME);

        InteractiveLivenessApi.init(MotionLivenessActivity.this, API_KEY, API_SECRET, FILES_PATH + LICENSE_FILE_NAME,
                FILES_PATH + MODEL_FILE_NAME, mLivenessListener);
        InteractiveLivenessApi.start(null, mDifficulty);

        mSenseCamera = new SenseCamera.Builder(this).setFacing(SenseCamera.CAMERA_FACING_FRONT)
                .setRequestedPreviewSize(640, 480)
                .build();
        startInputData = false;
    }
}