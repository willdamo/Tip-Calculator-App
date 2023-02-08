package com.example.tipcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        //sets the seekbar initial value to 15%
        tipSeekBar.setProgress(15);
        tipPercentageView.setText(String.valueOf(tipSeekBar.getProgress())+"%");

        //sets splitNumberTextView and splitInput to invisible when app first loads up
        splitNumberTextView.setVisibility(View.INVISIBLE);
        splitInput.setVisibility(View.INVISIBLE);

        //disables splitInput so user doesn't randomly touch it while it is invisible
        splitInput.setEnabled(false);


        //Gets the initial purchase that was inputted
        //final Double initPurchase = Double.parseDouble(String.valueOf(purchaseInput));

        //keeps track of the amount total as it goes through the program
        final Double[] amountTotal = new Double[1];

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
                amountTotal[0] = Double.parseDouble(String.valueOf(purchaseInput.getText())) + tipAmount;

                //sets the amount output
                amountOutput.setText("$"+String.format("%.2f",amountTotal[0]));
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

                }
            }
        });

        //-----------------------------------------------------------------------------------
        //checking the splitInput ime options and adjusting accordingly
        if(splitInput.getImeOptions()== 6){

            //calculates the split total for each buyer
            Double splitNumber = Double.parseDouble(String.valueOf(splitInput.getText()));
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
}