package com.sensetime.liveness.motion;

import android.app.Activity;

import com.sensetime.senseid.sdk.liveness.interactive.common.type.ResultCode;

/**
 * Created on 2018/03/13.
 *
 * @author Zhu Xiangdong
 */
public class ActivityUtils {
    public static final int RESULT_CODE_NO_PERMISSIONS = Activity.RESULT_FIRST_USER + 1;
    public static final int RESULT_CODE_CAMERA_ERROR = Activity.RESULT_FIRST_USER + 2;
    public static final int RESULT_CODE_LICENSE_FILE_NOT_FOUND = Activity.RESULT_FIRST_USER + 3;
    public static final int RESULT_CODE_WRONG_STATE = Activity.RESULT_FIRST_USER + 4;
    public static final int RESULT_CODE_LICENSE_EXPIRE = Activity.RESULT_FIRST_USER + 5;
    public static final int RESULT_CODE_LICENSE_PACKAGE_NAME_MISMATCH = Activity.RESULT_FIRST_USER + 6;
    public static final int RESULT_CODE_CHECK_LICENSE_FAIL = Activity.RESULT_FIRST_USER + 7;
    public static final int RESULT_CODE_TIMEOUT = Activity.RESULT_FIRST_USER + 8;
    public static final int RESULT_CODE_CHECK_MODEL_FAIL = Activity.RESULT_FIRST_USER + 9;
    public static final int RESULT_CODE_MODEL_FILE_NOT_FOUND = Activity.RESULT_FIRST_USER + 10;
    public static final int RESULT_CODE_ERROR_API_KEY_SECRET = Activity.RESULT_FIRST_USER + 11;
    public static final int RESULT_CODE_MODEL_EXPIRE = Activity.RESULT_FIRST_USER + 12;
    public static final int RESULT_CODE_SERVER = Activity.RESULT_FIRST_USER + 13;
    public static final int RESULT_CODE_DETECT_FAIL = Activity.RESULT_FIRST_USER + 14;
    public static final int RESULT_CODE_FACE_STATE = Activity.RESULT_FIRST_USER + 15;
    public static final int RESULT_CODE_SDK_VERSION_MISMATCH = Activity.RESULT_FIRST_USER + 16;
    public static final int RESULT_CODE_PLATFORM_NOTSUPPORTED = Activity.RESULT_FIRST_USER + 17;
    public static final int RESULT_CODE_FACE_COVERED = Activity.RESULT_FIRST_USER + 18;
    public static final int RESULT_CODE_NETWORK_TIMEOUT = Activity.RESULT_FIRST_USER + 19;
    public static final int RESULT_CODE_INVALID_ARGUMENTS = Activity.RESULT_FIRST_USER + 20;

    static int convertResultCode(final ResultCode resultCode) {
        switch (resultCode) {
            case STID_E_LICENSE_FILE_NOT_FOUND:
                return RESULT_CODE_LICENSE_FILE_NOT_FOUND;
            case STID_E_CALL_API_IN_WRONG_STATE:
                return RESULT_CODE_WRONG_STATE;
            case STID_E_LICENSE_EXPIRE:
                return RESULT_CODE_LICENSE_EXPIRE;
            case STID_E_LICENSE_VERSION_MISMATCH:
                return RESULT_CODE_SDK_VERSION_MISMATCH;
            case STID_E_LICENSE_PLATFORM_NOT_SUPPORTED:
                return RESULT_CODE_PLATFORM_NOTSUPPORTED;
            case STID_E_LICENSE_BUNDLE_ID_INVALID:
                return RESULT_CODE_LICENSE_PACKAGE_NAME_MISMATCH;
            case STID_E_LICENSE_INVALID:
                return RESULT_CODE_CHECK_LICENSE_FAIL;
            case STID_E_MODEL_INVALID:
                return RESULT_CODE_CHECK_MODEL_FAIL;
            case STID_E_MODEL_FILE_NOT_FOUND:
                return RESULT_CODE_MODEL_FILE_NOT_FOUND;
            case STID_E_API_KEY_INVALID:
                return RESULT_CODE_ERROR_API_KEY_SECRET;
            case STID_E_MODEL_EXPIRE:
                return RESULT_CODE_MODEL_EXPIRE;
            case STID_E_TIMEOUT:
                return RESULT_CODE_TIMEOUT;
            case STID_E_SERVER_ACCESS:
                return RESULT_CODE_SERVER;
            case STID_E_HACK:
            case STID_E_DETECT_FAIL:
            case STID_E_CHECK_CONFIG_FAIL:
                return RESULT_CODE_DETECT_FAIL;
            case STID_E_NOFACE_DETECTED:
                return RESULT_CODE_FACE_STATE;
            case STID_E_FACE_COVERED:
                return RESULT_CODE_FACE_COVERED;
            case STID_E_SERVER_TIMEOUT:
                return RESULT_CODE_NETWORK_TIMEOUT;
            case STID_E_INVALID_ARGUMENTS:
                return RESULT_CODE_INVALID_ARGUMENTS;
            default:
                return Activity.RESULT_OK;
        }
    }
}