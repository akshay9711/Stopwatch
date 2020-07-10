package com.creatpixel.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /*
//Old timer

    int x = 0;
    public void startButton(View view){
        EditText typeNumber = findViewById(R.id.typeNumber);
        final TextView displayTimer = findViewById(R.id.timerDsiplay);
        final Button startButton = findViewById(R.id.startButton);

        //Converting seconds into mili seconds
        String typeNumberStr = typeNumber.getText().toString() + "000";
        final int typeNumberInt = Integer.parseInt(typeNumberStr);

        //Change colour of button when start
        startButton.setBackgroundColor(Color.RED);

        new CountDownTimer(typeNumberInt, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                //String xStr = Integer.toString(x);
                x++;
                Toast.makeText(MainActivity.this, "Run " + x, Toast.LENGTH_SHORT).show();
                Log.i("akku", "Run "+ x + "And miliseconds "+ typeNumberInt);

                displayTimer.setText(Integer.toString(x));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Finished", Toast.LENGTH_SHORT).show();
                Log.i("akku", "Finished "+x);
                x = 0;

                //Back to normal of button colour when timer finished
                startButton.setBackgroundColor(Color.WHITE);
            }
        }.start();
    }
*/
    EditText LenghtOfEveryRun;
    EditText HowManyTimesRun;
    EditText WaitAfterEveryRun;
    TextView CountingNumberText;
    TextView waitingNumber;
    TextView countingWaitingNumber;
    CountDownTimer countDownTimer;
    CountDownTimer countDownTimer2;
    CountDownTimer countDownTimer3;
    Button startButton;

    Boolean countingFinished = true;
    int count = 1;
    int waitNumberCount = 1;
    int countingWaitingNum = 1;

    public void startButton(View view){
        //When we click on start button we will save the value we input in that 3 textEditors.
        saveData();

        LenghtOfEveryRun = findViewById(R.id.lengthOfEveryRun);
        HowManyTimesRun = findViewById(R.id.HowManyTimesRun);
        WaitAfterEveryRun = findViewById(R.id.WaitAfterEveryRun);
        startButton = findViewById(R.id.startButton);

        int waitAfterEveryRunInt = Integer.parseInt(WaitAfterEveryRun.getText().toString())*1000;
        int lenghtOfRun = Integer.parseInt(LenghtOfEveryRun.getText().toString())*1000;
        int howManyTimesRun = Integer.parseInt(HowManyTimesRun.getText().toString())*lenghtOfRun;

        int howManyTimesRun2 = howManyTimesRun + lenghtOfRun + waitAfterEveryRunInt;



        countDownTimer2 = new CountDownTimer(howManyTimesRun2, lenghtOfRun+waitAfterEveryRunInt) {
            @Override
            public void onTick(long millisUntilFinished) {
                countingFunction();
                //Toast.makeText(MainActivity.this, "countingFunction called", Toast.LENGTH_SHORT).show();
                //Change button colour to green
                startButton.setBackgroundColor(Color.rgb(255, 56, 56));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "All Finished", Toast.LENGTH_SHORT).show();
                waitNumberCount = 1;
            }
        }.start();
    }
    public void countingFunction(){
        LenghtOfEveryRun = findViewById(R.id.lengthOfEveryRun);
        HowManyTimesRun = findViewById(R.id.HowManyTimesRun);
        int lenghtOfRun = Integer.parseInt(LenghtOfEveryRun.getText().toString())*1000;
        int howManyTimesRun = Integer.parseInt(HowManyTimesRun.getText().toString())*1000;

        CountingNumberText = findViewById(R.id.countingNumber);

        countDownTimer = new CountDownTimer(lenghtOfRun, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                String CountStr = Integer.toString(count);
                CountingNumberText.setText(CountStr);
                count++;
            }

            @Override
            public void onFinish() {
                //Toast.makeText(MainActivity.this, "Finished", Toast.LENGTH_SHORT).show();
                countingFinished = true;
                count = 1;
                waitingFunction();
                startButton.setBackgroundColor(Color.rgb(106, 255, 56));
            }
        }.start();
    }
    public void waitingFunction(){
        final int waitNumber = Integer.parseInt(WaitAfterEveryRun.getText().toString())*1000;
        waitingNumber = findViewById(R.id.waitngNumber);
        countingWaitingNumber = findViewById(R.id.countWaitingNumber);

        countDownTimer3 = new CountDownTimer(waitNumber, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                String x = Integer.toString(waitNumberCount);
                waitingNumber.setText(x);
                waitingNumber.setTextColor(Color.GREEN);
                waitNumberCount++;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                waitingNumber.setTextColor(Color.BLACK);
                waitingNumber.setText("0");
                waitNumberCount = 1;

                countingWaitingNumber.setText(Integer.toString(countingWaitingNum));
                countingWaitingNum++;
            }
        }.start();
    }
    public void saveData(){
        LenghtOfEveryRun = findViewById(R.id.lengthOfEveryRun);
        HowManyTimesRun = findViewById(R.id.HowManyTimesRun);
        WaitAfterEveryRun = findViewById(R.id.WaitAfterEveryRun);

        String L = LenghtOfEveryRun.getText().toString();
        String H = HowManyTimesRun.getText().toString();
        String W = WaitAfterEveryRun.getText().toString();

        //Set value into our all 3 TextView of stopWatch
        //We are saving values in sharedpreferences
        SharedPreferences saveValue = getSharedPreferences("stopWatchData", MODE_PRIVATE);
        SharedPreferences.Editor editor = saveValue.edit();
        //Get make a unique keys and understand in easy language that create some folder for storing values.
        editor.putString("LenghtOfEveryRun", L);
        editor.putString("HowManyTimesRun", H);
        editor.putString("WaitAfterEveryRun", W);
        //Apply this all 3 values with the help of apply method.
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LenghtOfEveryRun = findViewById(R.id.lengthOfEveryRun);
        HowManyTimesRun = findViewById(R.id.HowManyTimesRun);
        WaitAfterEveryRun = findViewById(R.id.WaitAfterEveryRun);

        //Now when we open app we want our old values that we save before not Default value so for this
        //We code like this...
        SharedPreferences getValue = getSharedPreferences("stopWatchData", MODE_PRIVATE);
        //Set values in a strings, I created string for short length of code.
        String l = getValue.getString("LenghtOfEveryRun", "30");
        String h = getValue.getString("HowManyTimesRun", "5");
        String w = getValue.getString("WaitAfterEveryRun", "15");
        //Finally set this strings to main all 3 TextEditors
        LenghtOfEveryRun.setText(l);
        HowManyTimesRun.setText(h);
        WaitAfterEveryRun.setText(w);
    }
}
