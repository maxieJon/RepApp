package com.example.repapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    private EditText signname, signmail, signpass, confirmpass,phneno;
    private Button btnsave;

    private FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signname=(EditText) findViewById(R.id.signname);
        signmail=(EditText) findViewById(R.id.signmail);
        signpass=(EditText) findViewById(R.id.signpass);
        confirmpass=(EditText) findViewById(R.id.confirmpass);
        phneno=(EditText) findViewById(R.id.phneno);
        btnsave=(Button) findViewById(R.id.btnsave);

        mauth=FirebaseAuth.getInstance();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupuser();
            }
        });
    }

    private void signupuser()
    {
        String name = signname.getText().toString();
        String mail = signmail.getText().toString();
        String pass = signpass.getText().toString();
        String confirm = confirmpass.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please Input Your Name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "Please Input Your Email Address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please Input Your Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(confirm)) {
            Toast.makeText(this, "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
        }
        else if (!pass.equals(confirm)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }

        else
        {
            mauth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(SignUp.this, "Information saved successfully, Just Verify Your Email", Toast.LENGTH_SHORT).show();
                        emailverifaction();
                    }else{
                        String message= task.getException().getMessage();
                        Toast.makeText(SignUp.this, "Error Occurred"+message, Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    private void emailverifaction()
    {
        FirebaseUser user= mauth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUp.this, "Email verification Successfully sent", Toast.LENGTH_SHORT).show();

                        finish();
                        startActivity(new Intent(SignUp.this, MainActivity.class));
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(SignUp.this, "Email verification not sent" + message, Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

}