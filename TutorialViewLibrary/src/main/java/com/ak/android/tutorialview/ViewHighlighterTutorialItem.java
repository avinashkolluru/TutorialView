package com.ak.android.tutorialview;

import android.app.Activity;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akolluru on 09/06/14.
 */
public class ViewHighlighterTutorialItem implements  TutorialItem{
    private int[] viewIds;
    private int stringResId;
    private Activity activity;

    public ViewHighlighterTutorialItem(Activity activity, int stringResId, int... viewIds) {
        this.activity = activity;
        this.stringResId = stringResId;
        this.viewIds = viewIds;
    }

    public View getView(){
        View v = LayoutInflater.from(activity).inflate(R.layout.tutorial_view_content, null);
        List<RectF> viewAnchors = new ArrayList<RectF>();
        for(int viewId : viewIds) {
            View anchorView = activity.findViewById(viewId);
            if(null != anchorView) {
                int[] screenLocation = new int[2];
                anchorView.getLocationOnScreen(screenLocation);
                viewAnchors.add(new RectF(screenLocation[0], screenLocation[1], screenLocation[0] + anchorView.getWidth(), screenLocation[1] + anchorView.getHeight()));
            }
        }
        BackgroundViewAnchorHighlighter backgroundViewHighlighter = (BackgroundViewAnchorHighlighter) v.findViewById(R.id.tutorial_frame_background_view_highlighter);
        backgroundViewHighlighter.addViewAnchorRect(viewAnchors);
        TextView textView = (TextView) v.findViewById(R.id.tutorial_frame_content_text);
        textView.setText(stringResId);
        return v;
    }
}
