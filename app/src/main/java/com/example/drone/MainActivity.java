package com.example.drone;

import static android.location.Address.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private Button move;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            move=findViewById(R.id.Move);
            move.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(intent);
                }
            });

        DatabaseReference reference = FirebaseDatabase.getInstance("Link of Firebase").getReference().child("DHT");
        DatabaseReference reference1 = FirebaseDatabase.getInstance("Link of Firebase").getReference().child("Flame sensor");


        TextView t1 =findViewById(R.id.textView);
        TextView t2 =findViewById(R.id.textView2);
        TextView t3 =findViewById(R.id.textView3);



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HashMap<String, Object> data;
                data = (HashMap<String, Object>) snapshot.getValue();

                if (snapshot.exists()){
                    t1.setText(data != null ? Objects.requireNonNull(data.get("temperature")).toString() : null);
                    t2.setText(data != null ? Objects.requireNonNull(data.get("humidity")).toString() : null);

                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }
        );



        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> data2;
                data2 = (HashMap<String, Object>) snapshot.getValue();

                if (snapshot.exists()){
                    String s = data2 != null ? Objects.requireNonNull(data2.get("Status")).toString() : null;

                    if (Objects.equals(s, "0"))
                    {
                        t3.setText("No Fire");
                    }
                    else
                    {
                        t3.setText("Fire Detected");
                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }




        });

    }
}