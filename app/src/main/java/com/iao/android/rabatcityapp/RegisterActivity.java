package com.iao.android.rabatcityapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    EditText user;
    EditText email;
    Button register;
    EditText password;
    EditText passwordConfirmation;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //init UI
        setupUI();
        // init Listeners
        setupListeners();
    }

    private void setupListeners() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();
            }
        });
    }

    private void setupUI() {
        user = findViewById(R.id.user);
        email = findViewById(R.id.email2);
        password = findViewById(R.id.password2);
        passwordConfirmation = findViewById(R.id.confirm_password2);
        register = findViewById(R.id.sign_up);
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        boolean isValid = true;
        if (isEmpty(user)) {
            Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
            t.show();
            user.setError("User name is required!");
            isValid = false;
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
            isValid = false;

        }

        if(isEmpty(password)){
            password.setError("Password is required!");
            isValid = false;
        }else {
            if (password.getText().toString().length() < 4) {
                password.setError("Password must be at least 4 chars long!");
                isValid = false;
            }
        }

        if(isEmpty(passwordConfirmation)){
            password.setError("Please confirm your password!");
            isValid = false;
        }

        if (! password.getText().toString().equals(passwordConfirmation.getText().toString())) {
            passwordConfirmation.setError("Check your password !");
            isValid = false;
        }

        if (isValid) {
            String userValue = user.getText().toString();
            String passwordValue = password.getText().toString();
            String emailValue = email.getText().toString();

            mAuth = FirebaseAuth.getInstance();
            firestore = FirebaseFirestore.getInstance();
            mAuth.createUserWithEmailAndPassword(emailValue, passwordValue)
                    .addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful()) {
                                    userId = mAuth.getCurrentUser().getUid();
                                    DocumentReference newUser = firestore.collection("users").document(userId);
                                    Map<String, String> userMap = new HashMap<String, String>() {};
                                    userMap.put("userName",userValue);
                                    userMap.put("email",emailValue);

                                    newUser.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Intent myCallerIntent = getIntent();

                                                Bundle myBundle = new Bundle();
                                                myBundle.putString("email", emailValue );
                                                myBundle.putString("password", passwordValue );
                                                myCallerIntent.putExtras(myBundle);
                                                setResult(Activity.RESULT_OK,
                                                        myCallerIntent);

                                                Toast.makeText(getApplicationContext(),
                                                        "Account created !",
                                                        Toast.LENGTH_LONG)
                                                        .show();
                                                finish();

                                            }else{
                                                Toast.makeText(getApplicationContext(),
                                                        "Error while creating Account !",
                                                        Toast.LENGTH_LONG)
                                                        .show();
                                            }
                                        }
                                    });

                                }
                            });

            //we close this activity
            this.finish();
        } else {
            Toast t = Toast.makeText(this, "Information are Invalide !", Toast.LENGTH_SHORT);
            t.show();
        }

    }


}
