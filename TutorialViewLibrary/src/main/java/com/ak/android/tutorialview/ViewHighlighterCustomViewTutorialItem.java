package com.ak.android.tutorialview;

import android.app.Activity;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by akolluru on 09/06/14.
 */
public class ViewHighlighterCustomViewTutorialItem implements TutorialItem{

    private int viewId;
    private int layoutId;
    private Activity activity;

    public ViewHighlighterCustomViewTutorialItem(Activity activity, int viewId, int layoutId) {
        this.activity = activity;
        this.viewId = viewId;
        this.layoutId = layoutId;
    }

    public View getView(){
        RelativeLayout v = (RelativeLayout)LayoutInflater.from(activity).inflate(this.layoutId, null);

        if(-1 != viewId) {
            View anchorView = activity.findViewById(viewId);
            if(null != anchorView) {
                int[] screenLocation = new int[2];
                anchorView.getLocationOnScreen(screenLocation);
                final RectF viewAnchor = new RectF(screenLocation[0], screenLocation[1], screenLocation[0] + anchorView.getWidth(), screenLocation[1] + anchorView.getHeight());
                BackgroundViewAnchorHighlighter backgroundViewHighlighter = (BackgroundViewAnchorHighlighter) v.findViewById(R.id.tutorial_frame_background_view_highlighter);
                backgroundViewHighlighter.setVisibility(View.VISIBLE);
                backgroundViewHighlighter.addViewAnchorRect(new ArrayList<RectF>() {
                                                                {
                                                                    add(viewAnchor);
                                                                }
                                                            }
                );
            }
        }

        return v;
    }
}
