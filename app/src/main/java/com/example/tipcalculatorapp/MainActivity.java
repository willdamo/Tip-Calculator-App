package com.example.tipcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

        //sets splitNumberTextView and splitInput to invisible when app first loads up
        splitNumberTextView.setVisibility(View.INVISIBLE);
        splitInput.setVisibility(View.INVISIBLE);
        //disables splitInput so user doesn't randomly touch it while it is invisible
        splitInput.setEnabled(false);


        Double initPurchase = Double.parseDouble(String.valueOf(purchaseInput));

        //keeps track of the amount total as it goes through the program
        final Double[] amountTotal = new Double[1];

        //calculating the total cost percentage and tip amount using onProgressChanged
        tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Double tipToDouble = Double.parseDouble(String.valueOf(i))/100;

                //calculates the tip amount into a double
                Double tipAmount = Double.parseDouble(String.valueOf(purchaseInput))*tipToDouble;

                //sets the tip output
                tipOutput.setText(String.valueOf(tipAmount));

                //calculates the amount total including tip
                amountTotal[0] = initPurchase + tipAmount;

                //sets the amount output
                amountOutput.setText(String.valueOf(amountTotal[0]));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //-----------------------------------------------------------------------------

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

                    Double splitNumber = Double.parseDouble(String.valueOf(splitInput));
                    //calculates the split total for each buyer
                    Double splitAmount = amountTotal[0]/splitNumber;

                    //sets last two TextViews to their respective quantities
                    previousToFinalTextView.setText("Cost of each split:");
                    finalAmountTextView.setText(String.valueOf(splitAmount));

                }else{
                    //sets last two TextViews to their respective quantities
                    previousToFinalTextView.setText("Final Costs: ");
                    finalAmountTextView.setText(String.valueOf(amountTotal[0]));

                }
            }
        });


    }
}