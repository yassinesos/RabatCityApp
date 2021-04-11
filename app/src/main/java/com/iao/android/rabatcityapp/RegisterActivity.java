package com.iao.android.rabatcityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity  extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView next = (TextView) findViewById(R.id.gotologin);
        next.setOnClickListener(this);

        Button sign_in = (Button) findViewById(R.id.sign_up);
        sign_in.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
         EditText email2 = (EditText) findViewById(R.id.email2);
         EditText user = (EditText) findViewById(R.id.user);
         EditText password2 = (EditText) findViewById(R.id.password2);
         EditText confirm = (EditText) findViewById(R.id.confirm_password2);
        if(viewId == R.id.sign_up){

            if (email2.getText().equals("") || password2.getText().equals("")
                    || user.getText().equals("") || confirm.getText().equals("")) {
                Toast.makeText(getApplicationContext(), "entrer tout les " +
                        "champs", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password2.getText().equals(confirm.getText()) == false) {
                Toast.makeText(getApplicationContext(), "les deux mots de passes " +
                        "sont pas identiques", Toast.LENGTH_SHORT).show();
                confirm.setError("les deux mots de passes sont pas identiques");
                return;
            }
                Intent myIntent2 = new Intent(view.getContext(), LoginActivity.class);
                startActivity(myIntent2);
            return;
        }
        else if(viewId == R.id.gotoregister){
            Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(myIntent);
            return;
        } else{return;}

    }


}