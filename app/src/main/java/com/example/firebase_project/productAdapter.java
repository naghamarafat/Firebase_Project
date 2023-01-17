package com.example.firebase_project;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebase_project.databinding.ItemProductBinding;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.MyHolder> {
List<productModel> productModelArrayList;
Context context ;
Listener listener ;
favListener favListener;
addListener addListener;
boolean disLike = true;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;


    public productAdapter(List<productModel> productModelArrayList, Context context, Listener listener, favListener favListener, addListener addListener) {
        this.productModelArrayList = productModelArrayList;
        this.context = context;
        this.listener = listener;
        this.favListener = favListener;
        this.addListener = addListener;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
              sharedPreferences = context.getSharedPreferences("details",Context.MODE_PRIVATE);
              editor = sharedPreferences.edit();
              editor.putBoolean("like",true);
         productModel productModel = productModelArrayList.get(position);
        // holder.image.setImageResource(Integer.parseInt(String.valueOf(productModel.getImage())));
         holder.name.setText(productModel.getName());
         holder.category.setText(productModel.getCategory());
         holder.price.setText(String.valueOf(productModel.getPrice()));
         if (productModel.getImage()!=null && !productModel.getImage().isEmpty()){
             Glide.with(context).load(productModel.getImage()).into(holder.image);
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  listener.onClick(productModel,holder.getAdapterPosition());
              }
          });
          holder.disLike.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (disLike == false){
                      favListener.onFavourite(holder.getAdapterPosition(),productModel);
                         holder.disLike.setImageResource(R.drawable.ic_like);
                         disLike = true;
                  }else {
                      holder.disLike.setImageResource(R.drawable.ic_dislike);
                      disLike = false;
                  }
              }
          });
             holder.delete.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     addListener.onAdd(holder.getAdapterPosition(),productModel);
                 }
             });
         }
    }

    @Override
    public int getItemCount() {
        return productModelArrayList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView image,disLike,delete;
        TextView name,category,price;

        public MyHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            image = binding.imgItem;
            name = binding.tvName;
            category = binding.tvCategory;
            price = binding.tvPrice;
            disLike = binding.imgDisLike;
            delete = binding.imgDelete;
        }
    }
}
