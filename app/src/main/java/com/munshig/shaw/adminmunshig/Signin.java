package com.munshig.shaw.adminmunshig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signin extends AppCompatActivity {

    EditText signin_username_edittext;
    EditText signin_password_edittext;
    EditText sign_retype_password_edit;
    Button signin_button;
    SharedPreferences sharedPreferences,editor;
    String name;
    String pass;
    String re_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signin_username_edittext = findViewById(R.id.signin_username_edittext);
        signin_password_edittext = findViewById(R.id.signin_password_edittext);
        sign_retype_password_edit = findViewById(R.id.sign_retype_password_edit);
        signin_button = findViewById(R.id.signin_button_submit);


        sharedPreferences = this.getSharedPreferences("Login Details",MODE_PRIVATE);


        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = signin_username_edittext.getText().toString();
                pass = signin_password_edittext.getText().toString();
                re_pass = sign_retype_password_edit.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username", name);
                editor.putString("Password", pass);
                editor.commit();


                if(let_me_check(name,pass,re_pass)){
                    Toast.makeText(Signin.this, "Finally!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Signin.this, MainActivity.class));
                }
            }
        });



    }

    public boolean let_me_check(String name, String pass, String re_pass){
        if(!pass.equals(re_pass) || name.isEmpty() || pass.isEmpty()){
            if(!pass.equals(re_pass)) {
                sign_retype_password_edit.setError("Bhai chand raat ko nikalta hai");
                sign_retype_password_edit.requestFocus();
            }
            if(name.isEmpty()){
                signin_username_edittext.setError("Bhai tu kaun hai? Jadoo?");
                sign_retype_password_edit.requestFocus();
            }
            if(pass.isEmpty()){
                signin_password_edittext.setError("Password Speed post karun!");
                signin_password_edittext.requestFocus();
            }
        }
        else{return true;}
        return  false;
    }

}
