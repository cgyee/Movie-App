package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieapp.ui.login.LoginActivity;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText email =  findViewById(R.id.accountEmail);
        final EditText password = findViewById(R.id.accountPassword);
        final Button registerButton = findViewById(R.id.register);
        final DBHelper db = new DBHelper(getApplicationContext());

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.validEmail(email.getText().toString())) {
                    db.insertNewUser(email.getText().toString(), password.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    Toast.makeText(getApplicationContext(), "Success please login", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Account already exist please log in", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
