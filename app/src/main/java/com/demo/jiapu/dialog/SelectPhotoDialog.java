package com.demo.jiapu.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class SelectPhotoDialog extends Dialog {
    public SelectPhotoDialog(@NonNull Context context) {
        super(context);
    }

    public SelectPhotoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

}
