package com.munshig.shaw.adminmunshig;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity implements View.OnClickListener{

    TextView user_display;
    Spinner user_spinner;
    Button bills_home_button, barcode_home_button, speech_home_button;
    FirebaseFirestore fire_name;
    ArrayList<String> name;
    String selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Declaring Component Layouts
    user_display = findViewById(R.id.user_display);
    user_spinner = findViewById(R.id.user_spinner);
    bills_home_button = findViewById(R.id.bills_home_button);
    barcode_home_button = findViewById(R.id.barcode_home_button);
    speech_home_button = findViewById(R.id.speech_home_button);


    name = new ArrayList<>();
    fire_name = FirebaseFirestore.getInstance();

    fire_name.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful()){
                Log.i( "onComplete: ", String.valueOf(task.getResult().size()));
                for(DocumentSnapshot dp: task.getResult()){
                    name.add(dp.get("vendor_name").toString());
                }
            }
        }
    });

    //Click Listeners
    barcode_home_button.setOnClickListener(this);
    bills_home_button.setOnClickListener(this);
    speech_home_button.setOnClickListener(this);

//    loadspinnerdata(name);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        user_spinner = findViewById(R.id.user_spinner);
        // attaching data adapter to spinner
        user_spinner.setAdapter(dataAdapter);

        //  selection = user_spinner.getSelectedItem().toString();
        Log.i( "loadspinnerdata: ", user_spinner.toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.barcode_home_button:
              //  startActivity(new Intent(Home.this, Validate.class));

            case R.id.bills_home_button: {
                Toast.makeText(this, "Bharat Mata Ki", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, Bills.class));
                break;
            }
            case R.id.speech_home_button: {
                Toast.makeText(this, "Inqalab", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, Speech_items.class));
                break;
            }
        }
    }}
//    public void loadspinnerdata(ArrayList<String>  name){
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        user_spinner = findViewById(R.id.user_spinner);
//        // attaching data adapter to spinner
//        user_spinner.setAdapter(dataAdapter);
//
//      //  selection = user_spinner.getSelectedItem().toString();
//        Log.i( "loadspinnerdata: ", user_spinner.toString());
//    }
//
//}
