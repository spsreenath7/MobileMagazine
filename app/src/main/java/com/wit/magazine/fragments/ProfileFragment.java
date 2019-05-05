package com.wit.magazine.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wit.magazine.R;
import com.wit.magazine.activities.HomeActivity;
import com.wit.magazine.activities.MainActivity;
import com.wit.magazine.models.UserProfile;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.wit.magazine.activities.HomeActivity.app;

public class ProfileFragment extends Fragment {

    private static final int PICK_IMAGE = 101;
    View v;
    ImageView profilePic;
    EditText username;
    TextView email;
    Button save;

    Uri imageUri;
    String profilePicURL;
    FirebaseUser firebaseUser;
    HomeActivity activity;

    public ProfileFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (HomeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        email =(TextView)v.findViewById(R.id.textViewEmail);
        username = (EditText)v.findViewById(R.id.editDisplayname);
        profilePic = (ImageView)v.findViewById(R.id.imageViewprofile);
        save =(Button)v.findViewById(R.id.buttonSave);
        loadUserInfo();


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });


        return v;
    }

    private void loadUserInfo() {

        final String[] firepath = new String[1];
        email.setText(firebaseUser.getEmail());
        Log.v("coffeemate","COFFEE onCreateView END ");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("profilepics/"+firebaseUser.getUid()+"jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                                firepath[0] = uri.

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                profilePic.setImageDrawable(getContext().getResources().getDrawable(R.drawable.userimage));
            }
        });
        //StorageReference storageRef = storage.getReference();
//        StorageReference storageRef = storage.getReference();
//        if(profilePicURL != null){
//            try {
//                Glide.with(this.activity)
//                        .load(profilePicURL)
//                        .into(profilePic);
//
//            }catch (Exception e){
//                profilePic.setImageDrawable(getContext().getResources().getDrawable(R.drawable.userimage));
//                Log.v("coffeemate","COFFEE onCreateView END : https://firebasestorage.googleapis.com"+firebaseUser.getPhotoUrl() );
//                e.printStackTrace();
//
//            }
//        }
        if(firebaseUser.getDisplayName() != null){
            username.setText(firebaseUser.getDisplayName());
        }
    }

    private void imageChooser(){
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select profile pic.."), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData()!=null){
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageUri);
                profilePic.setImageBitmap(bitmap);
                uploadImageToFirebase();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void saveUserInfo() {
        String displayName =  username.getText().toString();
        if(displayName.isEmpty()){
            username.setError("Username required");
            username.requestFocus();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null && profilePicURL != null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profilePicURL)).build();
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(),"Profile updated !", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        DatabaseReference dbReference =FirebaseDatabase.getInstance().getReference("UserProfile")
                .child(app.fireBaseUser);
        UserProfile userProfile =new UserProfile(app.fireBaseUser, displayName, firebaseUser.getEmail(),profilePicURL);
        dbReference.setValue(userProfile);

    }

    private void uploadImageToFirebase() {
//        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference profilePicRef =
                FirebaseStorage.getInstance().getReference("profilepics/"+firebaseUser.getUid()+".jpg");
//        String downloadUrl;
        if(imageUri != null){
            profilePicRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            final String downloadUrl = uri.getPath();
//                            profilePicURL =downloadUrl;
//                        }
//                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Image upload failed", Toast.LENGTH_LONG).show();
                }
            });
        }
//        profilePicURL =downloadURL
    }
}
