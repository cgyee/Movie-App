package com.example.movieapp.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.DBHelper;
import com.example.movieapp.R;
import com.example.movieapp.SearchPage;
import com.example.movieapp.SignUp;
import com.example.movieapp.User;

public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button registerButton = findViewById(R.id.register);
        final DBHelper db = new DBHelper(getApplicationContext());
        loginButton.setEnabled(true);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(db.validPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString())) {
                    Intent intent = new Intent(getApplicationContext(), SearchPage.class);
                    intent.putExtra("USER", usernameEditText.getText().toString());
                    Toast.makeText(getApplicationContext(), "Welcome " + usernameEditText.getText().toString(),Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Incorrect email/password", Toast.LENGTH_SHORT).show();
                }


            }
        });

        registerButton.setEnabled(true);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
    }
}
