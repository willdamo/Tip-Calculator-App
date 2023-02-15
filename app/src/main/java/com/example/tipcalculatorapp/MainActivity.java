package com.example.tipcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText purchaseInput;
    SeekBar tipSeekBar;
    TextView amountOutput;
    RadioGroup ynRadioGroup;
    TextView tipOutput;
    RadioButton yesButton;
    RadioButton noButton;
    TextView splitNumberTextView;
    EditText splitInput;
    TextView finalAmountTextView, previousToFinalTextView;
    TextView tipPercentageView;
    Button calculateButton;

    //keeps track of the amount total as it goes through the program
    Double amountTotal;

    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connecting variables to the widgets
        purchaseInput = findViewById(R.id.purchaseInput);
        tipSeekBar = findViewById(R.id.tipSeekBar);
        ynRadioGroup = findViewById(R.id.ynRadioGroup);
        amountOutput = findViewById(R.id.amountOutput);
        tipOutput = findViewById(R.id.tipOutput);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        splitNumberTextView = findViewById(R.id.splitNumberTextView);
        splitInput = findViewById(R.id.splitInput);
        previousToFinalTextView = findViewById(R.id.previousToFinalTextView);
        finalAmountTextView = findViewById(R.id.finalAmountTextView);
        tipPercentageView = findViewById(R.id.tipPercentageView);
        calculateButton = findViewById(R.id.calculateButton);
        settingsButton = findViewById(R.id.settingsButton);

        //sets the seekbar initial value to 15%
        tipSeekBar.setProgress(15);
        tipPercentageView.setText(String.valueOf(tipSeekBar.getProgress())+"%");

        //sets the default value of the splitInput to 0
        splitInput.setText("0");

        //sets splitNumberTextView and splitInput to invisible when app first loads up
        splitNumberTextView.setVisibility(View.INVISIBLE);
        splitInput.setVisibility(View.INVISIBLE);
        finalAmountTextView.setVisibility(View.INVISIBLE);

        //disables splitInput so user doesn't randomly touch it while it is invisible
        splitInput.setEnabled(false);

        //sets the settings button to launch the settings activity when clicked
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSettings = new Intent(getApplicationContext(), SettingsActivity.class);

                //stores the progress of the tipSeekBar at the current spot
                int tipPercentage = tipSeekBar.getProgress();
                //sends it over to settings activity
                toSettings.putExtra("tipSeekBar", tipPercentage);

                //checks to see which of the two buttons is currently checked and sends the data
                //over
                if(noButton.isChecked()){
                    boolean no = true;
                    toSettings.putExtra("noButton", no);
                }else{
                    boolean no = false;
                    toSettings.putExtra("noButton", no);
                }

                //stores the value of the current splitInput
                int splitInt = Integer.parseInt(String.valueOf(splitInput.getText()));
                //sends it over to settings activity
                toSettings.putExtra("splitInput", splitInt);

                //starts the intent
                startActivity(toSettings);
            }
        });

        //calculating the total cost percentage and tip amount using onProgressChanged
        tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tipPercentageView.setText(String.valueOf(tipSeekBar.getProgress())+"%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //-----------------------------------------------------------------------------

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double tipToDouble = Double.parseDouble(String.valueOf(tipSeekBar.getProgress()))/100;

                //calculates the tip amount into a double
                Double tipAmount = Double.parseDouble(String.valueOf(purchaseInput.getText()))*tipToDouble;

                //sets the tip output
                tipOutput.setText("$"+String.format("%.2f",tipAmount));

                //calculates the amount total including tip
                amountTotal = Double.parseDouble(String.valueOf(purchaseInput.getText())) + tipAmount;

                //sets the amount output
                amountOutput.setText("$"+String.format("%.2f",amountTotal));
            }
        });

        //---------------------------------------------------------------------------------

        //checking yes/no button click in ynRadioGroup
        ynRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                //if yesButton is checked then display EditText
                if(yesButton.isChecked()){

                    //turns visibility back on for splitNumberTextView and splitInput
                    splitNumberTextView.setVisibility(View.VISIBLE);
                    splitInput.setVisibility(View.VISIBLE);

                    //enables the splitInput to be used again
                    splitInput.setEnabled(true);
                    splitInput.setImeOptions(6);

                }else {

                    //enables visibility on the finalAmountTextView
                    finalAmountTextView.setVisibility(View.VISIBLE);

                    //sets last two TextViews to their respective quantities
                    previousToFinalTextView.setText("Final Costs: ");
                    finalAmountTextView.setText("$"+String.format("%.2f",amountTotal));
                }
            }
        });

        //-----------------------------------------------------------------------------------

        //programming the the ime option: actionDone below
        splitInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    //calculates the split total for each buyer
                    Double splitNumber = Double.parseDouble(String.valueOf(splitInput.getText()));
                    Double splitAmount = amountTotal/splitNumber;

                    //enables visibility on the finalAmountTextView
                    finalAmountTextView.setVisibility(View.VISIBLE);

                    //sets last two TextViews to their respective quantities
                    previousToFinalTextView.setText("Cost of each split:");
                    finalAmountTextView.setText("$"+String.format("%.2f",splitAmount));
                    return true;
                }
                return false;
            }
        });
    }
}