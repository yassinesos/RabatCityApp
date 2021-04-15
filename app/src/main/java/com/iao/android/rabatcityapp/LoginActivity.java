package com.iao.android.rabatcityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    TextView register;
    Button login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(EditText) findViewById(R.id.email_sign_up);
        password=(EditText) findViewById(R.id.password_sign_up);
        register = (TextView) findViewById(R.id.gotoregister);
        login = (Button) findViewById(R.id.sign_in);
        setupListeners();
    }

    private void setupListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("signIn clicked",v.toString());
                checkUsername();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    void checkUsername() {
        boolean isValid = true;
        if (isEmpty(email)) {
            email.setError("You must enter username to login!");
            isValid = false;
        } else {
            if (!isEmail(email)) {
                email.setError("Enter valid email!");
                isValid = false;
            }
        }

        if (isEmpty(password)) {
            password.setError("You must enter password to login!");
            isValid = false;
        } else {
            if (password.getText().toString().length() < 4) {
                password.setError("Password must be at least 4 chars long!");
                isValid = false;
            }
        }

        //check email and password
        //IMPORTANT: here should be call to backend or safer function for local check; For example simple check is cool
        //For example simple check is cool
        if (isValid) {
            String usernameValue = email.getText().toString();
            String passwordValue = password.getText().toString();
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(usernameValue, passwordValue)
                    .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(
                                        @NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),
                                                "Login successful !",
                                                Toast.LENGTH_LONG)
                                                .show();

                                        // hide the progress bar


                                        // if sign-in is successful
                                        // intent to home acti
                                        Intent intent= new Intent(LoginActivity.this,
                                                MainActivity.class);
                                        startActivity(intent);
                                    }

                                    else {

                                        // sign-in failed
                                        Toast.makeText(getApplicationContext(),
                                                "Login failed !",
                                                Toast.LENGTH_LONG)
                                                .show();

                                        // hide the progress bar
                                    }
                                }
                            });

            //we close this activity
            this.finish();
        } else {
            Toast t = Toast.makeText(this, "Wrong email or password!", Toast.LENGTH_SHORT);
            t.show();
        }

    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

}
