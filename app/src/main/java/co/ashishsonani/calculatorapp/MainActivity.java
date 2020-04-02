package co.ashishsonani.calculatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Objects;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private int openParenthesis = 0;

    private boolean dotUsed = false;

    private boolean equalClicked = false;
    private String lastExpression = "";
    private final static int IS_NUMBER = 0;
    private final static int IS_OPERAND = 1;
    private final static int IS_OPEN_PARENTHESIS = 2;
    private final static int IS_CLOSE_PARENTHESIS = 3;
    private final static int IS_DOT = 4;

    Button buttonNumber0;
    Button buttonNumber1;
    Button buttonNumber2;
    Button buttonNumber3;
    Button buttonNumber4;
    Button buttonNumber5;
    Button buttonNumber6;
    Button buttonNumber7;
    Button buttonNumber8;
    Button buttonNumber9;

    Button buttonClear;
    Button buttonParentheses;
    Button buttonPercent;
    Button buttonDivision;
    Button buttonMultiplication;
    Button buttonSubtraction;
    Button buttonAddition;
    Button buttonEqual;
    Button buttonDot;

    TextView textViewInputNumbers;
    Toolbar appToolbar;

    String[] appThemeList;
    ScriptEngine scriptEngine;

    int currentDayNight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scriptEngine = new ScriptEngineManager().getEngineByName("rhino");

        initializeViewVariables();
        setOnClickListeners();

        setSupportActionBar(appToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        appToolbar.setTitle("");
        appToolbar.setSubtitle("");

        textViewInputNumbers.setMovementMethod(new ScrollingMovementMethod());
        currentDayNight = AppCompatDelegate.getDefaultNightMode();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.appTheme:
                int itemSelected = 0;
                new AlertDialog.Builder(this)
                        .setTitle("Choose Theme")
                        .setSingleChoiceItems(appThemeList, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                if (selectedIndex == 0) {
                                    darkMode();
                                } else if (selectedIndex == 1) {
                                    lightMode();
                                } else {
                                    systemFollowMode();
                                }
                            }
                        })
                        .setPositiveButton("Ok", null)
                        .setNegativeButton("Cancel", null)
                        .show();
                break;
            case R.id.exitApp:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void systemFollowMode() {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        getDelegate().applyDayNight();
        Toast.makeText(getApplicationContext(), "System Default ", Toast.LENGTH_LONG).show();
    }

    private void lightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getDelegate().applyDayNight();
        Toast.makeText(getApplicationContext(), "Light ", Toast.LENGTH_LONG).show();
    }

    private void darkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        getDelegate().applyDayNight();
        Toast.makeText(getApplicationContext(), "Dark Mode", Toast.LENGTH_LONG).show();
    }


    private void initializeViewVariables() {
        buttonNumber0 = findViewById(R.id.zeroButton);
        buttonNumber1 = findViewById(R.id.oneButton);
        buttonNumber2 = findViewById(R.id.twoButton);
        buttonNumber3 = findViewById(R.id.threeButton);
        buttonNumber4 = findViewById(R.id.fourButton);
        buttonNumber5 = findViewById(R.id.fiveButton);
        buttonNumber6 = findViewById(R.id.siXButton);
        buttonNumber7 = findViewById(R.id.sevenButton);
        buttonNumber8 = findViewById(R.id.eightButton);
        buttonNumber9 = findViewById(R.id.nineButton);

        buttonClear = findViewById(R.id.clearButton);
        buttonParentheses = findViewById(R.id.parenthesesButton);
        buttonPercent = findViewById(R.id.percentButton);
        buttonDivision = findViewById(R.id.divisionButton);
        buttonMultiplication = findViewById(R.id.multiplicationButton);
        buttonSubtraction = findViewById(R.id.subtractionButton);
        buttonAddition = findViewById(R.id.additionButton);
        buttonEqual = findViewById(R.id.equalButton);
        buttonDot = findViewById(R.id.dotButton);

        textViewInputNumbers = findViewById(R.id.inputeNumberTextView);

        appToolbar = findViewById(R.id.appToolBar);

        appThemeList = getResources().getStringArray(R.array.appTheme);
    }

    private void setOnClickListeners() {
        buttonNumber0.setOnClickListener(this);
        buttonNumber1.setOnClickListener(this);
        buttonNumber2.setOnClickListener(this);
        buttonNumber3.setOnClickListener(this);
        buttonNumber4.setOnClickListener(this);
        buttonNumber5.setOnClickListener(this);
        buttonNumber6.setOnClickListener(this);
        buttonNumber7.setOnClickListener(this);
        buttonNumber8.setOnClickListener(this);
        buttonNumber9.setOnClickListener(this);

        buttonClear.setOnClickListener(this);
        buttonClear.setOnLongClickListener(this);
        buttonParentheses.setOnClickListener(this);
        buttonPercent.setOnClickListener(this);
        buttonDivision.setOnClickListener(this);
        buttonMultiplication.setOnClickListener(this);
        buttonSubtraction.setOnClickListener(this);
        buttonAddition.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);
        buttonDot.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zeroButton:
                if (addNumber("0")) equalClicked = false;
                break;
            case R.id.oneButton:
                if (addNumber("1")) equalClicked = false;
                break;
            case R.id.twoButton:
                if (addNumber("2")) equalClicked = false;
                break;
            case R.id.threeButton:
                if (addNumber("3")) equalClicked = false;
                break;
            case R.id.fourButton:
                if (addNumber("4")) equalClicked = false;
                break;
            case R.id.fiveButton:
                if (addNumber("5")) equalClicked = false;
                break;
            case R.id.siXButton:
                if (addNumber("6")) equalClicked = false;
                break;
            case R.id.sevenButton:
                if (addNumber("7")) equalClicked = false;
                break;
            case R.id.eightButton:
                if (addNumber("8")) equalClicked = false;
                break;
            case R.id.nineButton:
                if (addNumber("9")) equalClicked = false;
                break;
            case R.id.additionButton:
                if (addOperand("+")) equalClicked = false;
                break;
            case R.id.subtractionButton:
                if (addOperand("-")) equalClicked = false;
                break;
            case R.id.multiplicationButton:
                if (addOperand("x")) equalClicked = false;
                break;
            case R.id.divisionButton:
                if (addOperand("\u00F7")) equalClicked = false;
                break;
            case R.id.percentButton:
                if (addOperand("%")) equalClicked = false;
                break;
            case R.id.dotButton:
                if (addDot()) equalClicked = false;
                break;
            case R.id.parenthesesButton:
                if (addParenthesis()) equalClicked = false;
                break;
            case R.id.clearButton:
                if (clearTextView()) equalClicked = false;
                break;
            case R.id.equalButton:
                textViewInputNumbers.getText().toString();
                if (!textViewInputNumbers.getText().toString().equals(""))
                    calculate(textViewInputNumbers.getText().toString());
                break;
        }

    }


    @SuppressLint("SetTextI18n")
    private boolean addDot() {
        boolean done = false;

        if (textViewInputNumbers.getText().length() == 0) {
            textViewInputNumbers.setText("0.");
            dotUsed = true;
            done = true;
        } else if (dotUsed) {
        } else if (defineLastCharacter(textViewInputNumbers.getText().charAt(textViewInputNumbers.getText().length() - 1) + "") == IS_OPERAND) {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + "0.");
            done = true;
            dotUsed = true;
        } else if (defineLastCharacter(textViewInputNumbers.getText().charAt(textViewInputNumbers.getText().length() - 1) + "") == IS_NUMBER) {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + ".");
            done = true;
            dotUsed = true;
        }
        return done;
    }

    private boolean addParenthesis() {
        boolean done = false;
        int operationLength = textViewInputNumbers.getText().length();

        if (operationLength == 0) {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + "(");
            dotUsed = false;
            openParenthesis++;
            done = true;
        } else if (openParenthesis > 0 && operationLength > 0) {
            String lastInput = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
            switch (defineLastCharacter(lastInput)) {
                case IS_NUMBER:
                case IS_CLOSE_PARENTHESIS:
                    textViewInputNumbers.setText(textViewInputNumbers.getText() + ")");
                    done = true;
                    openParenthesis--;
                    dotUsed = false;
                    break;
                case IS_OPERAND:
                case IS_OPEN_PARENTHESIS:
                    textViewInputNumbers.setText(textViewInputNumbers.getText() + "(");
                    done = true;
                    openParenthesis++;
                    dotUsed = false;
                    break;
            }
        } else if (openParenthesis == 0 && operationLength > 0) {
            String lastInput = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
            if (defineLastCharacter(lastInput) == IS_OPERAND) {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + "(");
                done = true;
                dotUsed = false;
                openParenthesis++;
            } else {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + "x(");
                done = true;
                dotUsed = false;
                openParenthesis++;
            }
        }
        return done;
    }

    private boolean addOperand(String operand) {
        boolean done = false;
        int operationLength = textViewInputNumbers.getText().length();
        if (operationLength > 0) {
            String lastInput = textViewInputNumbers.getText().charAt(operationLength - 1) + "";

            if ((lastInput.equals("+") || lastInput.equals("-") || lastInput.equals("*") || lastInput.equals("\u00F7") || lastInput.equals("%"))) {
                Toast.makeText(getApplicationContext(), "Wrong format", Toast.LENGTH_LONG).show();
            } else if (operand.equals("%") && defineLastCharacter(lastInput) == IS_NUMBER) {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + operand);
                dotUsed = false;
                equalClicked = false;
                lastExpression = "";
                done = true;
            } else if (!operand.equals("%")) {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + operand);
                dotUsed = false;
                equalClicked = false;
                lastExpression = "";
                done = true;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Wrong Format. Operand Without any numbers?", Toast.LENGTH_LONG).show();
        }
        return done;
    }

    private boolean addNumber(String number) {
        boolean done = false;
        int operationLength = textViewInputNumbers.getText().length();
        if (operationLength > 0) {
            String lastCharacter = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
            int lastCharacterState = defineLastCharacter(lastCharacter);

            if (operationLength == 1 && lastCharacterState == IS_NUMBER && lastCharacter.equals("0")) {
                textViewInputNumbers.setText(number);
                done = true;
            } else if (lastCharacterState == IS_OPEN_PARENTHESIS) {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + number);
                done = true;
            } else if (lastCharacterState == IS_CLOSE_PARENTHESIS || lastCharacter.equals("%")) {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + "x" + number);
                done = true;
            } else if (lastCharacterState == IS_NUMBER || lastCharacterState == IS_OPERAND || lastCharacterState == IS_DOT) {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + number);
                done = true;
            }
        } else {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + number);
            done = true;
        }
        return done;
    }


    private boolean clearTextView() {
        String input = textViewInputNumbers.getText().toString();
        if (input.length() > 1) {
            input = input.substring(0, input.length() - 1);
            textViewInputNumbers.setText(input);
        } else {
            input.length();
            textViewInputNumbers.setText("0");
        }
        openParenthesis = 0;
        dotUsed = false;
        return false;
    }

    private void calculate(String input) {
        String result = "";
        try {
            String temp = input;
            if (equalClicked) {
                temp = input + lastExpression;
            } else {
                saveLastExpression(input);
            }
            result = scriptEngine.eval(temp.replaceAll("%", "/100").replaceAll("x", "*").replaceAll("[^\\x00-\\x7F]", "/")).toString();
            BigDecimal decimal = new BigDecimal(result);
            result = decimal.setScale(8, BigDecimal.ROUND_HALF_UP).toPlainString();
            equalClicked = true;

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Wrong Format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (result.equals("Infinity")) {
            Toast.makeText(getApplicationContext(), "Division by zero is not allowed", Toast.LENGTH_SHORT).show();
            textViewInputNumbers.setText(input);

        } else if (result.contains(".")) {
            result = result.replaceAll("\\.?0*$", "");
            textViewInputNumbers.setText(result);
        }
    }

    private void saveLastExpression(String input) {
        String lastOfExpression = input.charAt(input.length() - 1) + "";
        if (input.length() > 1) {
            if (lastOfExpression.equals(")")) {
                lastExpression = ")";
                int numberOfCloseParenthesis = 1;

                for (int i = input.length() - 2; i >= 0; i--) {
                    if (numberOfCloseParenthesis > 0) {
                        String last = input.charAt(i) + "";
                        if (last.equals(")")) {
                            numberOfCloseParenthesis++;
                        } else if (last.equals("(")) {
                            numberOfCloseParenthesis--;
                        }
                        lastExpression = last + lastExpression;
                    } else if (defineLastCharacter(input.charAt(i) + "") == IS_OPERAND) {
                        lastExpression = input.charAt(i) + lastExpression;
                        break;
                    } else {
                        lastExpression = "";
                    }
                }
            } else if (defineLastCharacter(lastOfExpression + "") == IS_NUMBER) {
                lastExpression = lastOfExpression;
                for (int i = input.length() - 2; i >= 0; i--) {
                    String last = input.charAt(i) + "";
                    if (defineLastCharacter(last) == IS_NUMBER || defineLastCharacter(last) == IS_DOT) {
                        lastExpression = last + lastExpression;
                    } else if (defineLastCharacter(last) == IS_OPERAND) {
                        lastExpression = last + lastExpression;
                        break;
                    }
                    if (i == 0) {
                        lastExpression = "";
                    }
                }
            }
        }
    }

    private int defineLastCharacter(String lastCharacter) {
        try {
            Integer.parseInt(lastCharacter);
            return IS_NUMBER;
        } catch (NumberFormatException e) {
        }

        if ((lastCharacter.equals("+") || lastCharacter.equals("-") || lastCharacter.equals("x") || lastCharacter.equals("\u00F7") || lastCharacter.equals("%")))
            return IS_OPERAND;

        if (lastCharacter.equals("("))
            return IS_OPEN_PARENTHESIS;

        if (lastCharacter.equals(")"))
            return IS_CLOSE_PARENTHESIS;

        if (lastCharacter.equals("."))
            return IS_DOT;

        return -1;
    }

    @Override
    public boolean onLongClick(View v) {
        textViewInputNumbers.setText("0");
        return false;
    }
}
