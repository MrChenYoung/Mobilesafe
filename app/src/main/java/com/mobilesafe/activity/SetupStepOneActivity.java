package com.mobilesafe.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mobilesafe.R;

public class SetupStepOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_step_one);
    }

    /**
     * 下一步
     * @param view
     */
    public void nextStep(View view){
        Intent intent = new Intent(this,SetupStepTwoActivity.class);
        startActivity(intent);

        finish();

        overridePendingTransition(R.anim.next_in,R.anim.next_out);
    }
}
