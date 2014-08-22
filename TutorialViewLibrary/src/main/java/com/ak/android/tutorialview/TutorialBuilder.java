package com.ak.android.tutorialview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by Madhu on 6/6/14.
 */
public class TutorialBuilder {

    private Tutorial tutorial;
    private int tutId;
    private boolean force;

    public TutorialBuilder(Activity activity) {
        this.tutorial = new Tutorial(activity);
    }

    public TutorialBuilder setTutId(int id){
        this.tutorial.setTutId(id);
        return this;
    }

    public TutorialBuilder setForce(boolean force){
        this.tutorial.setForce(force);
        return this;
    }

    public TutorialBuilder addTutItem(TutorialItem item) {
        this.tutorial.addItem(item);
        return this;
    }

    public TutorialBuilder addListener(Tutorial.TutorialListener listener) {
        tutorial.setTutorialListener(listener);
        return this;
    }

    public Tutorial build() {
        return tutorial;
    }


    public static class Tutorial implements TutorialFrame.Callbacks {

        public static interface TutorialListener {
            public void onTutorialFinished();
        }
        private static final String TUTORIAL_SHARED_PREFS_NAME = "TUT_PREFS";
        private TutorialFrame tutorialFrame;
        private ViewPager viewPager;
        private boolean mIsAdded;
        private Activity mActivity;
        private ArrayList<TutorialItem> items = new ArrayList<TutorialItem>();
        private int tutId;
        private boolean force;
        private TutorialListener mCallbacks = null;

        private Tutorial(Activity activity) {
            this.mActivity = activity;
        }

        public void run() {
            if(shouldShowTutorial()) {
                WindowManager windowManager = mActivity.getWindowManager();
                tutorialFrame = (TutorialFrame) mActivity.getLayoutInflater().inflate(R.layout.tutorial_frame, null);
                View activityContentView = mActivity.findViewById(android.R.id.content);
                WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, PixelFormat.RGBA_8888);
                int[] activityWindowLocation = new int[2];
                activityContentView.getLocationOnScreen(activityWindowLocation);
                windowParams.gravity = Gravity.START | Gravity.TOP;
                windowParams.x = activityWindowLocation[0];
                windowParams.y = activityWindowLocation[1];
                windowManager.addView(tutorialFrame, windowParams);
                mIsAdded = true;

                tutorialFrame.setCallbacks(this);

                viewPager = (ViewPager) tutorialFrame.findViewById(R.id.tutorial_pager);
                CirclePageIndicator pagerIndicator = (CirclePageIndicator) tutorialFrame.findViewById(R.id.tutorial_pager_indicator);
                TutorialPagerAdapter pagerAdapter = new TutorialPagerAdapter(items, this);
                viewPager.setAdapter(pagerAdapter);
                pagerIndicator.setViewPager(viewPager);

                setTutorialState(false);
            }
        }

        public void setTutId(int tutId) {
            this.tutId = tutId;
        }

        public void setForce(boolean force) {
            this.force = force;
        }

        private boolean shouldShowTutorial(){
            if(this.force){
                return this.force;
            }
            else{
                SharedPreferences prefs = mActivity.getSharedPreferences(TUTORIAL_SHARED_PREFS_NAME, Context.MODE_PRIVATE);
                return prefs.getBoolean(Integer.valueOf(this.tutId).toString(), true);
            }
        }

        private void setTutorialState(boolean state){
            SharedPreferences.Editor editor = mActivity.getSharedPreferences(TUTORIAL_SHARED_PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putBoolean(Integer.valueOf(this.tutId).toString(), state);
            editor.commit();
        }

        protected void setTutorialListener(TutorialListener mCallbacks) {
            this.mCallbacks = mCallbacks;
        }

        public void destroy() {
            if(mIsAdded) {
                WindowManager windowManager = mActivity.getWindowManager();
                windowManager.removeViewImmediate(tutorialFrame);
                if(mCallbacks != null) {
                    mCallbacks.onTutorialFinished();
                }
                mIsAdded = false;
            }
        }

        @Override
        public void onBackKeyPressed() {
            destroy();
        }

        public boolean isDestroyed() {
            return !mIsAdded;
        }

        public void addItem(TutorialItem item) {
            items.add(item);
        }
    }

    private static class TutorialPagerAdapter extends PagerAdapter {

        private final Tutorial mTutorial;
        private ArrayList<TutorialItem> mTutorialItems;

        public TutorialPagerAdapter(ArrayList<TutorialItem> viewIds, Tutorial tutorial) {
            this.mTutorialItems = viewIds;
            this.mTutorial = tutorial;
        }

        @Override
        public int getCount() {
            return mTutorialItems.size();
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
           View v = mTutorialItems.get(position).getView();
            v.setClickable(true);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTutorial.destroy();
                }
            });
           ((ViewPager) collection).addView(v, 0);
           return v;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            ((ViewPager) collection).removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position+1);
        }
    }


}
