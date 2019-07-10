package com.example.futsalgo.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.util.Patterns;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.futsalgo.Konfigurasi;
import com.example.futsalgo.data.LoginRepository;
import com.example.futsalgo.data.Result;
import com.example.futsalgo.data.model.LoggedInUser;
import com.example.futsalgo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.support.constraint.Constraints.TAG;
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


    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        final Result<LoggedInUser> result = loginRepository.login(username, password);
//
//        if (result instanceof Result.Success) {
//            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
//            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
//        } else {
//            loginResult.setValue(new LoginResult(R.string.login_failed));
//        }
        AndroidNetworking.post(Konfigurasi.USER)
                .addBodyParameter("method", "login")
                .addBodyParameter("email", username)
                .addBodyParameter("password", password)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, "on response" + response.optString("status"));
                        if(response.opt("status") == TRUE) {
//                            id = response.opt("data").toString();
                            try {
                                JSONArray data = response.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject object = data.getJSONObject(i);
                                    id = object.getInt("id");
                                    nama = object.getString("nama");
                                    email = object.getString("email");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d(TAG, "on response" + e);
                            }
                            loginResult.setValue(new LoginResult(TRUE, ""));
                        } else {
                            loginResult.setValue(new LoginResult(FALSE, response.opt("msg").toString()));
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d(TAG, "onError: Failed" + error); //untuk log pada onerror
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

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 5;
    }
}
