package com.homeo.homeodocs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText mNAME,mEMAIL,mPASSWORD,mCONFIRMPASSWORD,mPHONENO;
    Button mSIGNUP;
    TextView mLOGIN;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNAME =findViewById(R.id.name);
        mEMAIL=findViewById(R.id.email);
        mPASSWORD=findViewById(R.id.password);
        mCONFIRMPASSWORD=findViewById(R.id.confirmpassword);
        mPHONENO=findViewById(R.id.phone);
        mLOGIN=findViewById(R.id.loginpage);
        mSIGNUP=findViewById(R.id.signup);


        fAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar2);

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mSIGNUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =mEMAIL.getText().toString().trim();
                String password=mPASSWORD.getText().toString().trim();
                String confirmpassword=mCONFIRMPASSWORD.getText().toString().trim();
                String phone=mPHONENO.getText().toString().trim();
                String name=mNAME.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEMAIL.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPASSWORD.setError("Password is required");
                    return;
                }
                if (password.length()< 8){
                    mPASSWORD.setError("Password must be 8 char long");
                    return;
                }

                if (TextUtils.isEmpty(confirmpassword)){
                    mCONFIRMPASSWORD.setError("Confirm password is required");
                    return;
                }
                if (confirmpassword.length()<8){
                    mCONFIRMPASSWORD.setError("Password must be same");
                    return;
                }

                if (TextUtils.isEmpty(phone)){
                    mPHONENO.setError("Phone no. is required");
                    return;
                }
                if (phone.length()<10){
                    mPHONENO.setError("Phone no. is incorrect");
                    return;
                }
                if (phone.length()>10){
                    mPHONENO.setError("Phone no. is incorrect");
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    mNAME.setError("Name field is empty");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User  Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else {
                            Toast.makeText(RegisterActivity.this, "Error!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
            }
        });

        mLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}