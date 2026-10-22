
package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class IngredientAdapter
        extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final List<Ingredient> ingredientList;
    private final OnIngredientActionListener listener;

    // Interface for Edit and Delete button clicks
    public interface OnIngredientActionListener {
        void onEdit(Ingredient ingredient);
        void onDelete(Ingredient ingredient);
    }

    public IngredientAdapter(
            List<Ingredient> ingredientList,
            OnIngredientActionListener listener) {

        this.ingredientList = ingredientList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder, int position) {

        Ingredient ingredient = ingredientList.get(position);

        holder.textIngredientName.setText(
                ingredient.getName()
        );

        holder.textIngredientQuantity.setText(
                String.format(
                        Locale.getDefault(),
                        "Quantity: %s %s",
                        ingredient.getQuantity(),
                        ingredient.getUnit()
                )
        );

        String expiryDate = ingredient.getExpiryDate();

        if (expiryDate == null || expiryDate.isEmpty()) {
            holder.textIngredientExpiry.setText(
                    "Expiry: Not specified"
            );
        } else {
            holder.textIngredientExpiry.setText(
                    "Expiry: " + expiryDate
            );
        }

        // Edit button
        holder.btnEditIngredient.setOnClickListener(v -> {
            listener.onEdit(ingredient);
        });

        // Delete button
        holder.btnDeleteIngredient.setOnClickListener(v -> {
            listener.onDelete(ingredient);
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class IngredientViewHolder
            extends RecyclerView.ViewHolder {

        TextView textIngredientName;
        TextView textIngredientQuantity;
        TextView textIngredientExpiry;

        Button btnEditIngredient;
        Button btnDeleteIngredient;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            textIngredientName = itemView.findViewById(
                    R.id.textIngredientName
            );

            textIngredientQuantity = itemView.findViewById(
                    R.id.textIngredientQuantity
            );

            textIngredientExpiry = itemView.findViewById(
                    R.id.textIngredientExpiry
            );

            btnEditIngredient = itemView.findViewById(
                    R.id.btnEditIngredient
            );

            btnDeleteIngredient = itemView.findViewById(
                    R.id.btnDeleteIngredient
            );
        }
    }
}
