package com.example.firebaseloginapp;

public class UserData {
    public String userName;
    public String userEmail;
    public String userAge;

    public UserData(){}
    public UserData(String userName, String userEmail, String userAge) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserAge() {
        return userAge;
    }
}
