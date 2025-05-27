package com.example.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        recyclerView = findViewById(R.id.recyclerView);
        itemList = new ArrayList<>();

        itemList.add(new Item("Shoes", 1, R.drawable.shoes, 129.99));
        itemList.add(new Item("Shirt", 1, R.drawable.shirt, 79.90));
        itemList.add(new Item("Dress", 1, R.drawable.dress, 199.50));
        itemList.add(new Item("Earrings", 1, R.drawable.earrings, 49.00));
        itemList.add(new Item("Satin Skirt", 1, R.drawable.satin_skirt, 159.99));
        itemList.add(new Item("Denim Skirt", 1, R.drawable.denim_skirt, 139.90));
        itemList.add(new Item("Basic Skirt", 1, R.drawable.skirt, 89.00));

        adapter = new ItemAdapter(itemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAddToCartClick(Item item, int quantity) {
        Intent intent = new Intent();
        intent.putExtra("itemName", item.getName());
        intent.putExtra("itemQuantity", quantity);
        intent.putExtra("itemPrice", item.getPrice());
        setResult(RESULT_OK, intent);
        finish();
    }
}