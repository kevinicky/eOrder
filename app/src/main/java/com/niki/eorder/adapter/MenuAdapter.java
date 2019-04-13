package com.niki.eorder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.niki.eorder.R;
import com.niki.eorder.model.Cart;
import com.niki.eorder.model.Menu;

import java.util.ArrayList;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private Context context;
    private ArrayList<Menu> menuList;
    private ArrayList<Cart> cartList;

    public MenuAdapter(Context context, ArrayList<Menu> menuList){
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_menu_card, viewGroup, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder menuViewHolder, final int i) {

        menuViewHolder.tvName.setText(menuList.get(i).getName());
        menuViewHolder.tvPrice.setText("IDR " + Integer.toString(menuList.get(i).getPrice()));
        menuViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart cart = new Cart();

                cart.setID(menuList.get(i).getID());
                cart.setName(menuList.get(i).getName());
                cart.setPrice(menuList.get(i).getPrice());
                cart.setQty(1);
                cartList.add(cart);

                Toast.makeText(context, "Success add this menu to your order list", Toast.LENGTH_SHORT).show();
                menuViewHolder.cardView.setBackgroundColor(Color.parseColor("#696969"));
                menuViewHolder.cardView.setClickable(false);
            }
        });

    }


    public ArrayList<Cart> getCartItem(){
        return cartList;
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvPrice;
        private CardView cardView;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_menu_name);
            tvPrice = itemView.findViewById(R.id.tv_menu_price);
            cardView = itemView.findViewById(R.id.cv_menu_card);

            cartList = new ArrayList<>();
        }
    }
}