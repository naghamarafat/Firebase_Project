package com.example.firebase_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.firebase_project.databinding.ActivityFavoriteBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class favoriteActivity extends AppCompatActivity {
    ActivityFavoriteBinding binding;
    FirebaseFirestore firestore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

                getList();

    }

    private  void getList() {
       firestore = FirebaseFirestore.getInstance();
        // قراءة البيانات سواء لييست أو أيتم واحد
        firestore.collection("favoriteProduct")
                .get() // method to get the collection
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // راح ترجعلي لييست من المنتجات نوع كل أوبجيكت هو منتج
                            List<productModel> product = task.getResult().toObjects(productModel.class);
                            Log.d("productList", product.toString());
                            // هنا بنحطها في الريسايكل فيو
                            productAdapter adapter = new productAdapter(product, getBaseContext(), new Listener() {
                                @Override
                                public void onClick(productModel productModel, int position) {
                                    productModel productModel1 = product.get(position);

                                    String name = productModel1.getName();
                                    String category = productModel1.getCategory();
                                    String image = productModel1.getImage();
                                    int price = productModel1.getPrice();

                                    Intent intent = new Intent(getBaseContext(), detailsActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("category", category);
                                    intent.putExtra("image", image);
                                    intent.putExtra("price", price);
                                    startActivity(intent);
                                }
                            }, new favListener() {
                                @Override
                                public void onFavourite(int position, productModel productModel) {

                                }
                            }, new addListener() {
                                @Override
                                public void onAdd(int position, productModel productModel) {
                                    firestore.collection("favoriteProduct")
                                            .document()
                                            .delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(favoriteActivity.this, "deleted from your favorite list successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(favoriteActivity.this, error, Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });

                                }
                            });
                            binding.RVFav.setAdapter(adapter);
                            binding.RVFav.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL,false));

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(favoriteActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}