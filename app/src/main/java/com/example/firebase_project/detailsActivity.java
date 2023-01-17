package com.example.firebase_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebase_project.databinding.ActivityDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class detailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fireStore = FirebaseFirestore.getInstance();


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String category = intent.getStringExtra("category");
        String image = intent.getStringExtra("image");
        int price = intent.getIntExtra("price",0);

        binding.tvNameProduct.setText(name);
        binding.tvCategoryProduct.setText(category);
        Glide.with(this).load(image).into(binding.imgProduct);
        binding.tvPriceProduct.setText(String.valueOf(price));

        switch (name){
            case "صابون الصبار الصحراوي":
                getAloeveraDetails();
                break;
            case "صابون اليانسون الطبيعي":
                getAniseDetails();
                break;
            case "صابون القهوة":
                getCoffeeDetails();
                break;
            case "صابون ورد الجوري":
                getFlowerDetails();
                break;
            case "صابون زهرة الليمون":
                getLemonDetails();
                break;
            case "صابون الرمان":
                getPomegranateDetails();
                break;
            case "صابون الياسمين":
                getYasmeenDetails();
                break;
            case "صابون ورق الغار":
                getGharDetails();
                break;
            case "صابون الخوزامي":
                getKhuzamiDetails();
                break;
            case "صابون زهرة النرجس":
                getNarcissusDetails();
                break;
            case "صابون ماء الورد":
                getRoseWaterDetails();
                break;
            case "صابون التين":
                getFigsDetails();
                break;
        }

    }
    private void getAloeveraDetails(){
        fireStore.collection("faceProduct")
                .document("aloevera").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getGharDetails(){
        fireStore.collection("bathProduct")
                .document("ghar").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getKhuzamiDetails(){
        fireStore.collection("bathProduct")
                .document("khuzami").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getNarcissusDetails(){
        fireStore.collection("bathProduct")
                .document("narcissus").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getRoseWaterDetails(){
        fireStore.collection("bathProduct")
                .document("roseWater").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getFlowerDetails(){
        fireStore.collection("bodyProduct")
                .document("flower").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getLemonDetails(){
        fireStore.collection("bodyProduct")
                .document("lemon").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getPomegranateDetails(){
        fireStore.collection("bodyProduct")
                .document("pomegranate").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getYasmeenDetails(){
        fireStore.collection("bodyProduct")
                .document("yasmeen").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getFigsDetails(){
        fireStore.collection("faceProduct")
                .document("Figs").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getAniseDetails(){
        fireStore.collection("faceProduct")
                .document("anise").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getCoffeeDetails(){
        fireStore.collection("faceProduct")
                .document("coffee").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String details = String.valueOf(task.getResult().get("details"));
                            binding.tvDetailsProduct.setText(details);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(detailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}