package team.corpore.in.pz2calculator;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String SIGNS = "+-*/";
    private static final String SIGN_EQ = "=";

    private TextView input;
    private NestedScrollView inputScroll;
    private TextView number1;
    private TextView number2;
    private TextView number3;
    private TextView number4;
    private TextView number5;
    private TextView number6;
    private TextView number7;
    private TextView number8;
    private TextView number9;
    private TextView number0;
    private TextView backspaceButton;
    private TextView eqButton;
    private TextView plusButton;
    private TextView minusButton;
    private TextView multiplyButton;
    private TextView divideButton;
    private TextView dotButton;

    private String firstVariable = "";
    private String secondVariable = "";
    private String resultVariable = "";
    private String sign = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);
        inputScroll = findViewById(R.id.input_scroll);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        number4 = findViewById(R.id.number4);
        number5 = findViewById(R.id.number5);
        number6 = findViewById(R.id.number6);
        number7 = findViewById(R.id.number7);
        number8 = findViewById(R.id.number8);
        number9 = findViewById(R.id.number9);
        number0 = findViewById(R.id.number0);
        backspaceButton = findViewById(R.id.backspace_button);
        eqButton = findViewById(R.id.eq_button);
        plusButton = findViewById(R.id.plus_button);
        minusButton = findViewById(R.id.minus_button);
        multiplyButton = findViewById(R.id.multiply_button);
        divideButton = findViewById(R.id.divide_button);
        dotButton = findViewById(R.id.dot_button);

        input.setOnClickListener(new OnClickNumberButton());
        number1.setOnClickListener(new OnClickNumberButton());
        number2.setOnClickListener(new OnClickNumberButton());
        number3.setOnClickListener(new OnClickNumberButton());
        number4.setOnClickListener(new OnClickNumberButton());
        number5.setOnClickListener(new OnClickNumberButton());
        number6.setOnClickListener(new OnClickNumberButton());
        number7.setOnClickListener(new OnClickNumberButton());
        number8.setOnClickListener(new OnClickNumberButton());
        number9.setOnClickListener(new OnClickNumberButton());
        number0.setOnClickListener(new OnClickNumberButton());
        backspaceButton.setOnClickListener(new OnClickBackspaceButton());
        eqButton.setOnClickListener(new OnClickEqButton());
        plusButton.setOnClickListener(new OnClickSignButton());
        minusButton.setOnClickListener(new OnClickSignButton());
        multiplyButton.setOnClickListener(new OnClickSignButton());
        divideButton.setOnClickListener(new OnClickSignButton());
        dotButton.setOnClickListener(new OnClickNumberButton());
    }

    private double operationOfSign(double firstVariable, double secondVariable, String sign) {
        switch (sign) {
            case "+":
                return firstVariable + secondVariable;
            case "-":
                return firstVariable - secondVariable;
            case "*":
                return firstVariable * secondVariable;
            case "/":
                return firstVariable / secondVariable;
            default:
                return 0;
        }
    }

    class OnClickNumberButton implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String buttonValue = ((TextView) v).getText().toString();
            if (sign.isEmpty()) {
                firstVariable += buttonValue;
            }
            else {
                secondVariable += buttonValue;
            }

            String result;
            if (!secondVariable.isEmpty()) {
                calculateResult();
                result = firstVariable + sign + secondVariable + SIGN_EQ + resultVariable;
            }
            else {
                result = firstVariable + sign;
            }

            input.setText(result);
            inputScroll.fullScroll(View.FOCUS_DOWN);
        }
    }

    class OnClickBackspaceButton implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String result;
            if (secondVariable.isEmpty()) {
                if (sign.isEmpty()) {
                    if (firstVariable.isEmpty()) {
                        return;
                    }
                    else {
                        firstVariable = firstVariable.substring(0, firstVariable.length() - 1);
                        result = firstVariable;
                    }
                }
                else {
                    sign = "";
                    result = firstVariable;
                }
            }
            else {
                secondVariable = secondVariable.substring(0, secondVariable.length() - 1);
                if (secondVariable.isEmpty()) {
                    result = firstVariable + sign;
                }
                else {
                    calculateResult();
                    result = firstVariable + sign + secondVariable + SIGN_EQ + resultVariable;
                }
            }
            input.setText(result);
            inputScroll.fullScroll(View.FOCUS_DOWN);
        }
    }

    class OnClickEqButton implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            result();
        }
    }

    class OnClickSignButton implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (firstVariable.isEmpty()) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
            else {
                String buttonValue = ((TextView) v).getText().toString();
                if (!secondVariable.isEmpty()) {
                    result();
                }
                sign = buttonValue;
                String result = firstVariable + sign;
                input.setText(result);
                inputScroll.fullScroll(View.FOCUS_DOWN);
            }
        }
    }

    private void result() {
        firstVariable = resultVariable;
        secondVariable = "";
        sign = "";
        resultVariable = "";
        input.setText(firstVariable);
        inputScroll.fullScroll(View.FOCUS_DOWN);
    }

    private void calculateResult() {
        double firstVar = Double.parseDouble(firstVariable);
        double secondVar = Double.parseDouble(secondVariable);
        double resultVar = operationOfSign(firstVar, secondVar, sign);
        resultVariable = String.valueOf(resultVar);

        int position = -1;
        for (int i = resultVariable.length() - 1; i >= 0; --i) {
            if (resultVariable.charAt(i) == '0') {
                position = i;
            }
        }

        if (position > 0) {
            if (resultVariable.charAt(position - 1) == '.') {
                position -= 1;
            }
            resultVariable = resultVariable.substring(0, position);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.KEY_FIRST_VARIABLE, firstVariable);
        outState.putString(Constants.KEY_SECOND_VARIABLE, secondVariable);
        outState.putString(Constants.KEY_RESULT_VARIABLE, resultVariable);
        outState.putString(Constants.KEY_SIGN, sign);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            firstVariable = savedInstanceState.getString(Constants.KEY_FIRST_VARIABLE);
            secondVariable = savedInstanceState.getString(Constants.KEY_SECOND_VARIABLE);
            resultVariable = savedInstanceState.getString(Constants.KEY_RESULT_VARIABLE);
            sign = savedInstanceState.getString(Constants.KEY_SIGN);
        }

        String result = "";
        if (firstVariable.isEmpty()) {
            input.setText(result);
            return;
        }
        else {
            result += firstVariable;
        }
        if (sign != null && sign.isEmpty()) {
            input.setText(result);
            return;
        }
        else {
            result += sign;
        }
        if (secondVariable.isEmpty()) {
            input.setText(result);
            return;
        }
        else {
            result += secondVariable;
        }
        if (resultVariable.isEmpty()) {
            input.setText(result);
            return;
        }
        else {
            result += "=" + resultVariable;
        }
        input.setText(result);
    }
}
