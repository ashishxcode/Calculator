package co.ashishsonani.calculatorapp.Activity;

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
import android.provider.Settings;
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

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import co.ashishsonani.calculatorapp.Model.dbMiddleware;
import co.ashishsonani.calculatorapp.R;

public class MainActivity extends AppCompatActivity {

    private Button ce, openBrc, closeBrc, div, mul, minus, plus, dot, back, equals;
    private Button zero, one, two, three, four, five, six, seven, eight, nine;
    private Button sin, cos, tan, pow, sqroot, fact;
    private TextView expr, result;

    String deviceID;
    String[] appThemeList;

    int currentDayNight;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        currentDayNight = AppCompatDelegate.getDefaultNightMode();

        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.appToolBar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
            toolbar.setTitle(" ");
            toolbar.setSubtitle(" ");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }


        // id linking
        expr = findViewById(R.id.expr);
        result = findViewById(R.id.result);
        ce = findViewById(R.id.ce);
        openBrc = findViewById(R.id.openBrc);
        closeBrc = findViewById(R.id.closeBrc);
        div = findViewById(R.id.div);
        mul = findViewById(R.id.mul);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        dot = findViewById(R.id.dot);
        back = findViewById(R.id.back);
        equals = findViewById(R.id.equals);
        sin = findViewById(R.id.sin);
        cos = findViewById(R.id.cos);
        tan = findViewById(R.id.tan);
        pow = findViewById(R.id.pow);
        sqroot = findViewById(R.id.sqroot);
        //fact = findViewById(R.id.fact);

        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);


        // Numbers
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("1", true);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("2", true);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("3", true);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("4", true);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("5", true);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("6", true);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("7", true);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("8", true);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("9", true);
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("0", true);
            }
        });
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr(".", true);
            }
        });


        // Operators
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("+", false);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("-", false);
            }
        });
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("*", false);
            }
        });
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("/", false);
            }
        });
        openBrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("(", false);
            }
        });
        closeBrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr(")", false);
            }
        });

        //app theme list
        appThemeList = getResources().getStringArray(R.array.appTheme);

        ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expr.setText("");
                result.setText("");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = expr.getText().toString();
                if (!string.isEmpty()) {
                    expr.setText(string.substring(0, string.length() - 1));
                }
                result.setText("");
            }
        });

        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("sin(", false);
            }
        });
        cos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("cos(", false);
            }
        });
        tan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("tan(", false);
            }
        });
        sqroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("sqrt(", false);
            }
        });
        pow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("^", false);
            }
        });

        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Expression expression = new ExpressionBuilder(expr.getText().toString()).build();
                    double reslt = expression.evaluate();
                    int intres = (int) reslt;
                    String inputExpr = expr.getText().toString();
                    String finalResult;
                    if (reslt == intres) {
                        result.setText(Integer.toString(intres));
                        finalResult = Integer.toString(intres);
                    } else {
                        result.setText(Double.toString(reslt));
                        finalResult = Double.toString(reslt);
                    }

                    try {
                        dbMiddleware dbM = new dbMiddleware(deviceID, inputExpr, finalResult, "Main Activity");
                        dbM.writeDB();
                        Toast.makeText(MainActivity.this, "Stored in Database!", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Failed to Store in Database!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    result.setText("Invalid Op!");
                }
            }
        });

    }

    void appendOnExpr(String string, Boolean canClear) {
        if (!result.getText().toString().isEmpty()) {
            expr.setText("");
        }

        if (canClear) {
            result.setText("");
            expr.append(string);
        } else {
            expr.append(result.getText());
            expr.append(string);
            result.setText("");
        }
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
            case R.id.appHistory:
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
                break;
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
}
