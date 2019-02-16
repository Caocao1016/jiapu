package com.sensetime.liveness.motion.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

import com.sensetime.sample.common.R;
import com.sensetime.senseid.sdk.liveness.interactive.NativeMotion;

public class MotionPagerAdapter extends PagerAdapter {

    private int[] mMotions;

    public MotionPagerAdapter(int[] motions) {
        mMotions = Arrays.copyOf(motions, motions.length);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        View view = View.inflate(context, R.layout.common_view_motion, null);

        TextView title = (TextView) view.findViewById(R.id.txt_title);
        ImageView motionImage = (ImageView) view.findViewById(R.id.img_image);

        int motion = mMotions[position];
        switch (motion) {
            case NativeMotion.CV_LIVENESS_BLINK:
                title.setText(context.getString(R.string.common_blink));
                motionImage.setImageResource(R.drawable.common_img_blink);
                break;
            case NativeMotion.CV_LIVENESS_MOUTH:
                title.setText(context.getString(R.string.common_mouth));
                motionImage.setImageResource(R.drawable.common_img_mouth);
                break;
            case NativeMotion.CV_LIVENESS_HEADNOD:
                title.setText(context.getString(R.string.common_nod));
                motionImage.setImageResource(R.drawable.common_img_nod);
                break;
            case NativeMotion.CV_LIVENESS_HEADYAW:
                title.setText(context.getString(R.string.common_yaw));
                motionImage.setImageResource(R.drawable.common_img_yaw);
                break;
            default:
                break;
        }

        AnimationDrawable animationDrawable = (AnimationDrawable) (motionImage.getDrawable());

        animationDrawable.start();

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mMotions == null ? 0 : mMotions.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
}