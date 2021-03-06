package com.munshig.shaw.adminmunshig;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class Home extends AppCompatActivity implements View.OnClickListener {

    TextView user_display;
    Spinner user_spinner;
    Button bills_home_button, barcode_home_button, speech_home_button, unlisted_barcodes_button;
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
        unlisted_barcodes_button = findViewById(R.id.unlisted_barcodes_button);

        name = new ArrayList<>();
        fire_name = FirebaseFirestore.getInstance();

        fire_name.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.i("onComplete: ", String.valueOf(task.getResult().size()));

                    for (DocumentSnapshot dp : task.getResult()) {
                        name.add(dp.get("vendor_name").toString());
                        Spinner user_spinner = (Spinner) findViewById(R.id.user_spinner);
                    }
                    name.add("global");
                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_spinner_item, name);
                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    user_spinner.setAdapter(areasAdapter);
                }
            }
        });

        //Click Listeners
        barcode_home_button.setOnClickListener(this);
        bills_home_button.setOnClickListener(this);
        speech_home_button.setOnClickListener(this);
        unlisted_barcodes_button.setOnClickListener(this);

        //Selection
        user_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selection = user_spinner.getSelectedItem().toString();
                if(selection.equals("global")){
                    View b = findViewById(R.id.unlisted_barcodes_button);
                    b.setVisibility(View.VISIBLE);
                }
                else{
                    View b = findViewById(R.id.unlisted_barcodes_button);
                    b.setVisibility(View.GONE);
                }
                user_display.setText(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.barcode_home_button:
                startActivity(new Intent(Home.this, Unlisted_Barcode.class));
                break;

            case R.id.unlisted_barcodes_button:
                startActivity(new Intent(Home.this, Unlisted_Barcode.class));
                break;

            case R.id.bills_home_button: {
                Toast.makeText(this, "Bharat Mata Ki", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Home.this, Bills.class).putExtra("name_selection", selection);
                startActivity(intent);
                break;
            }
            case R.id.speech_home_button: {
                Toast.makeText(this, "Inqalab", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, Speech_items.class));
                break;
            }
        }
    }
}

