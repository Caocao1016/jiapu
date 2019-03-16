package com.sensetime.sample.common.idcard;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import com.sensetime.senseid.sdk.ocr.common.type.ResultCode;
import com.sensetime.senseid.sdk.ocr.common.util.ImageUtil;
import com.sensetime.senseid.sdk.ocr.id.IdCardApi;
import com.sensetime.senseid.sdk.ocr.id.IdCardInfo;
import com.sensetime.senseid.sdk.ocr.id.IdCardSide;
import com.sensetime.senseid.sdk.ocr.id.OnIdCardScanListener;

/**
 * Created on 2016/11/23 15:55.
 *
 * @author Han Xu
 */
public class IdCardActivity extends AbstractIdCardActivity {

    //Set your API Key & Secret here.
    private static final String API_KEY = "5a5e84acb7d64383b364f134d8a1d05e";
    private static final String API_SECRET = "8dd212e851ae4c9c997c52837b7de496";


    private OnIdCardScanListener mListener = new OnIdCardScanListener() {
        @Override
        public void onError(final String requestId, ResultCode resultCode) {
            setResult(ActivityUtils.convertResultCode(resultCode));
            finish();
        }

        @Override
        public void onSuccess(final String requestId, IdCardInfo idCardInfo) {
            if (idCardInfo != null) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_CARD_SIDE, idCardInfo.getSide());
                if (idCardInfo.getSide() == IdCardSide.BACK || idCardInfo.getSide() == IdCardSide.BOTH) {
                    intent.putExtra(EXTRA_AUTHROITY, idCardInfo.getAuthority());
                    intent.putExtra(EXTRA_TIMELIMIT, idCardInfo.getTimeLimit());
                    intent.putExtra(EXTRA_BACK_IMAGE_SOURCE, idCardInfo.getBackImageType().name());
                    if (idCardInfo.getBackImage() != null) {
                        String backImagePath = Environment.getExternalStorageDirectory() + "/sensetime/idcard/back.jpg";
                        ImageUtil.saveBitmapToFile(idCardInfo.getBackImage(), backImagePath);
                        intent.putExtra(EXTRA_BACK_RESULT_IMAGE, backImagePath);
                    }
                }
                if (idCardInfo.getSide() == IdCardSide.FRONT || idCardInfo.getSide() == IdCardSide.BOTH) {
                    intent.putExtra(EXTRA_NAME, idCardInfo.getName());
                    intent.putExtra(EXTRA_SEX, idCardInfo.getGender());
                    intent.putExtra(EXTRA_NATION, idCardInfo.getNation());

                    if (!TextUtils.isEmpty(idCardInfo.getBirthdayYear()) && !TextUtils.isEmpty(
                            idCardInfo.getBirthdayMonth()) && !TextUtils.isEmpty(idCardInfo.getBirthdayDay())) {
                        intent.putExtra(EXTRA_BIRTHDAY, idCardInfo.getBirthdayYear()
                                + "-"
                                + idCardInfo.getBirthdayMonth()
                                + "-"
                                + idCardInfo.getBirthdayDay());
                    }

                    intent.putExtra(EXTRA_ADDRESS, idCardInfo.getAddress());
                    intent.putExtra(EXTRA_NUMBER, idCardInfo.getNumber());
                    intent.putExtra(EXTRA_FRONT_IMAGE_SOURCE, idCardInfo.getFrontImageType().name());
                    if (idCardInfo.getFrontImage() != null) {
                        String frontImagePath =
                                Environment.getExternalStorageDirectory() + "/sensetime/idcard/front.jpg";
                        ImageUtil.saveBitmapToFile(idCardInfo.getFrontImage(), frontImagePath);
                        intent.putExtra(EXTRA_FRONT_RESULT_IMAGE, frontImagePath);
                    }

                    intent.putExtra(EXTRA_IS_ONLY_NAME, mOnlyNameNumber);
                    Bundle rectBundle = new Bundle();
                    rectBundle.putParcelable(EXTRA_NAME_RECT, idCardInfo.getNameRect());
                    rectBundle.putParcelable(EXTRA_NUMBER_RECT, idCardInfo.getNumberRect());
                    rectBundle.putParcelable(EXTRA_PHOTO_RECT, idCardInfo.getPhotoRect());
                    intent.putExtras(rectBundle);
                }

                setResult(RESULT_OK, intent);
            }
            finish();
        }

        @Override
        public void onOnlineCheckBegin() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingView.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void onOneSideSuccess(final IdCardInfo idCardInfo) {
            mOverlayView.post(new Runnable() {
                @Override
                public void run() {
                    mOverlayView.setText(R.string.scan_success, Color.parseColor("#53EFA0"));
                }
            });
            mOverlayView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mOverlayView.setText(
                            idCardInfo.getSide() == IdCardSide.FRONT ? R.string.scan_tip_back : R.string.scan_tip_front,
                            Color.WHITE);
                }
            }, 1000);
        }
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        IdCardApi.init(FILES_PATH + LICENSE_FILE_NAME, FILES_PATH + MODEL_FILE_NAME, API_KEY, API_SECRET, mListener);

        IdCardApi.setScanOptions(this.mScanMode, this.mScanSide, this.mKeyRequires);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        IdCardApi.inputScanImage(this, data, this.mCamera.getPreviewSize(),
                this.mCameraPreview.convertViewRectToCameraPreview(this.mOverlayView.getCardRect()),
                this.mCamera.getRotationDegrees());
    }
}