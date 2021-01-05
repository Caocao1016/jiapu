package com.demo.jiapu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.jiapu.R;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.listener.OnViewGroupItemClickListener;
import com.demo.jiapu.util.DisplayUtil;


public class AddFamilyViewGroup extends ViewGroup implements View.OnClickListener {
    public static final int ITEM_MY = 1;
    public static final int ITEM_SPOUSE = 2;
    public static final int ITEM_MOTHER = 3;
    public static final int ITEM_FATHER = 4;
    public static final int ITEM_SON = 5;
    public static final int ITEM_DAUGHTER = 6;
    public static final int ITEM_BROTHER = 7;
    public static final int ITEM_OUT_SIDE = 8;


    private static final int AVATAR_MALE = R.drawable.ic_avatar_male;//男性默认头像
    private static final int AVATAR_FEMALE = R.drawable.ic_avatar_female;//女性默认头像

    private static final int BACKGROUND_MALE = R.drawable.shape_bg_male_selector;
    private static final int BACKGROUND_FEMALE = R.drawable.shape_bg_female_selector;


    private static final String SEX_MALE = "1";//1为男性
    private static final String SEX_FEMALE = "2";//2为女性

    private int mShowWidthPX;//在屏幕所占的宽度
    private int mShowHeightPX;//在屏幕所占的高度

    private Paint mPaint;//连线样式
    private Path mPath;//路径

    private int mLineWidthPX;//连线宽度PX
    private int mItemWidthPX;
    private int mItemHeightPX;
    private int mSpacePX;//元素间距PX

    private int mWidthMeasureSpec;
    private int mHeightMeasureSpec;

    private static final int LINE_WIDTH_DP = 2;//连线宽度2dp
    private static final int SPACE_WIDTH_DP = 20;//间距为20dp
    private static final int ITEM_WIDTH_DP = 85;
    private static final int ITEM_HEIGHT_DP = 114;

    private OnViewGroupItemClickListener onViewGroupItemClickListener;

    public AddFamilyViewGroup(Context context) {
        super(context);
    }

    public AddFamilyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddFamilyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void drawViewGroup() {
        initView();
        invalidate();
    }

    private void initView() {
        setWillNotDraw(false);

        mItemWidthPX = DisplayUtil.dip2px(ITEM_WIDTH_DP);
        mItemHeightPX = DisplayUtil.dip2px(ITEM_HEIGHT_DP);
        mSpacePX = DisplayUtil.dip2px(SPACE_WIDTH_DP);
        mLineWidthPX = DisplayUtil.dip2px(LINE_WIDTH_DP);

        mWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mItemWidthPX, MeasureSpec.EXACTLY);
        mHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mItemHeightPX, MeasureSpec.EXACTLY);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.reset();
        mPaint.setColor(0xFFBBBBBB);
        mPaint.setStrokeWidth(mLineWidthPX);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
        mPath.reset();
    }

    public void addItemView(int t) {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.menu_add_item, this, false);
        final TextView textView = view.findViewById(R.id.tv_menu_add__name);
        textView.setTextSize(15);
        switch (t) {
            case ITEM_BROTHER:
                textView.setText("兄弟姐妹");
                view.setTag(t);
                break;
            case ITEM_DAUGHTER:
                textView.setText("女儿");
                view.setTag(t);
                break;
            case ITEM_FATHER:
                textView.setText("父亲");
                view.setTag(t);
                break;
            case ITEM_MOTHER:
                textView.setText("母亲");
                view.setTag(t);
                break;
            case ITEM_SON:
                textView.setText("儿子");
                view.setTag(t);
                break;
            case ITEM_SPOUSE:
                textView.setText("配偶");
                view.setTag(t);
                break;
        }

        view.setOnClickListener(this);

        addView(view);
    }

    public void addItemView(FamilyBean family) {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.menu_add_item, this, false);
        final ImageView imageView = view.findViewById(R.id.iv_menu_add_avatar);

        RequestOptions requestOptions = new RequestOptions()
                .circleCrop()
                .placeholder(SEX_FEMALE.equals(family.getSex()) ? AVATAR_FEMALE : AVATAR_MALE)
                .error(SEX_FEMALE.equals(family.getSex()) ? AVATAR_FEMALE : AVATAR_MALE).centerCrop()
                .dontAnimate();
        Glide.with(getContext())
                .load(family.getMemberImg())
                .apply(requestOptions)
                .into(imageView);
        view.setBackgroundResource(SEX_FEMALE.equals(family.getSex()) ? BACKGROUND_FEMALE : BACKGROUND_MALE);

        TextView textView = view.findViewById(R.id.tv_menu_add__name);
        textView.setTextSize(15);
        if (!family.getNickname().equals(""))
            textView.setText(family.getNickname());
        else textView.setText(family.getSurname() + family.getNames());
        textView.setText(family.getNickname());
        view.setTag(ITEM_MY);
        addView(view);


    }

    @Override
    public void onClick(View v) {
        if (onViewGroupItemClickListener != null) {
            onViewGroupItemClickListener.onItemClick((Integer) v.getTag());
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = (DisplayUtil.getScreenWidth() - mItemWidthPX) / 2;
        int top = (DisplayUtil.getScreenHeight() - mItemHeightPX) / 2;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            int tag = (Integer) childView.getTag();

            switch (tag) {
                case ITEM_MY:
                    setChildViewFrame(childView, left, top, mItemWidthPX, mItemHeightPX);
                    break;
                case ITEM_SPOUSE:
                    setChildViewFrame(childView, left + mItemWidthPX + mSpacePX, top, mItemWidthPX, mItemHeightPX);
                    break;
                case ITEM_BROTHER:
                    setChildViewFrame(childView, left - mItemWidthPX - mSpacePX, top, mItemWidthPX, mItemHeightPX);
                    break;
                case ITEM_FATHER:
                    setChildViewFrame(childView, left - (mItemWidthPX + mSpacePX) / 2, top - mItemHeightPX - mSpacePX, mItemWidthPX, mItemHeightPX);
                    break;
                case ITEM_MOTHER:
                    setChildViewFrame(childView, left + (mItemWidthPX + mSpacePX) / 2, top - mItemHeightPX - mSpacePX, mItemWidthPX, mItemHeightPX);
                    break;
                case ITEM_SON:
                    setChildViewFrame(childView, left - (mItemWidthPX + mSpacePX) / 2, top + mItemHeightPX + mSpacePX, mItemWidthPX, mItemHeightPX);
                    break;
                case ITEM_DAUGHTER:
                    setChildViewFrame(childView, left + (mItemWidthPX + mSpacePX) / 2, top + mItemHeightPX + mSpacePX, mItemWidthPX, mItemHeightPX);
                    break;
            }
        }

    }


    private void setChildViewFrame(View childView, int left, int top, int width, int height) {
        childView.layout(left, top, left + width, top + height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerX = DisplayUtil.getScreenWidth() / 2;
        int centerY = DisplayUtil.getScreenHeight() / 2;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            int tag = (Integer) childView.getTag();

            switch (tag) {
                case ITEM_MY:
                    break;
                case ITEM_SPOUSE:
                    drawHorizontalLine(canvas, centerX + mItemWidthPX / 2, childView.getLeft(), centerY);
                    break;
                case ITEM_BROTHER:
                    drawHorizontalLine(canvas, centerX - mItemWidthPX / 2, childView.getRight(), centerY);
                    break;
                case ITEM_FATHER:
                case ITEM_MOTHER:
                    drawVerticalLine(canvas, centerX, centerY - mItemHeightPX / 2, centerY - (mItemHeightPX + mSpacePX) / 2);
                    drawVerticalLine(canvas, childView.getLeft() + mItemWidthPX / 2, childView.getBottom(), centerY - (mItemHeightPX + mSpacePX) / 2);
                    drawHorizontalLine(canvas, childView.getLeft() + mItemWidthPX / 2, centerX, childView.getBottom() + mSpacePX / 2);
                    break;
                case ITEM_SON:
                case ITEM_DAUGHTER:
                    drawVerticalLine(canvas, centerX, centerY + mItemHeightPX / 2, centerY + (mItemHeightPX + mSpacePX) / 2);
                    drawVerticalLine(canvas, childView.getLeft() + mItemWidthPX / 2, childView.getTop(), childView.getTop() - mSpacePX / 2);
                    drawHorizontalLine(canvas, childView.getLeft() + mItemWidthPX / 2, centerX, childView.getTop() - mSpacePX / 2);
                    break;

            }
        }

    }

    private void drawVerticalLine(Canvas canvas, int x, int startY, int endY) {
        drawLine(canvas, x, x, startY, endY);
    }

    private void drawHorizontalLine(Canvas canvas, int startX, int endX, int y) {
        drawLine(canvas, startX, endX, y, y);
    }


    private void drawLine(Canvas canvas, int startX, int endX, int startY, int endY) {

        mPath.reset();
        mPath.moveTo(startX, startY);
        mPath.lineTo(endX, endY);
        canvas.drawPath(mPath, mPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mShowWidthPX = MeasureSpec.getSize(widthMeasureSpec);
        mShowHeightPX = MeasureSpec.getSize(heightMeasureSpec);

        final int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            childView.measure(mWidthMeasureSpec, mHeightMeasureSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setOnItemClickListener(OnViewGroupItemClickListener onViewGroupItemClickListener) {
        this.onViewGroupItemClickListener = onViewGroupItemClickListener;
    }


}
