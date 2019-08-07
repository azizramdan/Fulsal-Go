package com.example.futsalgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.futsalgo.ui.login.LoginActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrasiActivity extends AppCompatActivity {
    EditText etnama, etemail, ettelp, etpassword, etkonfirmasi;
    String nama, email, telp, password, konfirmasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        etnama = findViewById(R.id.nama);
        etemail = findViewById(R.id.email);
        ettelp = findViewById(R.id.telp);
        etpassword = findViewById(R.id.password);
        etkonfirmasi = findViewById(R.id.konfirmasi_password);

        AndroidNetworking.initialize(getApplicationContext());

        Button btnregistrasi = findViewById(R.id.registrasi);
        btnregistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etnama.getText().toString();
                email = etemail.getText().toString();
                telp = ettelp.getText().toString();
                password = etpassword.getText().toString();
                konfirmasi = etkonfirmasi.getText().toString();
                if(nama.length() == 0 || email.length() == 0 || telp.length() == 0 || password.length() == 0 || konfirmasi.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Data harus diisi semua!", Toast.LENGTH_LONG).show();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getApplicationContext(), "Format E-Mail salah!", Toast.LENGTH_LONG).show();
                } else if(password.length() != 0 && password.length() < 5) {
                    Toast.makeText(getApplicationContext(), "Password minimal 5 karakter!", Toast.LENGTH_LONG).show();
                } else if(!password.equals(konfirmasi)) {
                    Toast.makeText(getApplicationContext(), "Konfirmasi password salah!", Toast.LENGTH_LONG).show();
                } else {
                    registrasi();
                }
            }
        });
    }

    private void registrasi() {
        final ProgressDialog progressDialog = new ProgressDialog(RegistrasiActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.post(Konfigurasi.USER)
                .addBodyParameter("method", "registrasi")
                .addBodyParameter("nama", nama)
                .addBodyParameter("telp", telp)
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if(response.getBoolean("status")) {
                                Toast.makeText(getApplicationContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
                                finishAffinity();
                                startActivity(new Intent(RegistrasiActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Pendaftaran gagal!", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Pendaftaran gagal!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
