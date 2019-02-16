package com.yskj.daishuguan.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.modle.ShareView;
import com.yskj.daishuguan.presenter.SharePresenter;
import com.yskj.daishuguan.response.ShareContentResponse;
import com.yskj.daishuguan.response.ShareListResponse;
import com.yskj.daishuguan.response.ShareResponse;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/16
 *
 * @ClassName: MyShareActivity
 * @Description: 邀请
 */

public class MyShareActivity extends BaseActivity<SharePresenter> implements ShareView {

    @BindView(R.id.tv_money)
    TextView mMoney;

    @BindView(R.id.tv_remake)
    TextView mRemaKe;

    @BindView(R.id.tv_apply_for)
    TextView mApplyFor;

    @BindView(R.id.tv_audit)
    TextView mAudit;


    @BindView(R.id.tv_customer)
    TextView mCustomer;


    @BindView(R.id.tv_wait)
    TextView mWait;


    @Override
    protected SharePresenter createPresenter() {
        return new SharePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_share;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_share_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        OCRRequest ocrRequest = new OCRRequest();
        ocrRequest.userid = RxSPTool.getString(this, Constant.USER_ID);
        ocrRequest.token = RxSPTool.getString(this, Constant.TOKEN);
        ocrRequest.merchantCode = Constant.Merchant_Code;
        mPresenter.inviteHomePage(ocrRequest);
    }


    @OnClick({R.id.tv_sure, R.id.rl_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:

                initAdress();
                break;
            case R.id.rl_list:
                startActivity(MyShareListActivity.class);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void initAdress() {

        XXPermissions.with(this)
                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.ACCESS_FINE_LOCATION,
                        Permission.CALL_PHONE, Permission.READ_PHONE_STATE,
                        Permission.READ_EXTERNAL_STORAGE, Permission.SYSTEM_ALERT_WINDOW, Permission.GET_ACCOUNTS)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            OCRRequest ocrRequest = new OCRRequest();
                            ocrRequest.userid = RxSPTool.getString(MyShareActivity.this, Constant.USER_ID);
                            ocrRequest.token = RxSPTool.getString(MyShareActivity.this, Constant.TOKEN);
                            ocrRequest.merchantCode = Constant.Merchant_Code;
                            mPresenter.share(ocrRequest);

                        } else {
                            ToastUtils.show("获取权限成功，部分权限未正常授予");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            ToastUtils.show("被永久拒绝授权，请手动授予权限");
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(MyShareActivity.this);
                        } else {
                            ToastUtils.show("获取权限失败");
                        }
                    }
                });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            UIUtils.showToast("分享成功！");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            UIUtils.showToast("分享取消！");
        }
    };

    @Override
    public void onSuccess(ShareResponse response) {
        mRemaKe.setText(response.getRemark());
        mMoney.setText(response.getContent());
    }

    @Override
    public void onRecordSuccess(ShareListResponse response) {

    }

    @Override
    public void onShareSuccess(ShareContentResponse response) {
        new ShareAction(MyShareActivity.this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .addButton("复制链接", "复制链接", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
                .setShareboardclickCallback(new ShareBoardlistener() {

                    private UMImage thumb;

                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (snsPlatform.mKeyword.equals("复制链接")) {
                            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            // 将文本内容放到系统剪贴板里。
                            cm.setText(response.getShareUrl());
                            UIUtils.showToast("链接已复制成功！");

                        } else {
                            if (StringUtil.isEmpty(response.getIcon())) {
                                thumb = new UMImage(MyShareActivity.this,
                                        R.mipmap.ic_logo
                                );
                            } else {
                                thumb = new UMImage(MyShareActivity.this,
                                        response.getIcon()
                                );

                            }

                            UMWeb umWeb = new UMWeb(response.getShareUrl());
                            umWeb.setTitle(response.getTittle());
                            umWeb.setThumb(thumb);  //缩略图
                            umWeb.setDescription(response.getContent());//描述

                            new ShareAction(MyShareActivity.this).withMedia(umWeb)
                                    .setPlatform(share_media)
                                    .setCallback(umShareListener)
                                    .share();
                        }
                    }
                }).open();
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFailure(BaseResponse response) {
        UIUtils.showToast(response.getRetmsg());
    }
}
