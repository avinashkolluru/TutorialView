package com.ak.android.tutorialview.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ak.android.tutorialview.TutorialBuilder;
import com.ak.android.tutorialview.ViewHighlighterCustomViewTutorialItem;
import com.ak.android.tutorialview.ViewHighlighterTutorialItem;


public class SampleTutorialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_tutorial);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        showTutorial();
    }

    private void showTutorial(){
        TutorialBuilder builder = new TutorialBuilder(this);

        ViewHighlighterCustomViewTutorialItem tutItem1 = new ViewHighlighterCustomViewTutorialItem(
                this,
                R.id.button1,
                R.layout.tutorial_custom_layout
        );
        ViewHighlighterTutorialItem tutItem2 = new ViewHighlighterTutorialItem(
                this,
                R.string.tut_page2_desc,
                R.id.button2, R.id.button3
        );
        builder.setForce(true)
                .addTutItem(tutItem1)
                .addTutItem(tutItem2)
                .build()
                .run();
    }
}
