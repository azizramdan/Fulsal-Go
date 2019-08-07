package com.example.futsalgo.ui.login;

import android.support.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private boolean status;
    private String error, nama, email, telp;
    private Integer id;

    LoginResult(boolean status, String error) {
        this.status = status;
        this.error = error;
    }

    LoginResult(boolean status, String error, Integer id, String nama, String email, String telp) {
        this.status = status;
        this.error = error;
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.telp = telp;
    }
    String getError(){
        return error;
    }
    Boolean getStatus() {
        return status;
    }
    Integer getId() {
        return  id;
    }
    String getNama() {
        return  nama;
    }
    String getEmail() {
        return  email;
    }
    String getTelp() {
        return  telp;
    }
}
