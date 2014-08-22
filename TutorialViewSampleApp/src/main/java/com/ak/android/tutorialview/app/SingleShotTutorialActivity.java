package com.ak.android.tutorialview.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ak.android.tutorialview.TutorialBuilder;
import com.ak.android.tutorialview.ViewHighlighterTutorialItem;


public class SingleShotTutorialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_shot_tutorial);
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

        ViewHighlighterTutorialItem tutItem1 = new ViewHighlighterTutorialItem(
                this,
                R.string.single_shot_tut_desc,
                R.id.button1
        );
        builder.setTutId(1)
                .setForce(false)
                .addTutItem(tutItem1)
                .build()
                .run();
    }
}
