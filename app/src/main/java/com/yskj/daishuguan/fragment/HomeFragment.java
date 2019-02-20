package com.yskj.daishuguan.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxui.view.RxTextViewVerticalMore;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.MainActivity;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.activity.AuthorizationActivity;
import com.yskj.daishuguan.activity.CerFinshActivity;
import com.yskj.daishuguan.activity.CerNumberActivity;
import com.yskj.daishuguan.activity.CertificationActivity;
import com.yskj.daishuguan.activity.LoginActivity;
import com.yskj.daishuguan.activity.MembersActivity;
import com.yskj.daishuguan.adapter.HomeViewPagerAdapter;
import com.yskj.daishuguan.adapter.WindowAdapter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.base.CommonLazyFragment;
import com.yskj.daishuguan.entity.evbus.FinshMoneyEvenbus;
import com.yskj.daishuguan.entity.evbus.QuanxianEvenbus;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.entity.request.SubmitRequest;
import com.yskj.daishuguan.modle.CommonDataView;
import com.yskj.daishuguan.presenter.CommonDataPresenter;
import com.yskj.daishuguan.response.BannerResponse;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;
import com.yskj.daishuguan.response.ItemlistResponse;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;
import com.yskj.daishuguan.view.IndicatorSeekBar;
import com.yskj.daishuguan.view.IndicatorStayLayout;
import com.yskj.daishuguan.view.OnSeekChangeListener;
import com.yskj.daishuguan.view.SeekParams;
import com.yskj.daishuguan.view.TickMarkType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * CaoPengFei
 * 2018/11/20
 *
 * @ClassName: HomeFragment
 * @Description: 首页
 */

public class HomeFragment extends CommonLazyFragment<CommonDataPresenter> implements
        BGABanner.Adapter<ImageView, String>, BGABanner.Delegate<ImageView, String>, CommonDataView {

    @BindView(R.id.sp_banner)
    BGABanner mDefaultBanner;
    @BindView(R.id.tv_window)
    TextView mTvWindow;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_money_left)
    TextView mTvMoneyLeft;
    @BindView(R.id.tv_money_right)
    TextView mTvMoneyRight;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_time_money)
    TextView mTvTiemMoney;
    @BindView(R.id.tv_money_all)
    TextView mTvAllMoney;
    @BindView(R.id.rl_bar)
    LinearLayout mRlBar;
    @BindView(R.id.upview1)
    RxTextViewVerticalMore mUpview1;

    private List<String> mImgList = new ArrayList<>();
    private List<String> mTextList = new ArrayList<>();
    private List<String> mWindow = new ArrayList<>();
    private List<ItemlistResponse> itemlist;
    private boolean authJudge;
    private boolean creditJudge;
    private boolean loanJudge;
    private int member;
    private int auditCreditLimit;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        List<View> views = new ArrayList<>();
        setUPMarqueeView(views, 6);
        mUpview1.setViews(views);

        mDefaultBanner.setAdapter(this);
        mDefaultBanner.setDelegate(this);


        mWindow.add("网上购物");
        mWindow.add("实体店购物");
        mWindow.add("培训教育");
        mWindow.add("出国留学");
        mWindow.add("婚庆装修");
        mWindow.add("餐饮娱乐");
        mWindow.add("医疗美容");
        mWindow.add("生活消费");
    }

    @Override
    protected void initData() {
        BannerRequest bannerRequest = new BannerRequest();
        bannerRequest.showType = "1";
        mPresenter.getBanner(bannerRequest);


        BannerRequest commonRequest = new BannerRequest();
        commonRequest.token = RxSPTool.getString(getContext(), Constant.TOKEN);
        commonRequest.userid = RxSPTool.getString(getContext(), Constant.USER_ID);
        mPresenter.getCommonData(commonRequest);


        BannerRequest homeInfoRequest = new BannerRequest();
        homeInfoRequest.token = RxSPTool.getString(getContext(), Constant.TOKEN);
        homeInfoRequest.userid = RxSPTool.getString(getContext(), Constant.USER_ID);
        homeInfoRequest.cycle = RxSPTool.getString(getContext(), Constant.AUTH_VALID_DAY);
        mPresenter.homeInfo(homeInfoRequest);

    }


    /**
     * 登录成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void QuanxianEvenbus(QuanxianEvenbus event) {
        initData();
    }


    @OnClick({R.id.tv_true, R.id.ll_window})
    public void submit(View view) {
        switch (view.getId()) {
            case R.id.ll_window:
                showPopwindow();
                break;
            case R.id.tv_true:

                if (RxSPTool.getString(getContext(), Constant.IS_LOGIN).equals("0")) {
                    startActivity(LoginActivity.class);
                    return;
                }
                if (StringUtil.isEmpty(mTvWindow.getText().toString())) {
                    UIUtils.showToast("请先选择借款用途");
                    return;
                }
                if (authJudge) {
                    if (creditJudge) {
                        if (loanJudge) {
                            if (member == 0) {
                                getSubmit();
                            } else {
                                Intent intent = new Intent(getContext(), MembersActivity.class);
                                intent.putExtra("moneyList", auditCreditLimit);
                                startActivity(intent);
                            }

                        } else {
                            UIUtils.showToast("您还有未还订单,无法再次借款哦~");
                        }
                    } else {
                        Intent intent = new Intent(getContext(), AuthorizationActivity.class);
                        intent.putExtra("MONEY", mTvMoney.getText().toString());
                        startActivity(intent);
                    }
                } else {

                    Intent intent = new Intent(getContext(), CertificationActivity.class);
                    intent.putExtra("maxMoney", mTvMoneyRight.getText().toString());
                    startActivity(intent);
                }
                break;
            default:
                break;

        }

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }


    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {

        Glide.with(itemView.getContext())
                .load(model)
                .apply(new RequestOptions().placeholder(R.mipmap.ic_y_bg).error(R.mipmap.ic_y_bg).dontAnimate().centerCrop())
                .into(itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable String model, int position) {

    }

    @Override
    protected CommonDataPresenter createPresenter() {
        return new CommonDataPresenter(this);
    }

    @Override
    public void onGetCommonDataSuccess(CommonDataResponse response) {

        RxSPTool.putInt(getActivity(), Constant.IS_LOGIN, response.getIsLogin());
        RxSPTool.putString(getActivity(), Constant.CONTACT_WAY, response.getServicecall());
        RxSPTool.putString(getActivity(), Constant.CONTACT_TIME, response.getServicecall_time());
        RxSPTool.putString(getActivity(), Constant.AUTH_VALID_DAY, response.getAuthValidDay());
        RxSPTool.putString(getActivity(), Constant.BEGINNING_RATE, response.getBeginningInterestRate());
        RxSPTool.putString(getActivity(), Constant.PRICE_RAGGE, response.getPriceRange());
        RxSPTool.putString(getActivity(), Constant.DAY_RATE, response.getDayRate() + "");
        RxSPTool.putString(getActivity(), Constant.IS_LOGIN, response.getIsLogin() + "");

        RxSPTool.putString(getActivity(), Constant.CHARGE_PROTOCOL, response.getCharge_protocol());
        RxSPTool.putString(getActivity(), Constant.REGISTER_PROTOCOL, response.getRegister_protocol());
        RxSPTool.putString(getActivity(), Constant.DATA_QUERY_PROTOCOL, response.getData_query_protocol());
        RxSPTool.putString(getActivity(), Constant.EXTEND_PROTOCOL, response.getExtend_protocol());
        RxSPTool.putString(getActivity(), Constant.LOAN_PROTOCOL, response.getLoan_protocol());
        RxSPTool.putString(getActivity(), Constant.MEMBER_PROTOCOL, response.getMember_protocol());
        RxSPTool.putString(getActivity(), Constant.CREDIT_PROTOCOL, response.getCredit_protocol());

        mTvTime.setText("借款期限：" + response.getAuthValidDay() + "天");
        if (!StringUtil.isEmpty(response.getPriceRange()) && response.getPriceRange().contains("-")) {
            String[] split = response.getPriceRange().split("-");
            mTvMoneyLeft.setText(StringUtil.getValue(split[0]) + "元");
            mTvMoneyRight.setText(StringUtil.getValue(split[1] + "元"));
            RxSPTool.putString(getActivity(), Constant.USER_BIG_MONEY, split[1]);
            int max = Integer.parseInt(StringUtil.trimStr(split[1], ","));
            final int min = Integer.parseInt(StringUtil.trimStr(split[0], ","));
            int count = StringUtil.getNUmber(StringUtil.trimStr(split[1], ","), StringUtil.trimStr(split[0], ","));
            mTvMoney.setText(min + "");
            mTvTiemMoney.setText("贷款利息：" + StringUtil.getActualNUmber(min, response.getDayRate()) + "元/天");
            mTvAllMoney.setText("到期应还：" + StringUtil.getALL(min, StringUtil.getActualNUmber(min, response.getDayRate()), response.getAuthValidDay()) + "元");
            IndicatorSeekBar discrete_ticks_texts_ends = IndicatorSeekBar
                    .with(getContext())
                    .max(max)
                    .min(min)
                    .progress(min)
                    .tickCount(count)
                    .showTickMarksType(TickMarkType.NONE)
                    .tickMarksColor(getResources().getColorStateList(R.color.cl_F34F03))
                    .showTickTexts(false)
                    .tickTextsColorStateList(getResources().getColorStateList(R.color.cl_F34F03))
                    .thumbColor(getResources().getColor(R.color.cl_F34F03))
                    .thumbSize(17)
                    .trackProgressColor(getResources().getColor(R.color.cl_F34F03))
                    .trackProgressSize(8)
                    .trackBackgroundColor(getResources().getColor(R.color.cl_F34F03))
                    .trackBackgroundSize(8)
                    .build();
            IndicatorStayLayout stayLayout = new IndicatorStayLayout(getContext());
            stayLayout.attachTo(discrete_ticks_texts_ends);
            mRlBar.addView(stayLayout);
            discrete_ticks_texts_ends.setOnSeekChangeListener(new OnSeekChangeListener() {
                @Override
                public void onSeeking(SeekParams seekParams) {

                }

                @Override
                public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                    mTvMoney.setText(seekBar.getProgress() + "");
                    mTvTiemMoney.setText("贷款利息：" + StringUtil.getActualNUmber(seekBar.getProgress(), response.getDayRate()) + "元/天");
                    mTvAllMoney.setText("到期应还：" + StringUtil.getALL(seekBar.getProgress(), StringUtil.getActualNUmber(seekBar.getProgress(), response.getDayRate()), response.getAuthValidDay()) + "元");

                }
            });
        }

    }

    @Override
    public void onGetBannerSuccess(List<BannerResponse> response) {

        if (response != null && response.size() > 0) {
            for (BannerResponse list : response) {
                mImgList.add(list.getPicUrl());
            }
        }

        mDefaultBanner.setData(mImgList, mTextList);
    }

    @Override
    public void onHomeInfoSuccess(HomeInfoResponse response) {

        //认证
        authJudge = response.isAuthJudge();
        //授信
        creditJudge = response.isCreditJudge();
        //可否借款
        loanJudge = response.isLoanJudge();
        member = response.getIsMember();
        auditCreditLimit = response.getAuditCreditLimit();

        RxSPTool.putBoolean(getContext(), Constant.AUTH_JUDGE, authJudge);
        RxSPTool.putBoolean(getContext(), Constant.CREDIT_JUDGE, creditJudge);
        RxSPTool.putBoolean(getContext(), Constant.LOAN_JUAGE, loanJudge);
        RxSPTool.putInt(getContext(), Constant.AUDIT_CREDIT_LIMIT, response.getAuditCreditLimit());
        RxSPTool.putString(getContext(), Constant.PRO_EXPLAIN, response.getProductintroduce());
    }

    @Override
    public void onSubmitSuccess(BaseResponse response) {
        rxDialogLoading.dismiss();
        Intent intent = new Intent(getContext(), CerFinshActivity.class);
        intent.putExtra("what", 2);
        startActivity(intent);

    }


    @Override
    public void onFailure(BaseResponse response) {
        rxDialogLoading.dismiss();
        UIUtils.showToast(response.getRetmsg());
    }

    @Override
    public void onError() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 查询认证等信息
     */
    @Override
    public void onResume() {
        super.onResume();
        BannerRequest homeInfoRequest = new BannerRequest();
        homeInfoRequest.token = RxSPTool.getString(getContext(), Constant.TOKEN);
        homeInfoRequest.userid = RxSPTool.getString(getContext(), Constant.USER_ID);
        homeInfoRequest.cycle = RxSPTool.getString(getContext(), Constant.AUTH_VALID_DAY);
        mPresenter.homeInfo(homeInfoRequest);
    }

    private void setUPMarqueeView(List<View> views, int size) {
        for (int i = 0; i < size; i++) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.fragment_item_view, null);
            //初始化布局的控件
            TextView tv2 = moreView.findViewById(R.id.tv1);

            if (i == 0) {
                tv2.setText("恭喜!     136******08用户成功借款1500元！");
            } else if (i == 1) {
                tv2.setText("恭喜!     180******26用户成功借款1000元！");
            } else if (i == 2) {
                tv2.setText("恭喜!     158******10用户成功借款3000元！");
            } else if (i == 3) {
                tv2.setText("恭喜!     136******52用户成功借款2000元！");
            } else if (i == 4) {
                tv2.setText("恭喜!     180******46用户成功借款4000元！");
            } else if (i == 5) {
                tv2.setText("恭喜!     181******46用户成功借款4000元！");
            }

            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_layout, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x33000000);
        window.setBackgroundDrawable(dw);


        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(mDefaultBanner,
                Gravity.BOTTOM, 0, 0);

        // 这里检验popWindow里的button是否可以点击
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        WindowAdapter windowAdapter = new WindowAdapter(mWindow);
        mRecyclerView.setAdapter(windowAdapter);
        TextView mCancel = (TextView) view.findViewById(R.id.tv_cancel);

        windowAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<String> mList = adapter.getData();
                mTvWindow.setText(mList.get(position));
                window.dismiss();
            }
        });


        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvWindow.setText("");
                window.dismiss();
            }
        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
    }

    /**
     * 借款
     */
    public void getSubmit() {
        rxDialogLoading.show();
        SubmitRequest submitRequest = new SubmitRequest();
        submitRequest.userId = RxSPTool.getString(getContext(), Constant.USER_ID);
        submitRequest.token = RxSPTool.getString(getContext(), Constant.TOKEN);
        submitRequest.mobileno = RxSPTool.getString(getContext(), Constant.USER_MOBILENO);
        submitRequest.cycle = RxSPTool.getString(getContext(), Constant.AUTH_VALID_DAY);
        submitRequest.loanAmount = auditCreditLimit;
        submitRequest.productNo = Build.MODEL;
        submitRequest.loanPurpose = mTvWindow.getText().toString();
        submitRequest.osType = "ANDROID";
        submitRequest.locgps = RxSPTool.getContent(getContext(), Constant.GPS_LATITUDE);
        submitRequest.locaddress = RxSPTool.getContent(getContext(), Constant.GPS_ADDRESS);
        mPresenter.getSubmit(submitRequest);
    }
}
