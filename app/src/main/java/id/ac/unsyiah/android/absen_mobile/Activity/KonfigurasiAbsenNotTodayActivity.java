package id.ac.unsyiah.android.absen_mobile.Activity;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.ac.unsyiah.android.absen_mobile.Activity.Response.MatakuliahResponse;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.StartAbsenResponse;
import id.ac.unsyiah.android.absen_mobile.AttendanceStore.DatabaseAccess;
import id.ac.unsyiah.android.absen_mobile.Model.DataAttendance;
import id.ac.unsyiah.android.absen_mobile.Model.LecturerAttendance;
import id.ac.unsyiah.android.absen_mobile.Model.Matakuliah;
import id.ac.unsyiah.android.absen_mobile.R;
import id.ac.unsyiah.android.absen_mobile.Rest.Api;
import id.ac.unsyiah.android.absen_mobile.Rest.RetrofitClient;
import id.ac.unsyiah.android.absen_mobile.Service.AttendanceService;
import id.ac.unsyiah.android.absen_mobile.SharedPreferences.DataStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfigurasiAbsenNotTodayActivity extends AppCompatActivity {
    public static final int REQUEST_ENABLE_BT = 1;
    private static int ALARM_REQUEST_CODE = 134;

    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PendingIntent pendingIntent;
    private TextView tvKdmk, tvMk, tvWaktu, tvRuang, tvSks, tvJumlahMhs, tvHari, tv_info;
    private Api apiModel;
    private LinearLayout linearKiri, linearTengah, linearKanan;
    private Button btnStart, btnShow;
    private ImageView bg_main;
    private RecyclerView recyclerView;
    private String kd_mk;
    private DataStore dataStore;
    private DatabaseAccess databaseAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfigurasi_absen);

        // akses database sqlite
        databaseAccess = new DatabaseAccess(getApplicationContext());
        // api model
        apiModel = RetrofitClient.getInstance().getApi();
        // membuat objek datastore (sharedpreferences)
        dataStore = new DataStore(KonfigurasiAbsenNotTodayActivity.this);


        swipeRefreshLayout = findViewById(R.id.swipe_layout_course);
        progressBar = findViewById(R.id.pb_configure);
        tvKdmk = findViewById(R.id.tv_kdmk);
        tvMk = findViewById(R.id.tv_value_namamk);
        tvHari = findViewById(R.id.tv_value_hari);
        tvWaktu = findViewById(R.id.tv_value_waktu);
        tv_info = findViewById(R.id.tv_info);
        tvRuang = findViewById(R.id.tv_value_ruang);
        tvSks = findViewById(R.id.tv_value_sks);
        tvJumlahMhs = findViewById(R.id.tv_value_kuota);
        bg_main = findViewById(R.id.bg_main);
        //tampilin back button di action bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(this).load(R.drawable.background_main).into(bg_main);

        btnStart = findViewById(R.id.btn_start_configure);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                boolean wifiEnabled = wifiManager.isWifiEnabled();
                if (!wifiEnabled) {
                    wifiManager.setWifiEnabled(true);
                }
                startAttendance();

                //absen(283, 6);

            }
        });

        btnStart.setVisibility(View.GONE);
        tv_info.setVisibility(View.GONE);


        btnShow = findViewById(R.id.btn_show_configure);
        linearKiri = findViewById(R.id.left_layout);
        linearTengah = findViewById(R.id.mid_layout);
        linearKanan = findViewById(R.id.right_layout);

        // mengambil intent yang dikirim pada activity sebelumnya
        Intent intent = getIntent();
        kd_mk = intent.getStringExtra("kd_mk");

        // panggil method untuk menampilkan informasi mata kuliah
        getListMk();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListMk();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        databaseAccess.open();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateClient = simpleDateFormat.format(date);
        /*if (databaseAccess.checkCourseId(kd_mk, dateClient)) {
            databaseAccess.close();
            btnStart.setText("Attendance is Running...");
            btnStart.setEnabled(false);
            btnStart.setBackground(ContextCompat.getDrawable(this, R.drawable.green_fill__rounded_color));
            return;
        }*/
    }

    // method untuk menampilkan daftar matakuliah yang diajar dosen.
    public void getListMk() {
        //set progres bar
        progressBar.setVisibility(View.VISIBLE);
        //memulai REST API CALL
        Call<MatakuliahResponse> call;
        Log.e("kd_mk", kd_mk);
        call = apiModel.getListMk(kd_mk);
        call.enqueue(new Callback<MatakuliahResponse>() {
            @Override
            public void onResponse(Call<MatakuliahResponse> call, Response<MatakuliahResponse> response) {
                if (response.body() != null && response.body().isSuccess()) {
                    Matakuliah[] matakuliahArray = response.body().getDataMk();
                    for (Matakuliah matkul : matakuliahArray) {
                        progressBar.setVisibility(View.GONE);
                        tvKdmk.setVisibility(View.VISIBLE);
                        linearKiri.setVisibility(View.VISIBLE);
                        linearTengah.setVisibility(View.VISIBLE);
                        linearKanan.setVisibility(View.VISIBLE);
                        btnShow.setVisibility(View.VISIBLE);
                        tvKdmk.setText("Course ID: " + matkul.getKd_mk());
                        tvMk.setText(matkul.getNama_mk());
                        tvHari.setText(matkul.getHari());
                        tvWaktu.setText(matkul.getJam_masuk() + " - " + matkul.getJam_keluar() + " WIB");
                        tvRuang.setText(matkul.getRuang());
                        tvSks.setText(matkul.getSks());
                        tvJumlahMhs.setText(matkul.getJumlah_mhs());
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = sdf.parse(matkul.getJam_keluar());
                            Calendar c = Calendar.getInstance();
                            c.setTime(date);
                            c.add(Calendar.MINUTE, -50);
                            tv_info.setText("Silahkan melakukan attendance sebelum " + sdf.format(c.getTime()) + " !");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        //fungsi untuk mengambil current time
                        Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");

                        /*try {
                            Date t1 = simpleDateFormat.parse(simpleDateFormat.format(currentTime));
                            Date t2 = simpleDateFormat.parse(matkul.getJam_keluar());
                            if (t1.after(t2)) {
                                btnStart.setEnabled(false);
                                btnStart.setText("The Course Has Ended");
                                btnStart.setBackground(ContextCompat.getDrawable(KonfigurasiAbsenNotTodayActivity.this, R.drawable.red_fill__rounded_color));
                            }
                        } catch (ParseException e) {

                        }*/
                    }
                } else {
                    Toast.makeText(KonfigurasiAbsenNotTodayActivity.this, "Terjadi Kesalahan: "+response.message(), Toast.LENGTH_SHORT).show();
                }
                // intent ke halaman informasi matakuliah
                btnShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pindah = new Intent(KonfigurasiAbsenNotTodayActivity.this, ListMahasiswaActivity.class);
                        pindah.putExtra("kode_mk", kd_mk);
                        startActivity(pindah);
                    }
                });
            }

            @Override
            public void onFailure(Call<MatakuliahResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedConnect), Toast.LENGTH_LONG).show();
            }
        });
    }

    //method untuk memulai absen pada tombol mulai absen
    public void startAttendance() {

        //fungsi untuk mengambil current time
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");
        String jam_masuk = simpleDateFormat.format(date);

        Log.e("data", dataStore.getNip() + " -- "+kd_mk+" -- "+jam_masuk);
        //memulai REST API CALL
        Call<StartAbsenResponse> call;
        call = apiModel.startAbsen(dataStore.getNip(), kd_mk, jam_masuk); //parameter harus berurutan(sesuai dengan method pada class API.java).
        call.enqueue(new Callback<StartAbsenResponse>() {
            @Override
            public void onResponse(Call<StartAbsenResponse> call, Response<StartAbsenResponse> response) {
                StartAbsenResponse startAbsenResponse = response.body();
                if (response.body() != null && response.isSuccessful()) {
                    //Log.e("body", response.body().toString());
                    if (response.body().isSuccess()) {

                        //menambahkan data absen ke database sqlite = table lectuter
                        Date tgl = new Date();
                        SimpleDateFormat formatTgl = new SimpleDateFormat("yyyy-MM-dd");
                        LecturerAttendance lecturerAttendance = new LecturerAttendance();
                        lecturerAttendance.setKdmkSqlite(kd_mk);
                        lecturerAttendance.setTanggalSqlite(formatTgl.format(tgl));
                        databaseAccess.open();
                        databaseAccess.addLecturerAttendance(lecturerAttendance);
                        databaseAccess.close();

                        btnStart.setText("Attendance is Running...");
                        btnStart.setEnabled(false);
                        btnStart.setBackground(ContextCompat.getDrawable(KonfigurasiAbsenNotTodayActivity.this, R.drawable.green_fill__rounded_color));

                        String jamMulai = startAbsenResponse.getJamMulai(); //mengambil jam mulai matakuliah di server.
                        String[] splitTime = jamMulai.split(":"); //split format jam mulai
                        int hour = Integer.parseInt(splitTime[0]); //ambil jam
                        int minute = Integer.parseInt(splitTime[1]); //ambil menit

                        //fungsi calendar untuk menyetel waktu yang diinginkan.
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        long milisMulai = calendar.getTimeInMillis(); //dalam milisekon.

                        //conditional untuk me-random menit waktu absen
                        for (int i = 1; i <= startAbsenResponse.getJumlahAbsen(); i++) {
                            int random = (int) (Math.random() * 9) + 1;

                            long milisAbsen = milisMulai + (2 * 60000); //dikali dengan 1 menit
                            calendar.setTimeInMillis(milisAbsen);
                            int jam = calendar.get(Calendar.HOUR) + calendar.get(Calendar.AM_PM) * 12;
                            int menit = calendar.get(Calendar.MINUTE);

                            //ambil ID absen di server
//                            Log.d("idRuang", +startAbsenResponse.getIdRuang() + "");

                            //menyimpan data-data yang diambil dari server ke dalam SQLite
                            DataAttendance dataAttendance = new DataAttendance();
                            dataAttendance.setIdAbsen(startAbsenResponse.getId());
                            dataAttendance.setJamAbsen(jam);
                            dataAttendance.setMenitAbsen(menit);
                            dataAttendance.setIdRuang(startAbsenResponse.getIdRuang());

                            databaseAccess.open();
                            databaseAccess.addDataAttendance(dataAttendance);

//                            milisMulai += 600000; // per 10 menit.
                            milisMulai += 120000; // per 2 menit.
                        }
                        databaseAccess.close();
                        //panggil method absen
                        Log.e("getId", ""+startAbsenResponse.getId());
                        Log.e("getJumlahAbsen", ""+startAbsenResponse.getJumlahAbsen());
                        absen(startAbsenResponse.getId(), startAbsenResponse.getJumlahAbsen());
                        Toast.makeText(KonfigurasiAbsenNotTodayActivity.this, "Successfully started attendance", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(KonfigurasiAbsenNotTodayActivity.this, startAbsenResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(KonfigurasiAbsenNotTodayActivity.this, startAbsenResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StartAbsenResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedConnect), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void absen(int idAbsen, int jumlahAbsen) {
        databaseAccess.open();

        // menyimpan data absen dari SQLite ke dalam List
        List<DataAttendance> attendancesList = databaseAccess.selectIsNotAbsen(idAbsen);
        databaseAccess.close();
        Calendar calendar = Calendar.getInstance();
        Log.d("DEBUG_DATA", attendancesList.size() + "");
        int nomorAbsen = 1;
        for (DataAttendance dataAttendance : attendancesList) {
            calendar.set(Calendar.HOUR_OF_DAY, dataAttendance.getJamAbsen());
            calendar.set(Calendar.MINUTE, dataAttendance.getMenitAbsen());
            calendar.set(Calendar.SECOND, 0);
            Log.d("SELECT_SQLITE", "nomor absen= " + nomorAbsen + " jumlah absen: "+jumlahAbsen+ " waktu = " + dataAttendance.getJamAbsen() + ":" + dataAttendance.getMenitAbsen());

            Intent intent = new Intent(getApplicationContext(), AttendanceService.class);
            intent.putExtra("id_absen", idAbsen); // kirim ID absen
            intent.putExtra("nip", dataStore.getNip()); // kirim NIP dose
            intent.putExtra("id", dataAttendance.getId()); // kirim ID absen dari SQLite.
            intent.putExtra("kdmk", kd_mk); // kirim kode mk.
            intent.putExtra("idRuang", dataAttendance.getIdRuang()); // kirim id ruang.
            Log.d("Konfigurasi", +dataAttendance.getIdAbsen() + "");
            // kirim sesuatu untuk memberhentikan service
            if (nomorAbsen == jumlahAbsen) {
                intent.putExtra("stopService", true);
            } else {
                intent.putExtra("stopService", false);
            }
            Log.d("TES_ID_ABSEN", dataAttendance.getIdAbsen() + "");

            //set alarm manager dengan memasukkan waktu yang telah dikonversi menjadi milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                pendingIntent = PendingIntent.getForegroundService(KonfigurasiAbsenNotTodayActivity.this, ALARM_REQUEST_CODE++, intent, PendingIntent.FLAG_ONE_SHOT);
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Log.d("request_code", ALARM_REQUEST_CODE + "");
            } else {
                Toast.makeText(getApplicationContext(), "Please update your Android version!", Toast.LENGTH_LONG).show();
            }
            nomorAbsen++;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("KONFIGURASI_ABSEN", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("KONFIGURASI_ABSEN", "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("KONFIGURASI_ABSEN", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("KONFIGURASI_ABSEN", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("KONFIGURASI_ABSEN", "onDestroy");
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Utils.toast(getApplicationContext(), "Thank you for turning on Bluetooth");
                btnStart.setText("START ATTENDANCE");
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startAttendance();
                    }
                });
            } else if (resultCode == RESULT_CANCELED) {
//                Utils.toast(getApplicationContext(), "Sorry, cannot start attendance!");
                btnStart.setText("Turn On Bluetooth!");
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        turnOnBluetooth();
                    }
                });
            }
        }
    }*/


    //action bar untuk back to previous activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}



