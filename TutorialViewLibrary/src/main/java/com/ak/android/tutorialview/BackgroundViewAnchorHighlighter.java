package com.ak.android.tutorialview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madhu on 6/7/14.
 */
public class BackgroundViewAnchorHighlighter extends View {
    private static final float PADDING = 4f;
    private int mBackgroundColor;
    private Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<RectF> anchorViews = new ArrayList<RectF>();
    private RectF canvasRectF;
    private float mPadding;

    public BackgroundViewAnchorHighlighter(Context context) {
        super(context);
        init(context);
    }

    public BackgroundViewAnchorHighlighter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BackgroundViewAnchorHighlighter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mBackgroundColor = context.getResources().getColor(R.color.transparent_black);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mBackgroundColor);
        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightPaint.setColor(context.getResources().getColor(R.color.tutorial_view_highlighter_color));
        highlightPaint.setStrokeWidth(10f);
        mPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PADDING, context.getResources().getDisplayMetrics());
    }

    public void addViewAnchorRect(List<RectF> viewAnchors) {
        //add some padding, just to be safe
        for(RectF viewAnchor : viewAnchors) {
            float left = viewAnchor.left - mPadding;
            float right = viewAnchor.right + mPadding;
            float top = viewAnchor.top - mPadding;
            float bottom = viewAnchor.bottom + mPadding;
            this.anchorViews.add(new RectF(left, top, right, bottom));
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasRectF = new RectF(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (anchorViews != null && anchorViews.size() > 0) {
            for(RectF anchorView: anchorViews) {
                canvas.clipRect(anchorView, Region.Op.DIFFERENCE);
            }
            canvas.drawRect(canvasRectF, mBackgroundPaint);
            //clip rects need highlighting without background paint mask
            for(RectF anchorView: anchorViews) {
                canvas.drawRoundRect(anchorView, 6f, 6f, highlightPaint);
            }
        }
    }

}
