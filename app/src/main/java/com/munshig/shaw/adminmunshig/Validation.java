package com.munshig.shaw.adminmunshig;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Validation extends AppCompatActivity {

    TextView barcode_title;
    EditText name_value, price_value, packet_price_value;
    Button button_validate;
    String name_selected, price_selected, packet_selected;
    FirebaseFirestore add_data, new_data;
    List<Barcodes> bar_list;
    List<String> price_list, pp_list;
    List<Button> price_button, pp_button;
    int i = 0, j = 0;int k=0;
    ListView kirana_validate;
    Validation_adapter adapt;
    List<String> phone;
    String kirana_name=null;
    static Barcodes data_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);

        final Intent i = getIntent();
        final String barcode_selected = i.getStringExtra("Baloo");

        barcode_title = findViewById(R.id.barcode_title);
        name_value = findViewById(R.id.name_value);
        price_value = findViewById(R.id.price_value);
        packet_price_value = findViewById(R.id.packet_price_value);
        button_validate = findViewById(R.id.button_validate);
        add_data = FirebaseFirestore.getInstance();
        bar_list = new ArrayList<>();
        pp_list = new ArrayList<>();
        price_list = new ArrayList<>();
        pp_button = new ArrayList<>();
        price_button = new ArrayList<>();
        phone = new ArrayList<>();
        kirana_validate = findViewById(R.id.kirana_validate);
        new_data = FirebaseFirestore.getInstance();

        barcode_title.setText(barcode_selected);

        //Data Retrieval
        add_data.collection("unlisted_barcode_inventory")
                .whereEqualTo("barcode", barcode_selected)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    k=0;
                    for (final DocumentSnapshot doc : task.getResult()) {
                      //  if (doc.getData().get("barcode").toString().equals(barcode_selected)) {


//                            if(!phone.contains(doc.getData().get("phone").toString()))
//                            { phone.add(doc.getData().get("phone").toString());}


                        //   phone.add(doc.getData().get("phone").toString());



                            new_data.collection("Users").document(doc.getData().get("phone").toString().trim()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    kirana_name = task.getResult().getString("vendor_name");
                                    Log.i( "onComplete: ", kirana_name);

                                 
                                    final String barcode_1 = doc.getData().get("barcode").toString();
                                    final String name_1 = doc.getData().get("name").toString();
                                    final String price_1 = doc.getData().get("price").toString();
                                    final String pp_1 = doc.getData().get("packet_price").toString();

                                    data_bar = new Barcodes();
                                    data_bar.setName(name_1);
                                    data_bar.setBarcode(barcode_1);
                                    data_bar.setPrice(price_1);
                                    data_bar.setPacket_price(pp_1);
                                    data_bar.setPhone(doc.getData().get("phone").toString().trim());


                                    data_bar.setVendor_name(kirana_name);
                                    price_list.add(price_1);
                                    pp_list.add(pp_1);
                                    bar_list.add(data_bar);
                                    k++;
                                }
                            });
                   //     }
                    }
                }
                //Log.i( "onComplete:++++ ", String.valueOf(phone.size()) + " * " + phone.get(1));
                }
        });

        adapt = new Validation_adapter(this, bar_list, i);
        kirana_validate.setAdapter(adapt);

        //Listeners
        price_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                LinearLayout ll = (LinearLayout) findViewById(R.id.price_buttons);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.removeAllViews();
                Button myButton;
                if (b) {
                    for (int i = 0; i < price_list.size(); i++) {
                        myButton = new Button(Validation.this);
                        myButton.setText(price_list.get(i));
                        ll.addView(myButton, lp);
                        final Button finalMyButton = myButton;
                        myButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                            price_value.setText(finalMyButton.getText().toString());
                                        }
                                    });
                                }
                            } else {
                                ll.removeAllViews();
                            }
                        }
                    });

            packet_price_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {

                    LinearLayout ll = (LinearLayout) findViewById(R.id.price_buttons);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll.removeAllViews();
                    Button myButton;
                    if (b) {
                        for (int i = 0; i < pp_list.size(); i++) {
                            myButton = new Button(Validation.this);
                            myButton.setText(pp_list.get(i));
                            ll.addView(myButton, lp);

                            final Button finalMyButton = myButton;
                            myButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    packet_price_value.setText(finalMyButton.getText().toString());
                                }
                            });
                        }
                    } else {
                        ll.removeAllViews();
                    }
                }
            });


            button_validate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name_selected = name_value.getText().toString().trim();
                    price_selected = price_value.getText().toString().trim();
                    packet_selected = packet_price_value.getText().toString().trim();

                    Barcodes barrr = new Barcodes();
                    barrr.setName(name_selected);
                    barrr.setBarcode(barcode_selected);
                    barrr.setPrice(price_selected);
                    barrr.setPacket_price(packet_selected);

                    Log.i("onClick:++++++ ", String.valueOf(barrr));

                    add_data.document("Barcode_Inventory/" + barcode_selected).set(barrr).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Validation.this, "Barcode Successfully added!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }

    }
