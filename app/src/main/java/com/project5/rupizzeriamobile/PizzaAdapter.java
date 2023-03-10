package com.project5.rupizzeriamobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.Serializable;
import java.util.ArrayList;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaHolder> {
    private Context context; //need the context to inflate the layout
    private ArrayList<Pizza> pizzas; //need the data binding to each row of RecyclerView

    public PizzaAdapter(Context context, ArrayList<Pizza> pizzas) {
        this.context = context;
        this.pizzas = pizzas;
    }

    /**
     * This method will inflate the row layout for the items in the RecyclerView
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public PizzaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the row layout for the items
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pizza_row, parent, false);

        return new PizzaHolder(view);
    }

    /**
     * Assign data values for each row according to their "position" (index) when the item becomes
     * visible on the screen.
     * @param holder the instance of ItemsHolder
     * @param position the index of the item in the list of item`s
     */
    @Override
    public void onBindViewHolder(@NonNull PizzaHolder holder, int position) {
        String special = "";
        Pizza cur = pizzas.get(position);
        if (cur instanceof Deluxe) {
            holder.pizza_image.setImageResource(R.drawable.deluxe);
            special = "Deluxe";
        }
        else if (cur instanceof BBQChicken) {
            holder.pizza_image.setImageResource(R.drawable.bbqchicken);
            special = "BBQChicken";
        }
        else if (cur instanceof Meatzza) {
            holder.pizza_image.setImageResource(R.drawable.meatzza);
            special = "Meatzza";
        }
        else {
            holder.pizza_image.setImageResource(R.drawable.byo);
            special = "BYO";
        }

        holder.pizza_name.setText(pizzas.get(position).getPizzaStyle() + " - " + special);
        holder.pizza_toppings.setText("[CUSTOM PIZZA - SELECT TOPPINGS]");
        String temp = pizzas.get(position).getToppings().toString();
        if (temp != "[]")
            holder.pizza_toppings.setText(temp);
        holder.pizza_crust.setText(pizzas.get(position).getCrust().toString());
    }

    /**
     * Get the number of items in the ArrayList.
     * @return the number of items in the list.
     */
    public int getItemCount() {
        return pizzas.size(); //number of MenuItem in the array list.
    }

    public ArrayList<Pizza> getPizzaList() {
        return pizzas;
    }

    public class PizzaHolder extends RecyclerView.ViewHolder {
        private ImageView pizza_image;
        private Button pizza_button;
        private ConstraintLayout parentLayout;
        private TextView pizza_name, pizza_crust, pizza_toppings;

        public PizzaHolder(@NonNull View PizzaView) {
            super(PizzaView);
            pizza_name = itemView.findViewById(R.id.pizza_name);
            pizza_crust = itemView.findViewById(R.id.pizza_crust);
            pizza_toppings = itemView.findViewById(R.id.pizza_toppings);
            pizza_image = itemView.findViewById(R.id.pizza_image);
            parentLayout = itemView.findViewById(R.id.row_layout);


            /* set onClickListener for the row layout,
             * clicking on a row will navigate to another Activity
             */
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PizzaSelectedActivity.class);
                    intent.putExtra("ITEM", pizza_name.getText().toString());
                    intent.putExtra("POSITION", getLayoutPosition());
                    intent.putExtra("PIZZA", (Serializable) getPizzaList().get(getLayoutPosition()));

                    PizzaView.getContext().startActivity(intent);
                }
            });
        }
    }
}
