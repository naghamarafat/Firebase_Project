package com.example.firebase_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.firebase_project.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    FirebaseFirestore firebaseFirestoreDB;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestoreDB = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        // لجعل التولبار الي صممتو هو التولبار بدل الافتراضي بستدعى الدالة التالية:
        setSupportActionBar(binding.toolbar);

           binding.tvBathFresheners.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String title = binding.tvBathFresheners.getText().toString();
                   Intent intent = new Intent(getBaseContext(),bathActivity.class);
                   intent.putExtra("bathTitle",title);
                   startActivity(intent);
               }
           });
           binding.tvBody.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String title = binding.tvBody.getText().toString();
                   Intent intent = new Intent(getBaseContext(),bodyActivity.class);
                   intent.putExtra("bodyTitle",title);

                   startActivity(intent);

               }
           });
           binding.tvFace.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   String title = binding.tvFace.getText().toString();
                   Intent intent = new Intent(getBaseContext(),faceActivity.class);
                   intent.putExtra("faceTitle",title);
                   startActivity(intent);

               }
           });

        getList();

    }

    //دالة لانشاء الmenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //دالة بتعمل inflat للمنيو و بتركبه على toolbar الي صممته
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    //دالة لعمل action على ال menu أو عناصره
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_fav:
                Intent intent = new Intent(getBaseContext(),favoriteActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_update:
                Intent intent1 = new Intent(getBaseContext(),UpdateActivity.class);
                startActivity(intent1);
                return true;

            case R.id.menu_logout:
                // العملية الي راح يعملها لما يضغط على كلمة تسجيل خروج
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getBaseContext(),LoginActivity.class));
                                    Toast.makeText(HomeActivity.this, "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(HomeActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
        }
        return false;
    }
       // قراءة و جلب البيانات من الفييرستور
    private  void getList() {
        //  قراءة البيانات و جلبها من الفير ستور
        firebaseFirestoreDB.collection("newProduct")
                .get() // method to get the collection
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            binding.progressBar.setVisibility(View.GONE);
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
//                                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                                                if (firebaseUser == null){
                                                                    Toast.makeText(HomeActivity.this, "you are not logged in", Toast.LENGTH_SHORT).show();
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
                                                                                            Toast.makeText(HomeActivity.this, "added to list Successfully", Toast.LENGTH_SHORT).show();
                                                                                        }else {
                                                                                            String error = task.getException().getMessage();
                                                                                            Toast.makeText(HomeActivity.this, error, Toast.LENGTH_SHORT).show();
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
                            binding.RV.setAdapter(adapter);
                            binding.RV.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL,false));

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(HomeActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
