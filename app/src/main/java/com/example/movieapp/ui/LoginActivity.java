package com.example.movieapp.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieapp.dbaccess.DBHelper;
import com.example.movieapp.R;

/**
 * Java code logic related to the activity_login.xml
 */
public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.accountPassword);
        final Button loginButton = findViewById(R.id.login);
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
