package com.yskj.daishuguan.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sensetime.senseid.sdk.liveness.interactive.MotionComplexity;
import com.sensetime.senseid.sdk.liveness.interactive.NativeMotion;
import com.yskj.daishuguan.Constant;

/**
 * CaoPengFei
 * 2018/12/27
 *
 * @ClassName: SettingUtil
 * @Description:
 */

public enum SettingUtil {

    INSTANCE;

    /**
     * 获取难易程度.
     */
    public int getDifficulty(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String difficultyString = preferences.getString(Constant.COMPLEXITY,"Normal" );
        if ("Easy".equals(difficultyString)) {
            return MotionComplexity.EASY;
        } else if ("Normal".equals(difficultyString)) {
            return MotionComplexity.NORMAL;
        } else if ("Hard".equals(difficultyString)) {
            return MotionComplexity.HARD;
        } else if ("Hell".equals(difficultyString)) {
            return MotionComplexity.HELL;
        }
        return -1;


    }

    /**
     * 获取动作序列.
     */
    public int[] getSequencesInt(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constant.DETECTLIST, context.MODE_PRIVATE);
        String input = preferences.getString(Constant.DETECTLIST, Constant.DEFAULTDETECTLIST);
        String[] sequencesString = input.split("\\s+");
        int[] sequences = new int[sequencesString.length];
        for (int index = 0; index < sequencesString.length; index++) {
            if ("BLINK".equals(sequencesString[index])) {
                sequences[index] = NativeMotion.CV_LIVENESS_BLINK;
            } else if ("MOUTH".equals(sequencesString[index])) {
                sequences[index] = NativeMotion.CV_LIVENESS_MOUTH;
            } else if ("YAW".equals(sequencesString[index])) {
                sequences[index] = NativeMotion.CV_LIVENESS_HEADYAW;
            } else if ("NOD".equals(sequencesString[index])) {
                sequences[index] = NativeMotion.CV_LIVENESS_HEADNOD;
            }
        }
        return sequences;
    }
}
