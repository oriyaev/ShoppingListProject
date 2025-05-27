package com.example.shoppingcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        UserManager.init(this); // אל תשכחי את זה

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        EditText phone = findViewById(R.id.phone);
        Button registerButton = findViewById(R.id.register);

        registerButton.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String tel = phone.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean success = UserManager.registerUser(user, pass, tel);
                if (success) {
                    Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}