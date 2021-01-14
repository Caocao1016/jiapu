package com.demo.jiapu.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.demo.jiapu.R;
import com.demo.jiapu.api.ApiConstant;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.entity.ReportRequest;
import com.demo.jiapu.modle.ReportView;
import com.demo.jiapu.presenter.ReportPresenter;
import com.demo.jiapu.util.FileUtil;
import com.demo.jiapu.util.GlideEngine;
import com.demo.jiapu.widget.RoundImageView;
import com.demo.jiapu.util.StringUtil;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.ToastUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReportActivity extends BaseActivity<ReportPresenter> implements ReportView {


    private static final String TAG = "ReportActivity";
    @BindView(R.id.select)
    RoundImageView roundImageView;
    @BindView(R.id.et_text)
    EditText et_text;
    private long id;
    private int type;
    private String url;

    @Override
    protected ReportPresenter createPresenter() {
        return new ReportPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        id = getIntent().getLongExtra("id",0L);
        type = getIntent().getIntExtra("type", 1);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.select, R.id.send})
    public void onClick(View view) {
        if (view.getId() == R.id.select) {
            XXPermissions.with(this)
                    //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .permission(Permission.CAMERA) //支持请求6.0悬浮窗权限8.0请求安装权限
                    .permission(Permission.Group.STORAGE) //不指定权限则自动获取清单中的危险权限
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {

                            PictureSelector.create(ReportActivity.this)
                                    .openGallery(PictureMimeType.ofImage())
                                    .imageEngine(GlideEngine.createGlideEngine())
                                    .isPreviewImage(true) // 是否可预览图片
                                    .isCompress(true)  // 是否压缩
                                    .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            //可以给toast提示 或其他
                            XXPermissions.gotoPermissionSettings(ReportActivity.this);
                        }
                    });

        } else if (view.getId() == R.id.send) {

            if (StringUtil.isEmpty(et_text.getText().toString().trim())) {
                ToastUtils.s(this, "请您填写您要举报的事项");
                return;
            }

            ReportRequest reportRequest = new ReportRequest();
            reportRequest.content = et_text.getText().toString().trim();
            reportRequest.jubao_id = id;
            reportRequest.jubao_type = type;
            reportRequest.imgs = url;
            mPresenter.getList(reportRequest);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回五种path
                    // 1.media.getPath(); 原图path
                    // 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
                    // 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
                    // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                    // 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路径；注意：.isAndroidQTransform 为false 此字段将返回空
                    // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                    for (LocalMedia media : selectList) {
                        Log.i(TAG, "是否压缩:" + media.isCompressed());
                        Log.i(TAG, "压缩:" + media.getCompressPath());
                        Log.i(TAG, "原图:" + media.getPath());
                        Log.i(TAG, "是否裁剪:" + media.isCut());
                        Log.i(TAG, "裁剪:" + media.getCutPath());
                        Log.i(TAG, "是否开启原图:" + media.isOriginal());
                        Log.i(TAG, "原图路径:" + media.getOriginalPath());
                        Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                        Log.i(TAG, "宽高: " + media.getWidth() + "x" + media.getHeight());
                        Log.i(TAG, "Size: " + media.getSize());

                    }
                    putFile(selectList.get(0).getCompressPath());
                    break;
            }
        }
    }

    private void putFile(String path) {
        File file = FileUtil.createNewFile(this, path);
        if (null != file) {
            mPresenter.sendMsgfile(file);
        } else {
            Glide.with(this)
                    .load(R.drawable.ic_add_photo)
                    .into(roundImageView);
            ToastUtils.s(this, "图片上传失败,请稍后再试");
        }
    }

    @Override
    public void onSuccess(String response) {
        ToastUtils.s(this, response);
        finish();
    }

    @Override
    public void onPhotoSuccess(String response) {
        url = String.format("%s%s", ApiConstant.BASE_PHOTO_URL, response);
        Glide.with(this)
                .load(url)
                .into(roundImageView);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }
}
