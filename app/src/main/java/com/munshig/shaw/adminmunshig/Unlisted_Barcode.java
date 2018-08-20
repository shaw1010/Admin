package com.munshig.shaw.adminmunshig;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Unlisted_Barcode extends AppCompatActivity {

    TextView barcode_text;
    ListView barcode_list;
    Barcode_adapter_home  barcode_adapter;
    List<String> barcode_names;
    FirebaseFirestore bar_data;
    String barcode_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlisted__barcode);

    barcode_text = findViewById(R.id.barcode_text);
    barcode_list = findViewById(R.id.barcode_list);
    barcode_names = new ArrayList<>();
    bar_data = FirebaseFirestore.getInstance();

    bar_data.collection("unlisted_barcode_inventory").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful()){
                for(DocumentSnapshot doc : task.getResult()){
                    barcode_names.add(doc.getData().get("barcode").toString());
                }
                for(int i=0;i<barcode_names.size();i++){

                    for(int j=0; j<barcode_names.size();j++){

                        String k = barcode_names.get(i);
                        if(j!=i){
                            if(k.equals(barcode_names.get(j)))
                            {
                                barcode_names.remove(j);
                            }    }      }      }
                barcode_adapter =  new Barcode_adapter_home(Unlisted_Barcode.this, barcode_names);
                barcode_list.setAdapter(barcode_adapter);
            }
        }
    });


        barcode_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String code = barcode_names.get(position);
                Intent intent = new Intent(Unlisted_Barcode.this,Validation.class).putExtra("Baloo", code);
                startActivity(intent);
            }
        });

    }
}