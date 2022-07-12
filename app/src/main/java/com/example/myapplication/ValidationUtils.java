package com.example.myapplication;

import android.content.Context;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ValidationUtils {
    private static final String PASSWORD_PATTERN = "^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[!@#$%^&*()])" +  // allows only specific special chars
            ".{10,}" +              //at least 10 characters
            "$";

    private static final String DOB_REGEX = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$"; // dob pattern DD/MM/YYYY

    public static ValidationErrorModel getPasswordValidationErrorModel(Context context, String password, String confPassword, String email) {
        boolean isValid = true;
        ValidationErrorModel errorModel = new ValidationErrorModel();

        if (password.isEmpty()) {
            errorModel.setInlineError(context.getString(R.string.error_plz_enter_password));
            if (confPassword.isEmpty()) {
                errorModel.setDialogMessage(context.getString(R.string.error_message_empty_passwords));
            } else {
                errorModel.setDialogMessage(context.getString(R.string.error_message_passwords_mismatch));
            }
            isValid = false;
        }

        if (password.contains("CVS") || password.contains("Specialty")) {
            errorModel.setInlineError(context.getString(R.string.error_password_cvs_specialty));
            if (confPassword.isEmpty()) {
                errorModel.setDialogMessage(errorModel.getInlineError() + ". " + context.getString(R.string.error_confirm_new_password));
            } else if (!password.equals(confPassword)) {
                errorModel.setDialogMessage(errorModel.getInlineError() + ". " + context.getString(R.string.error_make_sure_passwords_match));
            } else {
                errorModel.setDialogMessage(errorModel.getInlineError());
            }
            isValid = false;
        }

        if (password.contains(email)) {
            errorModel.setInlineError(context.getString(R.string.error_password_email_included));
            if (confPassword.isEmpty()) {
                errorModel.setDialogMessage(errorModel.getInlineError() + ". " + context.getString(R.string.error_confirm_new_password));
            } else if (!password.equals(confPassword)) {
                errorModel.setDialogMessage(errorModel.getInlineError() + ". " + context.getString(R.string.error_make_sure_passwords_match));
            } else {
                errorModel.setDialogMessage(errorModel.getInlineError());
            }
            isValid = false;
        }

        if (!password.matches("^(?=.*[!@#$%^&*()]).*$")) {
            errorModel.setInlineError(context.getString(R.string.error_password_special_chars));
            errorModel.setDialogMessage(errorModel.getInlineError());
            isValid = false;
        }

        if (!password.matches(PASSWORD_PATTERN)) {
            errorModel.setInlineError(context.getString(R.string.error_password_unsatisfactory));
            if (confPassword.isEmpty()) {
                errorModel.setDialogMessage(errorModel.getInlineError() + ". " + context.getString(R.string.error_confirm_new_password));
            } else if (!password.equals(confPassword)) {
                errorModel.setDialogMessage(errorModel.getInlineError() + ". " + context.getString(R.string.error_make_sure_passwords_match));
            } else {
                errorModel.setDialogMessage(errorModel.getInlineError());
            }
            isValid = false;
        }

        errorModel.setValid(isValid);

        return errorModel;
    }

    public static ValidationErrorModel getConfirmPasswordValidationErrorModel(Context context, String password, String confPassword) {
        boolean isValid = true;
        ValidationErrorModel errorModel = new ValidationErrorModel();

        if (confPassword.isEmpty()) {
            errorModel.setInlineError(context.getString(R.string.error_confirm_new_password));
            errorModel.setDialogMessage(errorModel.getInlineError());
            isValid = false;
        } else if (!confPassword.equals(password)) {
            errorModel.setInlineError(context.getString(R.string.error_make_sure_passwords_match));
            errorModel.setDialogMessage(errorModel.getInlineError());
            isValid = false;
        }

        errorModel.setValid(isValid);

        return errorModel;
    }

    public static ValidationErrorModel getUnlockUserValidationErrorModel(Context context, String email, String dob, String patientFirstName, String patientLastName) {
        boolean isValid = true;
        int errorCount = 0;
        ValidationErrorModel errorModel = new ValidationErrorModel();


        if (email.isEmpty()) {
            errorModel.setInlineError(context.getString(R.string.error_empty_email));
            errorCount++;
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorModel.setInlineError(context.getString(R.string.error_invalid_email));
            errorCount++;
            isValid = false;
        }

        if (dob.isEmpty()) {
            errorModel.setInlineError(context.getString(R.string.error_empty_dob));
            errorCount++;
            isValid = false;
        } else if (!dob.matches(DOB_REGEX)) {
            errorModel.setInlineError(context.getString(R.string.error_valid_dob));
            errorCount++;
            isValid = false;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(dob);
                Calendar now = Calendar.getInstance();
                Calendar yourDate = Calendar.getInstance();
                yourDate.setTimeInMillis(date.getTime());
                if (now.before(yourDate)) {
                    errorModel.setInlineError(context.getString(R.string.error_future_dob));
                    errorCount++;
                    isValid = false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (patientFirstName.isEmpty()) {
            errorModel.setInlineError(context.getString(R.string.error_patient_first_name));
            errorCount++;
            isValid = false;
        }

        if (patientLastName.isEmpty()) {
            errorModel.setInlineError(context.getString(R.string.error_patient_last_name));
            errorCount++;
            isValid = false;
        }

        if (!isValid) {
            errorModel.setDialogTitle(errorCount <= 1 ? errorModel.getInlineError() : context.getString(R.string.error_title_unlock_user, errorCount));
            errorModel.setDialogMessage(context.getString(R.string.error_message_unlock_user));
            errorModel.setDialogBtnTxt(context.getString(R.string.close));
        }
        errorModel.setValid(isValid);

        return errorModel;
    }

    public static ValidationErrorModel getForgotPasswordValidationErrorModel(Context context, String email, String dob) {
        boolean isValid = true;
        boolean isMissingDob = false;
        boolean isWrongDob = false;
        boolean isFutureDob = false;
        ValidationErrorModel errorModel = new ValidationErrorModel();

        if (dob.isEmpty()) {
            errorModel.setInlineError(context.getString(R.string.error_empty_dob));
            errorModel.setDialogMessage(errorModel.getInlineError());
            isMissingDob = true;
            isValid = false;
        } else if (!dob.matches(DOB_REGEX)) {
            errorModel.setInlineError(context.getString(R.string.error_valid_dob));
            errorModel.setDialogMessage(errorModel.getInlineError());
            isWrongDob = true;
            isValid = false;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date = sdf.parse(dob);
                Calendar now = Calendar.getInstance();
                Calendar yourDate = Calendar.getInstance();
                yourDate.setTimeInMillis(date.getTime());
                if (now.before(yourDate)) {
                    errorModel.setInlineError(context.getString(R.string.error_future_dob));
                    errorModel.setDialogMessage(errorModel.getInlineError());
                    isFutureDob = true;
                    isValid = false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (email.isEmpty()) {
            errorModel.setInlineError(context.getString(R.string.error_empty_email));
            if (isMissingDob) {
                errorModel.setDialogMessage(context.getString(R.string.error_empty_email_and_missing_dob));
            } else if (isWrongDob) {
                errorModel.setDialogMessage(context.getString(R.string.error_empty_email_and_wrong_dob));
            } else if (isFutureDob) {
                errorModel.setDialogMessage(context.getString(R.string.error_empty_email_and_future_dob));
            } else {
                errorModel.setDialogMessage(errorModel.getInlineError());
            }
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorModel.setInlineError(context.getString(R.string.error_invalid_email));
            if (isMissingDob) {
                errorModel.setDialogMessage(context.getString(R.string.error_invalid_email_and_missing_dob));
            } else if (isWrongDob) {
                errorModel.setDialogMessage(context.getString(R.string.error_invalid_email_and_wrong_dob));
            } else if (isFutureDob) {
                errorModel.setDialogMessage(context.getString(R.string.error_invalid_email_and_future_dob));
            } else {
                errorModel.setDialogMessage(errorModel.getInlineError());
            }
            isValid = false;
        }
        errorModel.setValid(isValid);

        return errorModel;
    }

}
