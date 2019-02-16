package com.sensetime.liveness.motion.view;

/**
 * Created on 2016/10/27.
 *
 * @author Han Xu
 */
public interface ITimeViewBase {

    void setProgress(float currentTime);

    void hide();

    void show();

    int getMaxTime();
}