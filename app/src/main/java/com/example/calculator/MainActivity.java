package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etFirst;
    private EditText etSecond;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);

        // Add padding so content doesn't go under the status bar / nav bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the views from the layout
        etFirst = findViewById(R.id.etFirst);
        etSecond = findViewById(R.id.etSecond);
        tvResult = findViewById(R.id.tvResult);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnDivide = findViewById(R.id.btnDivide);

        // Every button calls the same method with its operator
        btnAdd.setOnClickListener(v -> calculate('+'));
        btnSubtract.setOnClickListener(v -> calculate('-'));
        btnMultiply.setOnClickListener(v -> calculate('*'));
        btnDivide.setOnClickListener(v -> calculate('/'));
    }

    // Reads both numbers, does the math, and shows the result
    private void calculate(char op) {
        String firstText = etFirst.getText().toString().trim();
        String secondText = etSecond.getText().toString().trim();

        // Both boxes must have something in them
        if (firstText.isEmpty() || secondText.isEmpty()) {
            showToast(R.string.error_empty);
            return;
        }

        // Turn the text into numbers (catches input like "-" or ".")
        double a;
        double b;
        try {
            a = Double.parseDouble(firstText);
            b = Double.parseDouble(secondText);
        } catch (NumberFormatException e) {
            showToast(R.string.error_invalid);
            return;
        }

        double result;
        switch (op) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                // Can't divide by zero
                if (b == 0) {
                    showToast(R.string.error_divide_zero);
                    return;
                }
                result = a / b;
                break;
            default:
                return;
        }

        tvResult.setText(getString(R.string.result_format, formatNumber(result)));
    }

    // Shows 5 instead of 5.0, but keeps decimals like 2.5
    private String formatNumber(double number) {
        if (number == Math.rint(number) && Math.abs(number) < 1e15) {
            return String.valueOf((long) number);
        }
        return String.valueOf(number);
    }

    // Small helper to show a short Toast message
    private void showToast(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }
}
