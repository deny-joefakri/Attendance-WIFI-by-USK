package id.ac.unsyiah.android.absen_mobile.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.ac.unsyiah.android.absen_mobile.Adapter.MainAdapter;
import id.ac.unsyiah.android.absen_mobile.SharedPreferences.DataStore;
import id.ac.unsyiah.android.absen_mobile.AttendanceStore.DatabaseAccess;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.LogoutResponse;
import id.ac.unsyiah.android.absen_mobile.Model.Matakuliah;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.MatakuliahResponse;
import id.ac.unsyiah.android.absen_mobile.Rest.Api;
import id.ac.unsyiah.android.absen_mobile.Rest.RetrofitClient;
import id.ac.unsyiah.android.absen_mobile.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private ImageView imgLogout, imgSetting;
    private TextView tvLogout, tvNama, tvNip, tvEmail, tvNoMk;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Api apiModel;
    private DataStore dataStore;
    private DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // api model
        apiModel = RetrofitClient.getInstance().getApi();
        // buat object database
        databaseAccess = new DatabaseAccess(getApplicationContext());

        dataStore= new DataStore(MainActivity.this);


        recyclerView = findViewById(R.id.recycler_main);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        imgLogout = findViewById(R.id.iv_keluar);
        imgSetting = findViewById(R.id.iv_next);
        tvLogout = findViewById(R.id.tv_keluar);
        tvNama = findViewById(R.id.tv_nama);
        tvNip = findViewById(R.id.tv_nip);
        tvEmail = findViewById(R.id.tv_email);
        tvNoMk = findViewById(R.id.tv_no_mk);
        progressBar = findViewById(R.id.pb_mainactivity);

        //ONCLICK LISTENER
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        //LOAD DATA DOSEN
        tvNip.setText(dataStore.getNip());
        tvNama.setText(dataStore.getNama());
        tvEmail.setText(dataStore.getEmail());

        // panggil method untuk menampilkan daftar mata kuliah
        refreshListMatkul();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListMatkul();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    int backPressCount = 0;
    @Override
    public void onBackPressed(){
        backPressCount++;
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
    }

    public void refreshListMatkul() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dayClient = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        progressBar.setVisibility(View.VISIBLE);
        Call<MatakuliahResponse> call = apiModel.getTodayCourse(dataStore.getNip(), dayClient);
        call.enqueue(new Callback<MatakuliahResponse>() {
            @Override
            public void onResponse(Call<MatakuliahResponse> call, Response<MatakuliahResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if(response.body().isSuccess()){
                        Matakuliah[] matakuliahArray = response.body().getDataMk();
                        if (matakuliahArray.length == 0) {
                            tvNoMk.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            ArrayList<Matakuliah> mkList = new ArrayList<>();
                            for (Matakuliah matkul : matakuliahArray) {
                                mkList.add(matkul);
                                tvNoMk.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                            mainAdapter = new MainAdapter(mkList, MainActivity.this);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mainAdapter);
                        }
                    } else {
                        tvNoMk.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to..", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<MatakuliahResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedConnect), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Method untuk keluar dari aplikasi.
     */
    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final DataStore dataStore = new DataStore(MainActivity.this);
                Call<LogoutResponse> call = RetrofitClient.getInstance().getApi().logout(dataStore.getToken());
                call.enqueue(new Callback<LogoutResponse>() {
                    @Override
                    public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                        String value = response.body().getValue();
                        Log.d("cekLogout", value);
                        if (value.equals("1")) {
                            dataStore.clear();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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

