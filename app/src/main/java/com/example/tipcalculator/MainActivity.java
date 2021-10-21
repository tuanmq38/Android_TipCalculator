package com.example.tipcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
        implements TextView.OnEditorActionListener, View.OnClickListener {
    private EditText billAmountEditText;
    private TextView percentTextView;
    private Button percentUpButton;
    private Button percentDownButton;
    private TextView tipTextView;
    private TextView totalTextView;

    private String billAmountString = "";
    private float tipPercent = .15f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the UI controls
        billAmountEditText = findViewById(R.id.billAmountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        percentUpButton = findViewById(R.id.percentUpButton);
        percentDownButton = findViewById(R.id.percentDownButton);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);

        // set the listeners
        billAmountEditText.setOnEditorActionListener(this);
        percentDownButton.setOnClickListener(this);
        percentUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.percentDownButton:
                tipPercent = tipPercent - 0.01f;
                calculateAndDisplay();
                break;
            case R.id.percentUpButton:
                tipPercent = tipPercent + 0.01f;
                calculateAndDisplay();
                break;
        }
    }

    // Saving the state of the app
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("billAmountString", billAmountString);
        outState.putFloat("tipPercent", tipPercent);
    }

    // Restore the state of the app
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            billAmountString = savedInstanceState.getString("billAmountString", "");
            tipPercent = savedInstanceState.getFloat("tipPercent", 0.15f);

            billAmountEditText.setText(billAmountString);
            calculateAndDisplay();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        calculateAndDisplay();
        return false;
    }

    private void calculateAndDisplay() {
        // get the bill amount
        billAmountString = billAmountEditText.getText().toString();
        float billAmount;
        if (billAmountString.equals("")) {
            billAmount = 0;
        } else {
            billAmount = Float.parseFloat(billAmountString);
        }
        // calculate tip and total
        float tipAmount = billAmount * tipPercent;
        float totalAmount = billAmount + tipAmount;
        // display the result with formatting
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipTextView.setText(currency.format(tipAmount));
        totalTextView.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        percentTextView.setText(percent.format(tipPercent));
    }
}