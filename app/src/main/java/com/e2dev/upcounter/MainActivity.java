package com.e2dev.upcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    long counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Local init
        counter = 0;

        Button resetButton = (Button) findViewById(R.id.buttonReset);
        resetButton.setOnLongClickListener(new Button.OnLongClickListener(){
            public boolean onLongClick(View view){
                resetCounter(view);
                return true;
            }
        });
        resetButton.playSoundEffect(SoundEffectConstants.CLICK);
        resetButton.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS);
    }

    public void increaseCount (View view){

        counter ++;
        TextView rawLabel = (TextView)findViewById(R.id.textCountRaw);
        String label = String.format("Clicked: %d times", counter);
        rawLabel.setText( label );

        TextView unitLabel = (TextView) findViewById(R.id.textCountUnits);

        EditText incrementText = (EditText) findViewById(R.id.editIncrement);
        double incrementValue;
        try {
             incrementValue = Float.parseFloat(incrementText.getText().toString());
        }
        catch (Exception e){
            incrementValue = 0;
        }

        EditText unitText = (EditText) findViewById(R.id.editUnit);
        String unitString = unitText.getText().toString();

        if(incrementValue == (long) incrementValue){
            label = String.format( "%d %s",counter * (long)incrementValue, unitString);
        }
        else{
            label = String.format( "%s %s",counter * incrementValue, unitString);
        }

        unitLabel.setText(label);


        // Play a sound if the checkbox is ticked
        CheckBox soundCheckBox = (CheckBox) findViewById(R.id.checkBoxSound);
        Button resetButton = (Button) findViewById(R.id.buttonReset);
        if (soundCheckBox.isChecked()){
            try {
                AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                double vol = 1;
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD, (float)vol);
            } catch (Exception e) {
            }
        }

        // Play a sound if the checkbox is ticked
        CheckBox vibrateCheckBox = (CheckBox) findViewById(R.id.checkBoxVibrate);
        if (vibrateCheckBox.isChecked()){
            resetButton.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS);
        }
    }

    public void resetCounter(View view){
        counter = -1;
        increaseCount(view);
    }

}
