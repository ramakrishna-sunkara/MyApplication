package com.example.myapplication;

public class ValidationErrorModel {
    private boolean isValid;
    private String inlineError;
    private String dialogTitle;
    private String dialogMessage;
    private String dialogBtnTxt;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getInlineError() {
        return inlineError;
    }

    public void setInlineError(String inlineError) {
        this.inlineError = inlineError;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public String getDialogMessage() {
        return dialogMessage;
    }

    public void setDialogMessage(String dialogMessage) {
        this.dialogMessage = dialogMessage;
    }

    public String getDialogBtnTxt() {
        return dialogBtnTxt;
    }

    public void setDialogBtnTxt(String dialogBtnTxt) {
        this.dialogBtnTxt = dialogBtnTxt;
    }
}
