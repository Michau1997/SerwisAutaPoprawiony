package com.example.serwisauta2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;
    private TextView AddCar;
    private TextView NavProfileUserName;


    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    RecyclerView carview;
    CarAdapter carAdapter;

    String currentUserID;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        carAdapter.startListening();

        if (currentUser == null) {
            SendUserToLoginActivity();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();

        carAdapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddCar = (TextView) findViewById(R.id.add_car);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("User");


        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Moje auta");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);
        NavProfileUserName = (TextView) navView.findViewById(R.id.nav_user_full_name);

        carview=(RecyclerView)findViewById(R.id.all_cars_list);
        carview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Car> options =
                new FirebaseRecyclerOptions.Builder<Car>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Car").child(mAuth.getUid()), Car.class)
                .build();
        carAdapter = new CarAdapter(options);
        carview.setAdapter(carAdapter);



        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.hasChild("username"))
                    {

                        String fullname = dataSnapshot.child("username").getValue().toString();
                        NavProfileUserName.setText("Witaj, " + fullname);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                UserMenuSelector(item);
                return false;
            }
        });

        AddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToAddCar();
            }
        });
    }





    private void SendUserToLoginActivity() {

        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void SendUserToMainActivity() {

        Intent mainIntent = new Intent(MainActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    private void SendUserToMyCars() {

        Intent carsIntent = new Intent(MainActivity.this, MyCars.class);
        carsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(carsIntent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_home:
                SendUserToMainActivity();
                break;
            case R.id.nav_mycars:
                SendUserToMyCars();
                break;


            case R.id.nav_logout:
                mAuth.signOut();
                SendUserToLoginActivity();
                break;
        }

    }
    private void SendUserToAddCar() {

        Intent AddCarIntent = new Intent(MainActivity.this, AddCar.class);
        startActivity(AddCarIntent);
    }
}