package com.scorsaro;

import java.sql.SQLException;
import java.util.ArrayList;

public class SignUpModel {


    private ControlFlow controlFlow;
    private DatabaseManager con;
    private String pwdCompared;
    private ValidInputChecker validInputChecker;

    public SignUpModel(ControlFlow controlFlow, DatabaseManager con, ValidInputChecker validInputChecker) {
        this.controlFlow = controlFlow;
        this.validInputChecker = validInputChecker;
        this.con = con;
    }

    public void SignUpCheck(String userToCheck, String pwdToCompare, String pwdToCompare2,
                            String emailToCheck, String colorChosen) throws SQLException {
        ArrayList<String> signUpErrors = new ArrayList<String>();
        int counterToSignUp = 0;

        counterToSignUp = checkUsername(userToCheck, signUpErrors, counterToSignUp);
        counterToSignUp = checkEmail(emailToCheck, signUpErrors, counterToSignUp);
        counterToSignUp = checkPwd(pwdToCompare, pwdToCompare2, signUpErrors, counterToSignUp);
        counterToSignUp = checkColor(colorChosen, signUpErrors, counterToSignUp);


        showErrors(signUpErrors);

        if (counterToSignUp == 4) {

            signUp(userToCheck, emailToCheck, colorChosen);
        }
    }

    public void signUp(String userToCheck, String emailToCheck, String colorChosen) throws SQLException {
        writeSignUp(userToCheck, emailToCheck, colorChosen, pwdCompared);
        controlFlow.signUp.lblSignUpMessages.setText("You signed Up");
        controlFlow.signUp.hideUI();
        controlFlow.loginView.showUI();
    }

    public void showErrors(ArrayList<String> signUpErrors) {
        StringBuffer sb = new StringBuffer();

        for (String str : signUpErrors) {
            sb.append(str);
            sb.append(" ");
        }
        String messageToShow = sb.toString();


        controlFlow.signUp.lblSignUpMessages.setText(messageToShow);
    }

    public int checkColor(String colorChosen, ArrayList<String> signUpErrors, int counterToSignUp) {
        if (checkColorChosen(colorChosen) == true) {
            controlFlow.signUp.fieldColorChange(8);
            counterToSignUp++;
        } else {
            signUpErrors.add("You must chose a color");
            controlFlow.signUp.fieldColorChange(7);
        }
        return counterToSignUp;
    }

    public int checkPwd(String pwdToCompare, String pwdToCompare2, ArrayList<String> signUpErrors, int counterToSignUp) {
        if (checkSamePwd(pwdToCompare, pwdToCompare2) == true) {
            if (validInputChecker.checkValidPwd(pwdCompared) == true) {
                System.out.println("pwd ok");
                controlFlow.signUp.fieldColorChange(6);
                counterToSignUp++;
            } else {
                signUpErrors.add("pwd must be alphanumeric");
                controlFlow.signUp.fieldColorChange(5);
            }
        } else {
            signUpErrors.add("pwd must be the same");
            controlFlow.signUp.fieldColorChange(5);
        }
        return counterToSignUp;
    }

    public int checkEmail(String emailToCheck, ArrayList<String> signUpErrors, int counterToSignUp) {
        if (validInputChecker.checkValidEmail(emailToCheck) == true) {
            System.out.println("email valid");
            controlFlow.signUp.fieldColorChange(4);
            counterToSignUp++;
        } else {
            System.out.println("email not valid");
            signUpErrors.add("email not valid");
            controlFlow.signUp.fieldColorChange(3);
        }
        return counterToSignUp;
    }

    public int checkUsername(String userToCheck, ArrayList<String> signUpErrors, int counterToSignUp) throws SQLException {
        if (validInputChecker.checkValidUsername(userToCheck) == false) {
            System.out.println("username is not valid");
            signUpErrors.add("username must be alphanumeric");
            controlFlow.signUp.fieldColorChange(1);
        } else {
            if (checkUsedUsername(userToCheck) == true) {
                System.out.println("username allready chosen");
                signUpErrors.add("Username allready chosen");
                controlFlow.signUp.fieldColorChange(1);
            } else {
                controlFlow.signUp.fieldColorChange(2);
                counterToSignUp++;
            }

        }
        return counterToSignUp;
    }

    private void writeSignUp(String userToCheck, String emailToCheck, String colorChosen, String pwdCompared) throws SQLException {
        con.insertData(userToCheck, pwdCompared, emailToCheck, colorChosen);
    }

    public boolean checkUsedUsername(String userToCheck) throws SQLException {

        return con.searchData("usr", "users", "usr", userToCheck);
    }

    public boolean checkSamePwd(String pwdToCompare, String pwdToCompare2) {
        if (pwdToCompare.equals(pwdToCompare2)) {
            pwdCompared = pwdToCompare;
            return true;
        } else {
            return false;
        }
    }

    public boolean checkColorChosen(String colorChosen) {
        return !colorChosen.equals("Chose a color");
    }

}



