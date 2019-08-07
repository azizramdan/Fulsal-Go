package com.example.futsalgo.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Patterns;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.futsalgo.Konfigurasi;
import com.example.futsalgo.R;
import com.example.futsalgo.data.LoginRepository;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }
    public static Integer id = 0;
    public static String nama = "";
    public static String email = "";
    public static String telp = "";

    public void login(String username, String password) {
        AndroidNetworking.post(Konfigurasi.USER)
                .addBodyParameter("method", "login")
                .addBodyParameter("email", username)
                .addBodyParameter("password", password)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.opt("status") == TRUE) {
                            try {
                                JSONObject data = response.getJSONObject("data");
                                loginResult.setValue(
                                        new LoginResult(
                                                TRUE,
                                                "",
                                                data.optInt("id"),
                                                data.optString("nama"),
                                                data.optString("email"),
                                                data.optString("telp")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loginResult.setValue(new LoginResult(FALSE, response.opt("msg").toString()));
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        loginResult.setValue(new LoginResult(FALSE,"Login failed"));
                    }
                });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 5;
    }
}
