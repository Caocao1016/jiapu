package com.yskj.daishuguan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sensetime.liveness.motion.ActivityUtils;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.entity.evbus.liveResultEvbusBean;

import org.greenrobot.eventbus.EventBus;

/**
 * CaoPengFei
 * 2018/12/28
 *
 * @ClassName: LiveResultActivity
 * @Description:
 */

public class LiveResultActivity  extends Activity {

    Button bt_restart;
    TextView tv_tip;
    private String messageCode = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_facedect_result);

        initView();
    }

    private void initView() {

        bt_restart = (Button) findViewById(R.id.bt_restart);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        switch (getIntent().getIntExtra("extra_result_code", -1)) {
            case Activity.RESULT_CANCELED:
                messageCode = "活体检测已取消";
                finish();
                break;
            case ActivityUtils.RESULT_CODE_CAMERA_ERROR:
                messageCode = "初始化相机失败";
                break;
            case ActivityUtils.RESULT_CODE_NO_PERMISSIONS:
                messageCode = "权限检测失败，请检查应用权限设置";
                break;
            case ActivityUtils.RESULT_CODE_TIMEOUT:
                messageCode = "检测超时，请重试一次";
                break;
            case ActivityUtils.RESULT_CODE_MODEL_FILE_NOT_FOUND:
                messageCode = "模型文件不存在";
                break;
            case ActivityUtils.RESULT_CODE_LICENSE_FILE_NOT_FOUND:
                messageCode = "授权文件不存在";
                break;
            case ActivityUtils.RESULT_CODE_CHECK_LICENSE_FAIL:
            case ActivityUtils.RESULT_CODE_SDK_VERSION_MISMATCH:
            case ActivityUtils.RESULT_CODE_PLATFORM_NOTSUPPORTED:
                messageCode = "未通过授权验证";
                break;
            case ActivityUtils.RESULT_CODE_CHECK_MODEL_FAIL:
                messageCode = "模型文件错误";
                break;
            case ActivityUtils.RESULT_CODE_LICENSE_EXPIRE:
                messageCode = "授权文件过期";
                break;
            case ActivityUtils.RESULT_CODE_LICENSE_PACKAGE_NAME_MISMATCH:
                messageCode = "绑定包名错误";
                break;
            case ActivityUtils.RESULT_CODE_WRONG_STATE:
                messageCode = "错误的方法状态调用";
                break;
            case ActivityUtils.RESULT_CODE_DETECT_FAIL:
                messageCode = "活体检测失败，请重试一次";
                break;
            case ActivityUtils.RESULT_CODE_FACE_STATE:
                messageCode = "动作幅度过大，请保持人脸在屏幕中央，重试一次";
                break;
            case ActivityUtils.RESULT_CODE_FACE_COVERED:
                messageCode = "请调整人脸姿态，去除面部遮挡，正对屏幕重试一次";
                break;
            case ActivityUtils.RESULT_CODE_NETWORK_TIMEOUT:
                messageCode = "网络请求超时,请稍后重试";
                break;
            case ActivityUtils.RESULT_CODE_ERROR_API_KEY_SECRET:
                messageCode = "API_KEY 或 API_SECRET 错误";
                break;
            case ActivityUtils.RESULT_CODE_SERVER:
                messageCode = "服务器访问错误";
                break;
            case ActivityUtils.RESULT_CODE_INVALID_ARGUMENTS:
                messageCode = "参数设置不合法";
                break;
            default:
                break;
        }
        tv_tip.setText(messageCode);
        bt_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new liveResultEvbusBean());
                finish();
            }
        });
    }
}
