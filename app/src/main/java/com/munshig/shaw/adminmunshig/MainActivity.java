package com.munshig.shaw.adminmunshig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    EditText username_edittext;
    EditText password_edittext;
    Button login_button;
    Button signin_text;
    String username;
    String password;
    String saved_username,saved_password;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initializing the layout's Components
        username_edittext = findViewById(R.id.username_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        login_button = findViewById(R.id.login_button);
        signin_text = findViewById(R.id.signin_text);


        //Shared Preferences
        sharedPreferences = this.getSharedPreferences("Login Details", MODE_PRIVATE);


        //Authentication
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Getting Data
                username = username_edittext.getText().toString().trim();
                password = password_edittext.getText().toString().trim();

                //Shared Preference
                saved_username = sharedPreferences.getString("Username", "");
                saved_password = sharedPreferences.getString("Password", "");


                if (authentication(username, password, saved_username, saved_password)) {
                    startActivity(new Intent(MainActivity.this, Home.class));
                }
            }
        });


        //SignIN
        signin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin_text.setText("Welcome to Our Humble Abode");
                startActivity(new Intent(MainActivity.this, Signin.class));
            }
        });
    }

        public boolean authentication (String username, String password, String
        saved_username, String saved_password){



            if (!username.equals(saved_username) || !password.equals(saved_password) || password.length() <= 5 || username.length() <= 0) {
                if (username.length() <= 0) {
                    username_edittext.setError("Sapne mein ho kya!");
                    username_edittext.requestFocus();
                }

                if (password.length() <= 5) {
                    username_edittext.setError("Hagga!, atleast Try toh kar");
                    password_edittext.requestFocus();
                }


                if (!username.equals(saved_username) || !password.equals(saved_password)) {

                    Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                    username_edittext.clearComposingText();
                    username_edittext.setError("Invalid Username");
                    password_edittext.setError("Hagga, atleast Try toh kar");
                    username_edittext.requestFocus();

                }
            }
            else {
                return true;
            }

            return false;
        }
    }

