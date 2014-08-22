package com.ak.android.tutorialview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

/**
 * Created by Madhu on 6/7/14.
 */
public class TutorialFrame extends RelativeLayout {
    private Callbacks mCallbacks;

    public TutorialFrame(Context context) {
        super(context);
    }

    public TutorialFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TutorialFrame(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCallbacks(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    public static interface Callbacks {
        public void onBackKeyPressed();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            mCallbacks.onBackKeyPressed();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
