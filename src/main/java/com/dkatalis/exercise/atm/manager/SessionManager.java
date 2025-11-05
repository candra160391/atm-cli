package com.dkatalis.exercise.atm.manager;

import com.dkatalis.exercise.atm.model.User;

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
