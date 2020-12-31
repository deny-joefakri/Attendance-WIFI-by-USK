package id.ac.unsyiah.android.absen_mobile.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.LogoutResponse;
import id.ac.unsyiah.android.absen_mobile.R;
import id.ac.unsyiah.android.absen_mobile.Rest.RetrofitClient;
import id.ac.unsyiah.android.absen_mobile.Service.AttendanceService;
import id.ac.unsyiah.android.absen_mobile.SharedPreferences.DataStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private DataStore dataStore;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        dataStore= new DataStore(MainDrawerActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_matkul, R.id.nav_about, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();

        View headerView = navigationView.getHeaderView(0);
        ImageView bg_main = headerView.findViewById(R.id.bg_main);
        CircleImageView img_profile = headerView.findViewById(R.id.img_user);
        TextView txt_name = headerView.findViewById(R.id.txt_name);
        TextView txt_nip = headerView.findViewById(R.id.txt_nip);
        TextView txt_email = headerView.findViewById(R.id.txt_email);

        Glide.with(this).load(R.drawable.img_login).into(bg_main);
        Glide.with(this).load(R.drawable.img_teacher_college).into(img_profile);
        txt_nip.setText(dataStore.getNip());
        txt_name.setText(dataStore.getNama());
        txt_email.setText(dataStore.getEmail());

        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_dashboard){
                    post = 0;
                } else if (menuItem.getItemId() == R.id.nav_matkul){
                    post = 1;
                } else if (menuItem.getItemId() == R.id.nav_about){
                    post = 2;
                } else if (menuItem.getItemId() == R.id.nav_logout){
                    logOut();
                }

                NavigationUI.onNavDestinationSelected(menuItem, navController);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    int backPressCount = 0;
    int post = 0;
    @Override
    public void onBackPressed(){
        if (post == 0){
            backPressCount++;
            //Log.e("backPressCount", ""+backPressCount);
            if(backPressCount == 2 ) {
                super.onBackPressed();
            } else{
                Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();

                new CountDownTimer(5000, 1000){
                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        backPressCount = 0;
                    }
                }.start();
            }
        } else {
            backPressCount = 0;
            super.onBackPressed();
            post = 0;
        }

    }

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainDrawerActivity.this);
        builder.setTitle("LOGOUT");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final DataStore dataStore = new DataStore(MainDrawerActivity.this);
                Call<LogoutResponse> call = RetrofitClient.getInstance().getApi().logout(dataStore.getToken());
                call.enqueue(new Callback<LogoutResponse>() {
                    @Override
                    public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                        String value = response.body().getValue();
                        Log.e("cekLogout", value);
                        if (value.equals("1")) {
                            Toast.makeText(MainDrawerActivity.this, "You have signed out", Toast.LENGTH_SHORT).show();
                            dataStore.clear();

                            Intent ser = new Intent(MainDrawerActivity.this, AttendanceService.class);
                            stopService(ser);

                            Intent intent = new Intent(MainDrawerActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }
                    @Override
                    public void onFailure(Call<LogoutResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedConnect), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }

}