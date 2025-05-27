package com.example.shoppingcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserManager.init(this);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login);
        Button signupButton = findViewById(R.id.signup);

        loginButton.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (UserManager.loginUser(user, pass)) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });
    }
}