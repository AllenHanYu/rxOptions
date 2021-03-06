package com.example.hanyu.deeplinktest.weight;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class BLinearLayout extends LinearLayout {

    public BLinearLayout(Context context) {
        super(context);
    }

    public BLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
