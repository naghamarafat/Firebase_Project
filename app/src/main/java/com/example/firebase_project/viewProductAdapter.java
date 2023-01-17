package com.example.firebase_project;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebase_project.databinding.ItemProductBinding;
import com.example.firebase_project.databinding.ItemViewBinding;

import java.util.List;

public class viewProductAdapter extends RecyclerView.Adapter<viewProductAdapter.MyHolder> {
List<productModel> productModelArrayList;
Context context ;
Listener listener;
favListener favListener;
addListener addListener;
boolean disLike = true;

    public viewProductAdapter(List<productModel> productModelArrayList, Context context, Listener listener, com.example.firebase_project.favListener favListener, com.example.firebase_project.addListener addListener) {
        this.productModelArrayList = productModelArrayList;
        this.context = context;
        this.listener = listener;
        this.favListener = favListener;
        this.addListener = addListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewBinding binding = ItemViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
         productModel productModel = productModelArrayList.get(position);
         holder.name.setText(productModel.getName());
         holder.price.setText(String.valueOf(productModel.getPrice()));
         if (productModel.getImage()!=null && !productModel.getImage().isEmpty()){
             Glide.with(context).load(productModel.getImage()).into(holder.image);
             holder .itemView.setOnClickListener(new View.OnClickListener() {
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
             holder.deleteFrom.setOnClickListener(new View.OnClickListener() {
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
        ImageView image,disLike,deleteFrom;
        TextView name,price;

        public MyHolder(ItemViewBinding binding) {
            super(binding.getRoot());
            image = binding.imgProguct;
            name = binding.tvProductName;
            price = binding.tvProductPrice;
            disLike = binding.imgUnlove;
            deleteFrom = binding.imgDeletefrom;
        }
    }
}
