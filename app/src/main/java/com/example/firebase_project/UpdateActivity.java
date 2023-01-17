package com.example.firebase_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebase_project.databinding.ActivityUpdateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateActivity extends AppCompatActivity {
ActivityUpdateBinding binding;
FirebaseUser currentUser ;
FirebaseFirestore db;
private Uri mainImageURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        binding.imgProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
//
//            }
//        });

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        binding.updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        binding.updateEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmail();
            }
        });

        binding.updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });


    }

//    private void updateProfile() {
//
//        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
//                .setDisplayName(binding.etName.getText().toString())
//                .setPhotoUri(mainImageURI)
//                .build();
//        currentUser.updateProfile(userProfileChangeRequest)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        if (task.isSuccessful()) {
//                            binding.etName.setText(currentUser.getDisplayName());
//                            binding.imgProfile.setImageURI(mainImageURI);
//                            currentUser.reload();
//                        } else {
//                            task.getException().printStackTrace();
//                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }


    private void getData(){
        db.collection("user").document(currentUser.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              if (task.isSuccessful()) {
                  User user = task.getResult().toObject(User.class);
                  String name = user.getName();
                  String image = user.getImage();
                  binding.etName.setText(name);
                  Glide.with(UpdateActivity.this).load(image)
                          .into(binding.imgProfile);

              }else {
                  String error = task.getException().getMessage();
                  Toast.makeText(UpdateActivity.this, error, Toast.LENGTH_SHORT).show();
              }
            }
        });
    }

    private void updatePassword() {
        AuthCredential authCredential = EmailAuthProvider.getCredential(currentUser.getEmail(), binding.oldPasswordEd.getText().toString());
        currentUser.reauthenticate(authCredential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            currentUser.updatePassword(binding.passwordEd.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                currentUser.reload();
                                                Toast.makeText(getApplicationContext(), "password Update for" + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                task.getException().printStackTrace();
                                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            task.getException().printStackTrace();
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void updateEmail() {
        AuthCredential authCredential = EmailAuthProvider.getCredential(currentUser.getEmail(), binding.currentPasswordEd.getText().toString());
        currentUser.reauthenticate(authCredential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            currentUser.updateEmail(binding.emailEd.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                currentUser.reload();
                                                Toast.makeText(getApplicationContext(), "Email Update for" + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                task.getException().printStackTrace();
                                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            task.getException().printStackTrace();
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 10 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            mainImageURI = data.getData();
//        }
//    }
}