package com.ak.android.tutorialview.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.start_tutorial_layout).setOnClickListener(this);
        findViewById(R.id.multiple_view_layout).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(R.id.start_tutorial_layout == view.getId()){
            startActivity(new Intent(getApplicationContext(), SampleTutorialActivity.class));
        } else if(R.id.multiple_view_layout == view.getId()){
            startActivity(new Intent(getApplicationContext(), SingleShotTutorialActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
