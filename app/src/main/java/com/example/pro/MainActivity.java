package com.example.pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    EditText user_id;
    EditText password;
    Button btn_login;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_id = (EditText) findViewById(R.id.user_id);
        password = (EditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.login);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(user_id.getText().toString()) || TextUtils.isEmpty(password.getText().toString()))
                {
                    user_id.setError("Enter UserId and Password");
                }
                else {

                    final String user = user_id.getText().toString();
                    final String Pass = password.getText().toString();

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child(user).exists())
                            {
                                if (Pass.equals(dataSnapshot.child(user).child("Password").getValue().toString()))
                                {
                                    Toast.makeText(MainActivity.this, "Loged In Sucessfully..!!", Toast.LENGTH_SHORT).show();
                                   // Intent myIntent = new Intent(MainActivity.this, user_dashbord.class);
                                }
                                else {
                                    password.setError("Invelid Password");
                                }
                            }
                            else
                            {
                                user_id.setError("Enter Valid User");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }
}
