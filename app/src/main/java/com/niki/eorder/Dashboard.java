package com.niki.eorder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.niki.eorder.model.Users;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.annotation.Nullable;

public class Dashboard extends AppCompatActivity{
    private Button btnSignOut;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private TextView tvUserEmail, tvUserName, tvUserEbalance;
    private CardView cvOrderMenu, cvProfile;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String name, email;
    private long eBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // disable action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnSignOut = findViewById(R.id.btn_sign_out);

        cvOrderMenu = findViewById(R.id.cv_order_menu);
        cvProfile = findViewById(R.id.cv_profile);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(Dashboard.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        cvOrderMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, QrCodeScanner.class);
                startActivity(intent);
            }
        });

        cvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            DocumentReference ref = db.collection("users").document(firebaseAuth.getUid());
            tvUserEmail = findViewById(R.id.tv_user_email);
            tvUserName = findViewById(R.id.tv_user_name);
            tvUserEbalance = findViewById(R.id.tv_user_ebalance);

            ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d("LOGGER", "name " + documentSnapshot.getString("name"));
                    Log.d("LOGGER", "email " + documentSnapshot.getString("email"));
                    Log.d("LOGGER", "eBalance " + documentSnapshot.getLong("eBalance"));

                    name = documentSnapshot.getString("name");
                    email = documentSnapshot.getString("email");
                    eBalance = documentSnapshot.getLong("eBalance");

                    // set eBalance to IDR format

                    Utility util = new Utility();

                    tvUserEmail.setText(email);
                    tvUserName.setText("Welcome back, " + name);
                    tvUserEbalance.setText("Your eBalance : " + util.toIDR(eBalance));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Dashboard.this, "Error getting data, please try again", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        else {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            finish();
        }

    }

}
