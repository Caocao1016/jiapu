package com.sensetime.sample.common.idcard;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.IOException;

import com.sensetime.sample.common.idcard.ui.camera.SenseCamera;
import com.sensetime.sample.common.idcard.ui.camera.SenseCameraPreview;
import com.sensetime.senseid.sdk.ocr.common.util.FileUtil;
import com.sensetime.senseid.sdk.ocr.id.IdCardApi;
import com.sensetime.senseid.sdk.ocr.id.IdCardSide;
import com.sensetime.senseid.sdk.ocr.id.KeyRequires;
import com.sensetime.senseid.sdk.ocr.id.ScanMode;
import com.sensetime.senseid.sdk.ocr.id.ScanSide;

/**
 * Created on 2016/11/23 15:55.
 *
 * @author Han Xu
 */
abstract class AbstractIdCardActivity extends Activity
        implements Camera.PreviewCallback, SenseCameraPreview.StartListener {

    public static final String EXTRA_SCAN_MODE = "extra_scan_mode";
    public static final String EXTRA_SCAN_SIDE = "extra_scan_side";
    public static final String EXTRA_KEY_REQUIRE = "extra_key_require";

    public static final String EXTRA_CARD_SIDE = "extra_card_side";

    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_SEX = "extra_sex";
    public static final String EXTRA_NATION = "extra_nation";
    public static final String EXTRA_BIRTHDAY = "extra_birthday";
    public static final String EXTRA_ADDRESS = "extra_address";
    public static final String EXTRA_NUMBER = "extra_number";
    public static final String EXTRA_IS_ONLY_NAME = "extra_is_only_name";
    public static final String EXTRA_NAME_RECT = "extra_name_rect";
    public static final String EXTRA_NUMBER_RECT = "extra_number_rect";
    public static final String EXTRA_PHOTO_RECT = "extra_photo_rect";
    public static final String EXTRA_FRONT_RESULT_IMAGE = "extra_front_result_image";
    public static final String EXTRA_FRONT_IMAGE_SOURCE = "extra_front_image_source";
    public static final String EXTRA_BACK_IMAGE_SOURCE = "extra_back_image_source";

    public static final String EXTRA_AUTHROITY = "extra_authority";
    public static final String EXTRA_TIMELIMIT = "extra_timelimit";
    public static final String EXTRA_BACK_RESULT_IMAGE = "extra_back_result_image";

    protected static final String FILES_PATH = Environment.getExternalStorageDirectory().getPath() + "/sensetime/";
    protected static final String MODEL_FILE_NAME = "SenseID_Ocr_Idcard.model";
    protected static final String LICENSE_FILE_NAME = "SenseID_OCR.lic";

    protected static final int DEFAULT_PREVIEW_WIDTH = 1280;
    protected static final int DEFAULT_PREVIEW_HEIGHT = 960;

    protected SenseCameraPreview mCameraPreview;

    protected SenseCamera mCamera;

    @ScanMode
    protected int mScanMode = ScanMode.SINGLE;
    @ScanSide
    protected int mScanSide = ScanSide.FRONT;
    protected int mKeyRequires = KeyRequires.ALL;

    protected boolean mOnlyNameNumber = false;

    protected View mLoadingView = null;

    protected OverlayView mOverlayView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            setResult(ActivityUtils.RESULT_CODE_NO_PERMISSIONS);
            finish();
            return;
        }

        setContentView(R.layout.activity_idcard);

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mScanMode = getIntent().getIntExtra(EXTRA_SCAN_MODE, ScanMode.SINGLE);
        mScanSide = getIntent().getIntExtra(EXTRA_SCAN_SIDE, IdCardSide.FRONT);
        mKeyRequires = getIntent().getIntExtra(EXTRA_KEY_REQUIRE, KeyRequires.ALL);
        mOnlyNameNumber = getIntent().getBooleanExtra(EXTRA_IS_ONLY_NAME, false);
        mOverlayView = ((OverlayView) findViewById(R.id.overlay));
        mOverlayView.setText(mScanSide == ScanSide.AUTO ? R.string.scan_tip_auto
                : (mScanSide == ScanSide.FRONT ? R.string.scan_tip_front : R.string.scan_tip_back), Color.WHITE);
        mLoadingView = findViewById(R.id.pb_loading);

        this.mCameraPreview = (SenseCameraPreview) this.findViewById(R.id.camera_preview);
        this.mCameraPreview.setStartListener(this);
        this.mCamera =
                new SenseCamera.Builder(this).setRequestedPreviewSize(DEFAULT_PREVIEW_WIDTH, DEFAULT_PREVIEW_HEIGHT)
                        .build();

        File dir = new File(FILES_PATH);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileUtil.copyAssetsToFile(getApplicationContext(), MODEL_FILE_NAME, FILES_PATH + MODEL_FILE_NAME);
        FileUtil.copyAssetsToFile(getApplicationContext(), LICENSE_FILE_NAME, FILES_PATH + LICENSE_FILE_NAME);
    }

    protected boolean checkPermission(String... permissions) {
        if (permissions == null || permissions.length < 1) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            this.mCameraPreview.start(this.mCamera);
            this.mCamera.setOnPreviewFrameCallback(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        IdCardApi.cancel();

        this.mCameraPreview.stop();
        this.mCameraPreview.release();

        mLoadingView.setVisibility(View.GONE);

        setResult(RESULT_CANCELED);

        if (!isFinishing()) {
            finish();
        }
    }

    @Override
    public void onFail() {
        setResult(ActivityUtils.RESULT_CODE_CAMERA_ERROR);
        if (!isFinishing()) {
            finish();
        }
    }
}