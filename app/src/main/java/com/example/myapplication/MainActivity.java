package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.example.components.DialogUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutDob;
    private MaterialButton btnContinue;
    private MaterialButton btnCancel;
    int selectedYear = -1;
    int selectedMonth = -1;
    int selectedDay = -1;
    private boolean isUnlockAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        registerListeners();
    }

    private void registerListeners() {
        Objects.requireNonNull(inputLayoutDob.getEditText()).addTextChangedListener(new AutoAddTextWatcher(inputLayoutDob.getEditText(),
                "/",
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                },
                2, 4));
        btnContinue.setOnClickListener(v -> {
            String email = Objects.requireNonNull(inputLayoutEmail.getEditText()).getText().toString();
            String dob = Objects.requireNonNull(inputLayoutDob.getEditText()).getText().toString();

            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                hideError(inputLayoutEmail);
            } else {
                showError(inputLayoutEmail, getString(R.string.label_email_error));
            }

            if (dob.isEmpty() || dob.length() < 10) {
                showError(inputLayoutDob, getString(R.string.label_dob_error));
            } else {
                hideError(inputLayoutDob);
            }
        });
        btnCancel.setOnClickListener(v -> {
            /*DialogUtils.showDialog(MainActivity.this, "Hello....");
            DialogUtils.showDialog(MainActivity.this, "Hello....","With title");
            DialogUtils.showDialog(MainActivity.this, "Hello....","title","Yes","No",(dialog, which) -> {
                Toast.makeText(getApplicationContext(), "Clicked yes...", Toast.LENGTH_SHORT).show();
            }, (dialog, which) -> {
                Toast.makeText(getApplicationContext(), "Clicked no", Toast.LENGTH_SHORT).show();
            });*/

            /*AppDatePicker appDatePicker = new AppDatePicker();
            appDatePicker.setMaxDate(Calendar.getInstance());
            appDatePicker.setAppDatePickerListener((year, month, day) -> {
                Toast.makeText(getApplicationContext(), "date: " + (month+1) + "/" + day + "/" + year, Toast.LENGTH_LONG).show();
                selectedYear = year;
                selectedMonth = month;
                selectedDay = day;
            });
            if (selectedYear != -1) {
                Bundle bundle = new Bundle();
                bundle.putInt(AppDatePicker.SELECTED_YEAR, selectedYear);
                bundle.putInt(AppDatePicker.SELECTED_MONTH, selectedMonth);
                bundle.putInt(AppDatePicker.SELECTED_DAY, selectedDay);
                appDatePicker.setArguments(bundle);
            }
            appDatePicker.show(getSupportFragmentManager(), "AppDatePicker");*/
        });
    }

    private void showError(TextInputLayout inputLayout, String message) {
        inputLayout.setError(message);
        inputLayout.setErrorEnabled(true);
    }

    private void hideError(TextInputLayout inputLayout) {
        inputLayout.setError("");
        inputLayout.setErrorEnabled(false);
    }

    private void initViews() {
        inputLayoutEmail = findViewById(R.id.il_email);
        inputLayoutDob = findViewById(R.id.il_dob);
        btnContinue = findViewById(R.id.btn_continue);
        btnCancel = findViewById(R.id.btn_cancel);

        Group groupUnlockAccount = findViewById(R.id.groupUnlockAccount);
        if (isUnlockAccount){
            groupUnlockAccount.setVisibility(View.VISIBLE);
        } else {
            groupUnlockAccount.setVisibility(View.GONE);
        }
    }
}