package com.example.futsalgo.ui.login;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private boolean status;
    private String error;

    LoginResult(@Nullable boolean status, String error) {
        this.status = status;
        this.error = error;
    }
    String getError(){
        return error;
    }
    Boolean getStatus() {
        return status;
    }
}
