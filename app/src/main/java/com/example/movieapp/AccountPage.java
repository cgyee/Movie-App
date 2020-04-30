package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.ui.login.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;

public class AccountPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        final TextInputEditText accountEmail = findViewById(R.id.accountEmail);
        final TextInputEditText accountPassword = findViewById(R.id.accountPassword);
        final DBHelper dbHelper = new DBHelper(getApplicationContext());
        final Intent intent = getIntent();
        final String email = intent.getStringExtra("EMAIL");
        final Button confirm = findViewById(R.id.confirm);

        final Button deleteAccount = findViewById(R.id.deleteAccount);

        final Button changeInfo = findViewById(R.id.changeInfo);
        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.equals(accountEmail.getText().toString()) &&
                        dbHelper.validPassword(accountEmail.getText().toString(), accountPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Please enter in your new Email/Password" +
                                    " then select Confirm",
                            Toast.LENGTH_LONG).show();

                    changeInfo.setEnabled(false);
                    deleteAccount.setEnabled(false);

                    final String oldEmail = accountEmail.getText().toString();
                    final String oldPassword = accountPassword.getText().toString();
                    confirm.setEnabled(true);
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(email.equals(accountEmail.getText().toString()) || dbHelper.validEmail(accountEmail.getText().toString())) {
                                dbHelper.updateAccount(oldEmail, oldPassword, accountEmail.getText().toString(), accountPassword.getText().toString());

                                Toast.makeText(getApplicationContext(), "Account info changed!",
                                        Toast.LENGTH_SHORT).show();

                                confirm.setVisibility(View.INVISIBLE);
                                confirm.setEnabled(false);
                                changeInfo.setEnabled(true);
                                deleteAccount.setEnabled(true);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Please enter a valid new email account already exists",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter a valid Email/Password for this account",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.validPassword(accountEmail.getText().toString(), accountPassword.getText().toString())) {

                    Toast.makeText(getApplicationContext(), "Please select Confirm if you wish" +
                                    " to Continue", Toast.LENGTH_LONG).show();
                    confirm.setEnabled(true);
                    confirm.setVisibility(View.VISIBLE);
                    changeInfo.setEnabled(false);
                    deleteAccount.setEnabled(false);

                    final String tempEmail = accountEmail.getText().toString();
                    final String tempPassword = accountPassword.getText().toString();

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final Intent temp = new Intent(getApplicationContext(), LoginActivity.class);
                            dbHelper.deleteAccount(tempEmail, tempPassword);
                            startActivity(temp);
                        }
                    });
                }
            }
        });

    }
}
