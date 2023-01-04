package com.example.repapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText logmail, logpass;
    private TextView logtext;
    private Button btnlog;
    private FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        logtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createacc();
            }
        });

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkUser();
            }
        });
    }

    private void checkUser()
    {
        String mail = logmail.getText().toString();
        String pass = logpass.getText().toString();

        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "Please Input Your Name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please Input Your Email Address", Toast.LENGTH_SHORT).show();
        }
        else{
            mauth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "LogIn Successful, Welcome Back", Toast.LENGTH_SHORT).show();
                        verifyemail();
                    }
                    else {
                        String message= task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error Occurred"+message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void verifyemail()
    {
        FirebaseUser user = mauth.getInstance().getCurrentUser();
        Boolean emailflag = user.isEmailVerified();

        if (emailflag) {
            finish();
            Toast.makeText(this, "Email verified, Welcome Back", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Home.class));
        } else {
            Toast.makeText(this, "Verify Your Email Address", Toast.LENGTH_SHORT).show();

        }
    }

    private void createacc()
    {
        Intent signintent = new Intent(MainActivity.this, SignUp.class);
        startActivity(signintent);
        finish();
    }
}