package com.example.shoppingcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;
    private List<Item> itemListFull;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onAddToCartClick(Item item, int quantity);
    }

    public ItemAdapter(List<Item> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.itemListFull = new ArrayList<>(itemList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemImage.setImageResource(item.getImageResId());
        holder.itemPrice.setText(String.format("$%.2f", item.getPrice()));

        holder.addToCart.setOnClickListener(v -> {
            int quantity = 1;
            try {
                quantity = Integer.parseInt(holder.quantityInput.getText().toString());
            } catch (NumberFormatException ignored) {}
            listener.onAddToCartClick(item, quantity);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filterList(String query) {
        itemList.clear();
        if (query == null || query.trim().isEmpty()) {
            itemList.addAll(itemListFull);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Item item : itemListFull) {
                if (item.getName().toLowerCase().contains(lowerCaseQuery)) {
                    itemList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        ImageView itemImage;
        EditText quantityInput;
        Button addToCart;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.itemImage);
            quantityInput = itemView.findViewById(R.id.quantityInput);
            addToCart = itemView.findViewById(R.id.addToCart);
        }
    }
}