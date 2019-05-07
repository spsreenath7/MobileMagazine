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
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wit.magazine.R;
import com.wit.magazine.activities.HomeActivity;
import com.wit.magazine.activities.MainActivity;
import com.wit.magazine.adapters.BookmarkListAdpater;
import com.wit.magazine.models.Bookmark;
import com.wit.magazine.models.UserProfile;

import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;
import static com.wit.magazine.activities.HomeActivity.app;

public class ProfileFragment extends Fragment {

    private static final int PICK_IMAGE = 101;
    View v;
    ImageView profilePic;
    EditText username;
    TextView email;
    Button save;
    UserProfile userProfile;

    Uri imageUri;
    String profilePicURL;
    String downloadURL;
    FirebaseUser firebaseUser;
    HomeActivity activity;

    public ProfileFragment() {

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserProfile")
                .child(app.fireBaseUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                bookmarkList.clear();
                activity.showLoader("downloading bookmarks");
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    userProfile = dataSnapshot.getValue(UserProfile.class);
//                    bookmarkList.add(bookmark);
//                }
//                BookmarkListAdpater trackListAdapter = new BookmarkListAdpater(activity, BookmarkFragment.this, bookmarkList);
//                listView.setAdapter(trackListAdapter);
                loadUserInfo();
                activity.hideLoader();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        email = (TextView) v.findViewById(R.id.textViewEmail);
        username = (EditText) v.findViewById(R.id.editDisplayname);
        profilePic = (ImageView) v.findViewById(R.id.imageViewprofile);
        save = (Button) v.findViewById(R.id.buttonSave);
//        loadUserInfo();


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
        if(userProfile != null){
            email.setText(userProfile.getEmail());
            username.setText(userProfile.getUsername());

        }else {
            email.setText(firebaseUser.getEmail());
            username.setText(firebaseUser.getDisplayName());
        }


    }

    private void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select profile pic.."), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                profilePic.setImageBitmap(bitmap);
                uploadImageToFirebase();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void saveUserInfo() {
        String displayName = username.getText().toString();
        if (displayName.isEmpty()) {
            username.setError("Username required");
            username.requestFocus();
            return;
        }

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("UserProfile")
                .child(app.fireBaseUser);
        UserProfile userProfile = new UserProfile(app.fireBaseUser, displayName, firebaseUser.getEmail(), downloadURL);
        Log.v("Magazine", "uploadImageToFirebase before return "+userProfile.toString());
        dbReference.setValue(userProfile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        user.set
        if (user != null ) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Profile updated !", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


//        DatabaseReference dbReference =FirebaseDatabase.getInstance().getReference("UserProfile")
//                .child(app.fireBaseUser).child("profilephotoURL");

    }

    private void uploadImageToFirebase() {
//        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String uploadRef = "profilepics/" + System.currentTimeMillis() + ".jpg";

        final StorageReference profilePicRef =
                FirebaseStorage.getInstance().getReference(uploadRef);
        final UploadTask uploadTask = profilePicRef.putFile(imageUri);
        final String[] downloadUrlArr = new String[1];
        if (imageUri != null) {
//            progressBar.setVisibility(View.VISIBLE);
            profilePicRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                            progressBar.setVisibility(View.GONE);
//                            downloadURL = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            downloadURL= uploadRef;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
//        if (imageUri != null) {
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                        @Override
//                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                            if (!task.isSuccessful()) {
//                                throw task.getException();
//
//                            }
//                            // Continue with the task to get the download URL
//                            return profilePicRef.getDownloadUrl();
//
//                        }
//                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            if (task.isSuccessful()) {
//                                downloadUrlArr[0] = task.getResult().toString();
//                                Toast.makeText(getContext(), "Image upload done", Toast.LENGTH_LONG).show();
//
//
//                            }
//                        }
//                    });
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getContext(), "Image upload failed", Toast.LENGTH_LONG).show();
//                }
//            });
//
//
//        }
//        Log.v("Magazine", "uploadImageToFirebase before retur");
//        return downloadUrlArr[0];
    }

//    @GlideModule
//    public class MyAppGlideModule extends AppGlideModule {
//        @Override
//        public void registerComponents(Context context, Glide glide, Registry registry) {
//            // Register FirebaseImageLoader to handle StorageReference
//            registry.append(StorageReference.class, InputStream.class,
//                    new FirebaseImageLoader.Factory());
//        }
//    }
}
