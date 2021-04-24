package com.iao.android.rabatcityapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    TextView register;
    Button login;
    ImageView background;
    private FirebaseAuth mAuth;
    RelativeLayout relativeLayout;
    CheckBox rememberme;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init des UI
        setupUI();
        //init des listeners
        setupListeners();
    }

    private void setupUI() {
        email=(EditText) findViewById(R.id.email_sign_up);
        password=(EditText) findViewById(R.id.password_sign_up);
        register = (TextView) findViewById(R.id.gotoregister);
        login = (Button) findViewById(R.id.sign_in);
        background = findViewById(R.id.loginBackground);
        rememberme = findViewById(R.id.rememberme);
        Glide.with(background.getContext())
                .load(R.drawable.hassanblack)
                .centerCrop()
                .into(background);
        Animation anim_from_button = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);
        relativeLayout = findViewById(R.id.loginLayout);
        relativeLayout.setAnimation(anim_from_button);

    }

    private void setupListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUsername();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(i, 101);
            }
        });

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences preferences2 = getSharedPreferences("login", MODE_PRIVATE);

        String checkbox = preferences.getString("remember", "");
        String loginFirstTime = preferences2.getString("first", "");

        if(checkbox.equals("true") && loginFirstTime.equals("true")){
            Intent intent= new Intent(LoginActivity.this,
                    MainActivity.class);
            startActivity(intent);
        }
        rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                }else{
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
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
                                        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("first", "true");
                                        editor.apply();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if ((requestCode == 101 ) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResultBundle = data.getExtras();
                String emailIntent = myResultBundle.getString("email");
                String passwordIntent = myResultBundle.getString("password");
                email.setText(emailIntent);
                password.setText(passwordIntent);
            }

        }
        catch (Exception e) {
//            txtResult.setText("Problems - " + requestCode + " " + resultCode);
            Toast.makeText(getApplicationContext(),
                    " creation error" + resultCode + " - " + requestCode,
                    Toast.LENGTH_LONG)
                    .show();
            e.getCause();
        }

    }
}
