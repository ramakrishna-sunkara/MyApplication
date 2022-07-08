package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputLayout ilNewPassword;
    private TextInputLayout ilConfirmPassword;
    private MaterialButton btnSavePassword;
    private MaterialButton btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initViews();
        registerListeners();
    }

    private void registerListeners() {
        ilNewPassword.getEditText().setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                hideError(ilNewPassword);
            }
        });
        ilConfirmPassword.getEditText().setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                hideError(ilConfirmPassword);
            }
        });
        ilNewPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hideError(ilNewPassword);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ilConfirmPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hideError(ilConfirmPassword);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnSavePassword.setOnClickListener(v -> {
            String newPassword = Objects.requireNonNull(ilNewPassword.getEditText()).getText().toString();
            String confirmPassword = Objects.requireNonNull(ilConfirmPassword.getEditText()).getText().toString();
            if (!isValidInputs(newPassword, confirmPassword)) {
                return;
            }
            Toast.makeText(ResetPasswordActivity.this, "Proceed with api call...", Toast.LENGTH_SHORT).show();
        });
        btnCancel.setOnClickListener(v -> {
        });
    }

    private boolean isValidInputs(String newPassword, String confirmPassword) {
        boolean isValid = true;
        if (!ValidationUtils.isValidPassword(newPassword)) {
            showError(ilNewPassword, getString(R.string.label_password_error));
            isValid = false;
        } else {
            hideError(ilNewPassword);
        }

        if (!confirmPassword.equals(newPassword)) {
            showError(ilConfirmPassword, getString(R.string.label_password_match_error));
            isValid = false;
        } else {
            hideError(ilConfirmPassword);
        }
        return isValid;
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
        ilNewPassword = findViewById(R.id.il_new_password);
        ilConfirmPassword = findViewById(R.id.il_confirm_password);
        btnSavePassword = findViewById(R.id.btn_save_password);
        btnCancel = findViewById(R.id.btn_cancel);
    }
}