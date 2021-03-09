package com.example.quipuxprueba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quipuxprueba.R;
import com.example.quipuxprueba.Services.AsynkTaskLogin;
import com.example.quipuxprueba.interfaces.ProgresoInterface;
import com.example.quipuxprueba.models.Login;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ProgresoInterface {

    EditText etUser, etPass;
    Button btSignIn;
    private ProgressDialog progressDialog;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindUi();
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        progressDialog = new ProgressDialog(this);
    }

    public void bindUi(){
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btSignIn = findViewById(R.id.btSignIn);
        btSignIn.setOnClickListener(this);
    }

    public void createJSON() {

        Login login = new Login();
        login.setUsername(etUser.getText().toString());
        login.setPassword(md5(etPass.getText().toString()));

        String jsonString = gson.toJson(login);
        System.out.println(jsonString);

        AsynkTaskLogin asynkTaskLogin = new AsynkTaskLogin(this,jsonString,this);
        asynkTaskLogin.execute();

    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void enviarvalidador() {
        boolean cancel = false;
        View focusView = null;

        String etUser1, etPass1;
        etUser1 = etUser.getText().toString();
        etPass1 = etPass.getText().toString();

        if (TextUtils.isEmpty(etUser1)) {

            etUser.setError("Este campo es requerido");
            focusView = etUser;
            cancel = true;
        } else if (TextUtils.isEmpty(etPass1)) {

            etPass.setError("Este campo es requerido");
            focusView = etPass;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            createJSON();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btSignIn){
            enviarvalidador();
        }
    }

    @Override
    public void showProgressDialog() {
        progressDialog.setMessage(getResources().getString(R.string.msgProgressDialog));
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void login() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}