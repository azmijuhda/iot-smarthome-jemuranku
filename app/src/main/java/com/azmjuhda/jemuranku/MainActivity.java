package com.azmjuhda.jemuranku;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jemuranKu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference action = database.getReference("action");
        final DatabaseReference manual = database.getReference("manual");
        final DatabaseReference refSuhu = database.getReference("suhu");
        final DatabaseReference refKelembapan = database.getReference("kelembapan");
        final DatabaseReference refAtap = database.getReference("atap");
        final DatabaseReference refLangit = database.getReference("langit");

        final Button btBukaAtap, btTutupAtap;
        final TextView Tsuhu, Tkelembapan, Tatap, Tlangit, Tmanual;
        final Switch swManual;
        final LinearLayout lyManual;

        Tsuhu = findViewById(R.id.Tsuhu);
        Tkelembapan = findViewById(R.id.Tkelembapan);
        Tatap = findViewById(R.id.TkondisiAtap);
        Tlangit = findViewById(R.id.TkondisiLangit);
        Tmanual = findViewById(R.id.TManual);
        btBukaAtap = findViewById(R.id.btBukaAtap);
        btTutupAtap = findViewById(R.id.btTutupAtap);
        swManual = findViewById(R.id.swManual);
        lyManual = findViewById(R.id.lyManual);

        manual.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean value = dataSnapshot.getValue(Boolean.class);
                if(value){
                    swManual.setChecked(false);
                    lyManual.setVisibility(View.VISIBLE);
                } else {
                    swManual.setChecked(true);
                    lyManual.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        swManual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    manual.setValue(false);
                    Tmanual.setText("Atap Otomatis");
                } else {
                    manual.setValue(true);
                    Tmanual.setText("Atap Otomatis");
                }
            }
        });

        btBukaAtap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.setValue(true);
            }
        });

        btTutupAtap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.setValue(false);
            }
        });


        // Write a message to the database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("LED_STATUS");
        //myRef.setValue("Hello, World!");


        // Read from the database
        refSuhu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float value = dataSnapshot.getValue(Float.class);
                Tsuhu.setText(""+value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        refKelembapan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float value = dataSnapshot.getValue(Float.class);
                Tkelembapan.setText("" + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        refAtap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean value = dataSnapshot.getValue(Boolean.class);
                if (value){
                    Tatap.setText("Atap Terbuka");
                    btBukaAtap.setEnabled(false);
                    btBukaAtap.setBackgroundColor(Color.parseColor("#95a5a6"));
                    btTutupAtap.setEnabled(true);
                    btTutupAtap.setBackgroundColor(Color.parseColor("#2980b9"));
                } else {
                    Tatap.setText("Atap Tertutup");
                    btBukaAtap.setEnabled(true);
                    btBukaAtap.setBackgroundColor(Color.parseColor("#2980b9"));
                    btTutupAtap.setEnabled(false);
                    btTutupAtap.setBackgroundColor(Color.parseColor("#95a5a6"));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        refLangit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                if (value == 1){
                    Tlangit.setText("Sedang Hujan");
                } else if (value == 2) {
                    Tlangit.setText("Mendung dengan Suhu Panas");
                } else if (value == 3) {
                    Tlangit.setText("Mendung dengan Suhu Dingin");
                } else if (value == 4) {
                    Tlangit.setText("Terang Benerang");
                } else {
                    Tlangit.setText("ERROR!!");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
