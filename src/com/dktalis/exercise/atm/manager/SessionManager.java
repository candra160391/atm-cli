package com.dktalis.exercise.atm.manager;

import com.dktalis.exercise.atm.model.User;

public class SessionManager {

    public User currentUser;

    public void createSession(User user){
        currentUser = user;
    }

    public void clearSession(){
        currentUser = null;
    }

    public User getCurrentUser(){
        return currentUser;
    }

}
