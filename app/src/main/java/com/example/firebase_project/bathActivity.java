package com.example.firebase_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.firebase_project.databinding.ActivityBathBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class bathActivity extends AppCompatActivity {
    ActivityBathBinding binding;
    List<productModel> product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBathBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String title = intent.getStringExtra("bathTitle");
        binding.tvTitlebath.setText(title);

        getListOfBath();

    }

    private void getListOfBath() {
        FirebaseFirestore firebaseFirestoreDB = FirebaseFirestore.getInstance();

        // قراءة البيانات سواء لييست أو أيتم واحد
        firebaseFirestoreDB.collection("bathProduct")
                .get() // method to get the collection
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            binding.progressBar.setVisibility(View.GONE);
                            // راح ترجعلي لييست من المنتجات نوع كل أوبجيكت هو منتج
                            product = task.getResult().toObjects(productModel.class);
                            Log.d("productList", product.toString());
                            // هنا بنحطها في الريسايكل فيو
                            viewProductAdapter adapter = new viewProductAdapter(product, getBaseContext(), new Listener() {
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
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    if (user == null){
                                        Toast.makeText(bathActivity.this, "you are not logged in", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        productModel productModel1 = product.get(position);

                                        String name = productModel1.getName();
                                        String category = productModel1.getCategory();
                                        String image = productModel1.getImage();
                                        int price = productModel1.getPrice();

                                        productModel1.setName(name);
                                        productModel1.setCategory(category);
                                        productModel1.setImage(image);
                                        productModel1.setPrice(price);

                                        firebaseFirestoreDB.collection("favoriteProduct")
                                                .add(productModel1) // تاخذ هاش ماب أو اوبجيكت
                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if (task.isSuccessful()){
                                                            startActivity(new Intent(getBaseContext(),favoriteActivity.class));
                                                            Toast.makeText(bathActivity.this, "added to list Successfully", Toast.LENGTH_SHORT).show();
                                                        }else {
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(bathActivity.this, error, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }

                                }
                            }, new addListener() {
                                @Override
                                public void onAdd(int position, productModel productModel) {

                                }
                            });
                            binding.RVBathView.setAdapter(adapter);
                            binding.RVBathView.setHasFixedSize(true);
                            binding.RVBathView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
                            // binding.RVView.setLayoutManager(new LinearLayoutManager(getBaseContext(),RecyclerView.VERTICAL,false));

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}