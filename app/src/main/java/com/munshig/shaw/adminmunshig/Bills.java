package com.munshig.shaw.adminmunshig;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Bills extends AppCompatActivity{

    TextView total_bills, last_bill, calender_bill, bill_by_date;
    TextView  count_date;
    EditText calenderview;
    FirebaseFirestore firestore;
    String name;
    Editable date_selected;
    String date_1;
    String date_2;
    long timeInMilliseconds_start, timeInMilliseconds_end;
    long date_start, date_end;

    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        Toast.makeText(this, "Jai", Toast.LENGTH_SHORT).show();

        Intent i = getIntent();
        name = i.getStringExtra("name_selection").toString();
        Log.i( "onCreate:+++++ ", name);

        total_bills = findViewById(R.id.total_bills);
        last_bill = findViewById(R.id.last_bill);
        calender_bill = findViewById(R.id.calender_bill);
        bill_by_date = findViewById(R.id.bill_by_date);

        calenderview = findViewById(R.id.calenderview);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                calenderview.setText(sdf.format(myCalendar.getTime()));
                date_selected = calenderview.getText();
                date_1 = String.valueOf((date_selected.append(" 00:00:00")));
                date_selected.delete(10, 19);
                date_2 = String.valueOf(date_selected.append(" 23:59:59"));
                date_selected.delete(10, 19);
                Log.i("onDateSet: ", date_selected.toString());
                Log.i("onDateSet: ", date_1.toString());
                Log.i("onDateSet: ", date_2.toString());

                try {
                    timeInMilliseconds_start = dateConvertor(date_1);
                    timeInMilliseconds_end = dateConvertor(date_2);
                } catch (ParseException e) {
                    e.printStackTrace();
                Log.i("errorintrycatch", e.toString());

                }
                Log.i("onDateSet: " , String.valueOf(timeInMilliseconds_start));
            }
        };

        calenderview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Bills.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        firestore = FirebaseFirestore.getInstance();

        firestore.collection("Users").whereEqualTo("vendor_name", name).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(final QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                    path = doc.getDocument().getId();


                    firestore.collection("Users/"+path+"/Bills").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                           total_bills.append(" "+ String.valueOf(task.getResult().size()));

                           String max = String.valueOf(0);
                           int count=0;

                           for(DocumentSnapshot doc: task.getResult()){
                               if(doc.getId().compareTo(max) > 0){
                                   max = doc.getId();
                               }

                               //Timestamp vaala part
                               if(doc.getId().compareTo(String.valueOf(timeInMilliseconds_start)) >=0 && doc.getId().compareTo(String.valueOf(timeInMilliseconds_end))<0){count++;}
                           }
                            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                            cal.setTimeInMillis(Long.parseLong(max));
                            String date = DateFormat.format("dd-MM-yyyy", cal).toString();
                            last_bill.append(": " + date);
                            bill_by_date.append(": " + count);
                        }
                    });
                }
            }
        });


    }

    public long dateConvertor(String date) throws ParseException {
        Date currentpastDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            Log.i("formatter", formatter.toString());

            currentpastDate = (Date) formatter.parse(date);
            Log.i("currentpastdate", currentpastDate.toString());


        } catch (Exception E) {
            E.printStackTrace();
        }
        return currentpastDate.getTime();
    }


}
