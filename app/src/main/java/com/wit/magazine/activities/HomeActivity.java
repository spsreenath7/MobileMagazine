package com.wit.magazine.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wit.magazine.R;
import com.wit.magazine.fragments.ArticlePageFragment;
import com.wit.magazine.fragments.ArticlesFragment;
import com.wit.magazine.fragments.BookmarkFragment;
import com.wit.magazine.fragments.FindfriendsFragment;
import com.wit.magazine.fragments.PreferenceFragment;
import com.wit.magazine.fragments.ProfileFragment;
import com.wit.magazine.fragments.SearchFragment;
import com.wit.magazine.fragments.ShareDialogFragment;
import com.wit.magazine.fragments.SocialFeedFragment;
import com.wit.magazine.main.MagazineApp;
import com.wit.magazine.models.UserPreference;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ArticlePageFragment.ArticlePageInteractionListener, ShareDialogFragment.ShareDialogListener {

//    Button button;
    public static MagazineApp app = MagazineApp.getInstance();
    public static AlertDialog loader;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference dbReference;

    FragmentTransaction ft;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (MagazineApp) getApplication();
        mAuth = FirebaseAuth.getInstance();
        app.fireBaseUser =mAuth.getCurrentUser().getUid();
        app.fireBaseUserName = mAuth.getCurrentUser().getDisplayName();
        app.fireBaseUserEmail = mAuth.getCurrentUser().getEmail();
        app.userPreference =new UserPreference(true, false, false, false, false, false, false, "ie", "Ireland", 10);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserPreference").child(app.fireBaseUser);

//        showLoader("downloading user preferences..");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserPreference dbUserPref = dataSnapshot.getValue(UserPreference.class);
                if(dbUserPref != null){
                    app.userPreference =dbUserPref;
                }
//                hideLoader();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        setContentView(R.layout.home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser() == null){
                    startActivity(new Intent(HomeActivity.this, MainActivity.class ));
                }
            }
        };

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView userName = navigationView.getHeaderView(0).findViewById(R.id.username);
        userName.setText(app.fireBaseUserName);

        TextView userMail = navigationView.getHeaderView(0).findViewById(R.id.email);
        userMail.setText(app.fireBaseUserEmail);

        ft = getSupportFragmentManager().beginTransaction();

        ArticlesFragment fragment = ArticlesFragment.newInstance();
        ft.replace(R.id.homeFrame, fragment);
        //ft.addToBackStack(null);
        ft.commit();


        createLoader();
        dbReference = FirebaseDatabase.getInstance().getReference("FriendLists").child(app.fireBaseUser);
        showLoader("please wait");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String friend = postSnapshot.getValue(String.class);
                    app.friendsSet.add(friend);
                }
                hideLoader();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        this.setTitle("Articles");
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        // http://stackoverflow.com/questions/  32944798/switch-between-fragments-with-onnavigationitemselected-in-new-navigation-drawer

        int id = item.getItemId();
        Fragment fragment;
        ft = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            fragment = ArticlesFragment.newInstance();

            startActivity(new Intent(this, HomeActivity.class));

        }else if (id == R.id.nav_bookmarks) {
            fragment = BookmarkFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }else if (id == R.id.nav_prefrences) {
            fragment = PreferenceFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_feed) {
            fragment = SocialFeedFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }  else if (id == R.id.nav_search) {
            fragment = FindfriendsFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }else if (id == R.id.nav_search_bookmarks) {
            fragment = SearchFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }else if (id == R.id.nav_map) {
//            fragment = MapsFragment.newInstance();
//            ft.replace(R.id.homeFrame, fragment);
//            ft.addToBackStack(null);
//            ft.commit();
//            Intent intent = ;
            startActivity(new Intent(this, MapActivity.class));
        } else if (id == R.id.nav_info) {
            fragment = ProfileFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_logout) {
            mAuth.signOut();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void buy(View v) {
        ArticlePageFragment articlePageFragment = (ArticlePageFragment)
                getSupportFragmentManager().findFragmentById(R.id.homeFrame);
        if (articlePageFragment != null) {
            articlePageFragment.buy();
        }
    }

    @Override
    public void bookmark(View v) {
        ArticlePageFragment articlePageFragment = (ArticlePageFragment)
                getSupportFragmentManager().findFragmentById(R.id.homeFrame);
        if (articlePageFragment != null) {
            articlePageFragment.bookmark();
        }
    }

    @Override
    public void share(View v) {
        ShareDialogFragment exampleDialog = new ShareDialogFragment();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void menuHome(MenuItem m) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void menuInfo(MenuItem m) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.appAbout))
                .setMessage(getString(R.string.appDesc)
                        + "\n\n"
                        + getString(R.string.appMoreInfo))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // we could put some code here too
                    }
                })
                .show();
    }

    public void createLoader() {
        AlertDialog.Builder loaderBuilder = new AlertDialog.Builder(this);
        loaderBuilder.setCancelable(true); // 'false' if you want user to wait
        loaderBuilder.setView(R.layout.loading);
        loader = loaderBuilder.create();
        loader.setTitle(R.string.appDisplayName);
    }

    public void showLoader(String message) {
        if (!loader.isShowing()) {
            if(message != null)loader.setTitle(message);
            loader.show();
        }
    }

    public void hideLoader() {
        if (loader.isShowing())
            loader.dismiss();
    }

    @Override
    public void applyTexts(String caption) {
        ArticlePageFragment articlePageFragment = (ArticlePageFragment)
                getSupportFragmentManager().findFragmentById(R.id.homeFrame);
        if (articlePageFragment != null) {
            articlePageFragment.share(caption);
        }
    }
}
