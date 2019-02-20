package com.yskj.daishuguan.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.adapter.BillAdapter;
import com.yskj.daishuguan.adapter.EnvelopeAdapter;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.ManagementListRequest;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.modle.ManagementMoneyView;
import com.yskj.daishuguan.presenter.ManagementMoneyPresenter;
import com.yskj.daishuguan.response.ManagementListItemResponse;
import com.yskj.daishuguan.response.ManagementListResponse;
import com.yskj.daishuguan.response.ManagementResponse;
import com.yskj.daishuguan.response.ShareContentResponse;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: EnvelopeActivity
 * @Description: 优惠券
 */

public class EnvelopeActivity extends BaseActivity<ManagementMoneyPresenter> implements
        BaseQuickAdapter.RequestLoadMoreListener, ManagementMoneyView {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    private int mPageNo = 1;
    private EnvelopeAdapter mAdapter;
    private View emptyView;
    private boolean mIsLoadMore;

    @Override
    protected ManagementMoneyPresenter createPresenter() {
        return new ManagementMoneyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_envelope;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_envelope_title;
    }

    @Override
    protected void initView() {
        emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new EnvelopeAdapter(null);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<ManagementListItemResponse> entity = adapter.getData();
                for (ManagementListItemResponse mList : entity) {
                    mList.setSelect(!entity.get(position).isSelect());
                    mAdapter.notifyItemChanged(position);
                }
            }
        });
    }


    @OnClick({R.id.tv_sure,R.id.rl_share})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_sure) {
            List<ManagementListItemResponse> entity = mAdapter.getData();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.reverse();
            for (ManagementListItemResponse mList : entity) {
                if (mList.isSelect()) {
                    stringBuilder.append(mList.getId() + ",");
                }
            }
            Intent intent = new Intent();
            intent.putExtra("mListID", stringBuilder.toString());
            setResult(2, intent);
            finish();
        }else if (view.getId() == R.id.rl_share){

            initAdress();
        }


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
                            ocrRequest.userid = RxSPTool.getString(EnvelopeActivity.this, Constant.USER_ID);
                            ocrRequest.token = RxSPTool.getString(EnvelopeActivity.this, Constant.TOKEN);
                            ocrRequest.merchantCode = Constant.merchantcode;
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
                            XXPermissions.gotoPermissionSettings(EnvelopeActivity.this);
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
    protected void initData() {
        ManagementListRequest request = new ManagementListRequest();
        request.userid = RxSPTool.getString(this, Constant.USER_ID);
        request.token = RxSPTool.getString(this, Constant.TOKEN);
        request.type = "1";
        request.page = mPageNo;
        request.limit = 10;
        mPresenter.couponUse(request);
    }

    @Override
    public void onLoadMoreRequested() {
        mIsLoadMore = true;
        initData();
    }

    @Override
    public void onSuccess(ManagementResponse response) {

    }

    @Override
    public void onCouponUseSuccess(ManagementListResponse response) {
        List<ManagementListItemResponse> entity = response.getList();
        if (mIsLoadMore) {
            mIsLoadMore = false;
            if (entity != null && entity.size() <= Constant.PAGE_SIZE && entity.size() > 0) {
                mAdapter.addData(entity);
                mPageNo++;
                mAdapter.loadMoreComplete();
            } else {
                mAdapter.loadMoreEnd(true);
            }
        } else {
            if (null != entity && entity.size() > 0) {
                mPageNo++;
                mAdapter.setNewData(entity);
            } else {
                mAdapter.setEmptyView(emptyView);
            }
        }
    }

    @Override
    public void onShareSuccess(ShareContentResponse response) {
        new ShareAction(EnvelopeActivity.this)
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
                                thumb = new UMImage(EnvelopeActivity.this,
                                        R.mipmap.ic_logo
                                );
                            } else {
                                thumb = new UMImage(EnvelopeActivity.this,
                                        response.getIcon()
                                );

                            }

                            UMWeb umWeb = new UMWeb(response.getShareUrl());
                            umWeb.setTitle(response.getTittle());
                            umWeb.setThumb(thumb);  //缩略图
                            umWeb.setDescription(response.getContent());//描述

                            new ShareAction(EnvelopeActivity.this).withMedia(umWeb)
                                    .setPlatform(share_media)
                                    .setCallback(umShareListener)
                                    .share();
                        }
                    }
                }).open();
    }

    @Override
    public void onFailure(BaseResponse response) {
        UIUtils.showToast(response.getRetmsg());
    }

    @Override
    public void onError() {

    }
}
