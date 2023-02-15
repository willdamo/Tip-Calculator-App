package com.example.tipcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    TextView currentTip, currentSplit, currentSplitNum;
    Button homeButton;
    EditText tipInput, ynInput, splitNumInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //connecting variables to the activity variables
        homeButton = findViewById(R.id.homeButton);
        currentTip = findViewById(R.id.currentTipView);
        currentSplit = findViewById(R.id.currentSplitView);
        currentSplitNum = findViewById(R.id.currentSplitNumView);
        tipInput = findViewById(R.id.tipInput);
        ynInput = findViewById(R.id.ynInput);
        splitNumInput = findViewById(R.id.splitNumInput);

        int tipPercentage;
        boolean no;
        int splitInt;

        //gets the intent from the main activity
        Intent i = getIntent();

        //gets the tip percentage amount
        tipPercentage = i.getIntExtra("tipSeekBar", 15);

        //gets the no boolean value
        no = i.getBooleanExtra("noButton", true);

        //gets the splitInput value
        splitInt = i.getIntExtra("splitInput", 0);

        //sets the current textViews to their values in main
        currentTip.setText(String.valueOf(tipPercentage));
        if(no){
            currentSplit.setText("N");
        }else{
            currentSplit.setText("Y");
        }
        currentSplitNum.setText(String.valueOf(splitInt));



    }
}