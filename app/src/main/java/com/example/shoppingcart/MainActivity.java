package com.example.shoppingcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView welcomeText;
    private Button resetButton;
    private LinearLayout listContainer;
    private TextView totalPriceView;
    private double totalPrice = 0.0;
    public static final int CATALOG_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserManager.init(this);

        if (!UserManager.isUserLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        welcomeText = findViewById(R.id.welcomeText);
        resetButton = findViewById(R.id.resetButton);
        listContainer = findViewById(R.id.listContainer);
        Button catalogButton = findViewById(R.id.catalogButton);
        Button logoutButton = findViewById(R.id.logoutButton);
        totalPriceView = findViewById(R.id.totalPrice); // במקום ליצור חדש

        String currentUser = UserManager.getCurrentUser();
        welcomeText.setText(getString(R.string.welcome_text, currentUser));

        resetButton.setOnClickListener(v -> {
            listContainer.removeAllViews();
            totalPrice = 0.0;
            updateTotalPrice();
        });

        logoutButton.setOnClickListener(v -> {
            UserManager.logoutUser();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        catalogButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CatalogActivity.class);
            startActivityForResult(intent, CATALOG_REQUEST);
        });

        updateTotalPrice();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CATALOG_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra("itemName");
            int quantity = data.getIntExtra("itemQuantity", 1);
            double price = data.getDoubleExtra("itemPrice", 0.0);
            addItemToCart(name, quantity, price);
        }
    }

    private void addItemToCart(String name, int quantity, double price) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setPadding(0, 16, 0, 16);
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        ImageView trash = new ImageView(this);
        trash.setImageResource(android.R.drawable.ic_menu_delete);
        trash.setColorFilter(0xFFFFFFFF);
        LinearLayout.LayoutParams trashParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        trashParams.setMargins(0, 0, 16, 0);
        trash.setLayoutParams(trashParams);

        TextView text = new TextView(this);
        double totalItemPrice = price * quantity;
        text.setText(String.format("%s x%d ($%.2f)", name, quantity, totalItemPrice));
        text.setTextColor(0xFFFFFFFF);
        text.setTextSize(18);
        text.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        text.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        trash.setOnClickListener(v -> {
            listContainer.removeView(itemLayout);
            totalPrice -= totalItemPrice;
            updateTotalPrice();
        });

        itemLayout.addView(trash);
        itemLayout.addView(text);
        listContainer.addView(itemLayout);

        totalPrice += totalItemPrice;
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        totalPriceView.setText(String.format("Total: $%.2f", totalPrice));
    }
}