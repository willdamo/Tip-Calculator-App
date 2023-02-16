package com.example.tipcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

        //Creates a shared preferences file
        SharedPreferences file = getSharedPreferences("settingsFile", Context.MODE_PRIVATE);

        //Creates an editor
        SharedPreferences.Editor editor = file.edit();

        //sets the home button to launch back to the main activity
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //transfers data from tip edit text box to Shared preferences file
                int tipInt = Integer.parseInt(String.valueOf(tipInput.getText()));

                String yn = String.valueOf(ynInput.getText());
                yn = yn.toUpperCase();

                int groupInt = Integer.parseInt(String.valueOf(splitNumInput.getText()));

                editor.putInt("tipOverride", tipInt);
                editor.putString("ynOverride", yn);
                editor.putInt("splitNumOverride", groupInt);
                editor.commit();

                Intent toHome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toHome);
            }
        });

    }
}