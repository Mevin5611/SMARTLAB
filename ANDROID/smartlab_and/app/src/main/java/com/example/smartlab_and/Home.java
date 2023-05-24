package com.example.smartlab_and;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartlab_and.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
//        if( id==R.id.)

        if (id == R.id.nav_gallery) {
            // Handle the camera action
            Intent i =new Intent(getApplicationContext(),view_allocated_lab.class);
            startActivity(i);
        }

        else if (id == R.id.nav_slidesho) {
            // Handle the camera action
            Intent i = new Intent(getApplicationContext(),set_permission.class);
            startActivity(i);
        }
        else if (id == R.id.nav_slideshoo) {
            // Handle the camera action
            Intent i = new Intent(getApplicationContext(),blocked_apps.class);
            startActivity(i);
        }
        else if (id == R.id.nav_slidehow) {
            // Handle the camera action
            Intent i = new Intent(getApplicationContext(), view_exam_arrangement.class);
            startActivity(i);
        }
        else if (id == R.id.nav_slidesow) {
            // Handle the camera action
            Intent i = new Intent(getApplicationContext(),view_attendance.class);
            startActivity(i);
        }
//        else if (id == R.id.nav_slidee) {
//            // Handle thye camera action
//            Intent i = new Intent(getApplicationContext(),exam_scheduling.class);
//            startActivity(i);
//        }

        else if (id == R.id.nav_slihow) {
            // Handle the camera action
            Intent i = new Intent(getApplicationContext(),login.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        else if (id == R.id.nav_slide) {
            // Handle the camera action
            Intent i = new Intent(getApplicationContext(),lab_scheduling.class);
            startActivity(i);
        }







//        else if (id == R.id.f) {
//            Intent i =new Intent(getApplicationContext(),Addfeedback.class);
//            startActivity(i);
//        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
//            return false;
    }
}