package id.ac.unsyiah.android.absen_mobile.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import id.ac.unsyiah.android.absen_mobile.AttendanceStore.DatabaseAccess;
import id.ac.unsyiah.android.absen_mobile.KNN.KNN;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.AbsenDosenResponse;
import id.ac.unsyiah.android.absen_mobile.R;
import id.ac.unsyiah.android.absen_mobile.Wifi.ResultModel;
import id.ac.unsyiah.android.absen_mobile.Wifi.WifiModel;
import id.ac.unsyiah.android.absen_mobile.Rest.Api;
import id.ac.unsyiah.android.absen_mobile.Rest.RetrofitClient;
import id.ac.unsyiah.android.absen_mobile.Wifi.WifiScanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanActivity extends AppCompatActivity {
    private Api apiModel;
    private int idAbsen, idDb, idRuang;
    private String nip, kdmk;
    private boolean stopAbsen;
    private DatabaseAccess databaseAccess;

    private WifiScanner wifiScanner;
    private HashMap<String, WifiModel> trackedData;

    private int label;

    private PowerManager.WakeLock mWakeLock;
    private TextView tvDetected, tvRemaining, tvExecutionTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "myapp:mywakelockgtag");

        // untuk menghidupkan layar device
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_scan);

        // ambil value dari intent halaman sebelumnya.
        idAbsen = (int) (getIntent().getExtras().get("id_absen"));
        nip = (String) (getIntent().getExtras().get("nip"));
        idDb = (int) (getIntent().getExtras().get("id"));
        stopAbsen = (boolean) (getIntent().getExtras().get("stopService"));
        kdmk = (String) (getIntent().getExtras().get("kdmk"));
        idRuang = (int) (getIntent().getExtras().get("idRuang"));

        tvDetected = findViewById(R.id.tv_detected);
        tvRemaining = findViewById(R.id.tv_remaining);
        tvExecutionTime = findViewById(R.id.tv_execution_time);


        // untuk prevent layar mati
        mWakeLock.acquire();

        // membuat objek untuk akses database SQLite.
        databaseAccess = new DatabaseAccess(ScanActivity.this);

        // membuat objek API.
        apiModel = RetrofitClient.getInstance().getApi();

        startScan(); // panggil method scan Bluetooth.
    }

    private void startScan() {
        //   trackedData.clear();
        Log.i("WIFISCANNER", "start scanning");
//        wifiScanner = new WifiScanner(this, getApplicationContext());
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startScanThread();
            }
        });

    }

    public void startScanThread() {
        wifiScanner = new WifiScanner(this, getApplicationContext());
    }


    public void stopScan() {
        String train = "data_training_4_edt.csv";

        trackedData = wifiScanner.getScannedAps();
        Log.i("WIFISCANNER", "stopScan: "+trackedData.size());

        //untuk mengambil data hasil pemindaian yg tersimpan di Hash.
        WifiModel wlan1 = trackedData.get(ResultModel.WIFI_MAC_1);
        WifiModel wlan2 = trackedData.get(ResultModel.WIFI_MAC_2);
        WifiModel wlan3 = trackedData.get(ResultModel.WIFI_MAC_3);
        WifiModel wlan4 = trackedData.get(ResultModel.WIFI_MAC_4);
        WifiModel wlan5 = trackedData.get(ResultModel.WIFI_MAC_5);
        WifiModel wlan6 = trackedData.get(ResultModel.WIFI_MAC_6);

        //untuk kasih nilai default apabila sinyal Bluetooth tidak dapat.
        int rssi1 = wlan1 == null ? -100 : wlan1.getSignalStrength();
        int rssi2 = wlan2 == null ? -100 : wlan2.getSignalStrength();
        int rssi3 = wlan3 == null ? -100 : wlan3.getSignalStrength();
        int rssi4 = wlan4 == null ? -100 : wlan4.getSignalStrength();
        int rssi5 = wlan5 == null ? -100 : wlan5.getSignalStrength();
        int rssi6 = wlan6 == null ? -100 : wlan6.getSignalStrength();

        /*int rssi1 = trackedData.get(ResultModel.WIFI_MAC_1).getSignalStrength();
        int rssi2 = trackedData.get(ResultModel.WIFI_MAC_2).getSignalStrength();
        int rssi3 = trackedData.get(ResultModel.WIFI_MAC_3).getSignalStrength();*/
        //int rssi4 = trackedData.get(ResultModel.WIFI_MAC_7).getSignalStrength();

        //Log.i("WIISCANNER", "stopScan: " + rssi1 + ", " +rssi2+ ", "+rssi3);
        double[] testData = {rssi1, rssi2, rssi3, rssi4, rssi5, rssi6};
        //double[] testData = {rssi4};

        try {
            InputStream streamTrain = getAssets().open(train);
            label = (int) KNN.knn(streamTrain, testData, 5);
            // boolean masukKelas = true; // KNN.knn(streamTrain, testData, 5);
            boolean masukKelas = (label == idRuang);

            Log.e("label", ""+label);
            Log.e("idRuang", ""+idRuang);
            //Log.d("testData", ""+testData);
            Log.e("Boolean", ""+masukKelas);

            sentToServer(masukKelas, stopAbsen);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // method untuk mengirim data ke server
    private void sentToServer(final boolean masukKelas, final boolean stop) {
        Log.d("Tes_Id", "ID: " + idDb + " " + "Mencoba mengirim data id_absen :" + idAbsen + " ----- nip :" + nip);
        databaseAccess.open();
        if (databaseAccess.cekIsAbsen(idDb)) {
            databaseAccess.close();
            return;
        }
        if (stop) {
            Log.d("CEK_STOP", "BERHENTI");
            // hapus data di SQLite
            Log.d("CEK_DELETE", "panggil method delete");
            databaseAccess.open();
            databaseAccess.deleteDataAttendance(idAbsen);
            databaseAccess.close();
        } else {
            Log.d("CEK_STOP", "LANJUT");
        }
        // CALL
        Call<AbsenDosenResponse> call;
        // cek status hasil prediksi KNN.
        int status = masukKelas ? 1 : 0;

        // select count sisa absen
        databaseAccess.open();
        final int sisaAbsen = databaseAccess.countAttendance(idDb, idAbsen);
        databaseAccess.close();


        if (label == 1) {
            tvDetected.setVisibility(View.VISIBLE);
            tvDetected.setText("You were detected at: B.03.02");
        } else if(label == 2) {
            tvDetected.setVisibility(View.VISIBLE);
            tvDetected.setText("You were detected at: E.02.07");
        } else {
            tvDetected.setVisibility(View.VISIBLE);
            tvDetected.setText("You were detected outside the classroom");
        }
        // lanjut call
        call = apiModel.dosenAbsen(idAbsen, nip, status);
        call.enqueue(new Callback<AbsenDosenResponse>() {
            @Override
            public void onResponse(Call<AbsenDosenResponse> call, Response<AbsenDosenResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
//                    AbsenDosenResponse absenDosenResponse = response.body();
                    if (response.body().isSuccess()) {
//                        Toast.makeText(getApplicationContext(), "Berhasil Mengirim Data Dosen", Toast.LENGTH_LONG).show();
                        databaseAccess.open();
                        databaseAccess.updateIsAbsen(idDb);
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_LONG).show();
                    }
                    tvRemaining.setVisibility(View.VISIBLE);
                    tvRemaining.setText("Remaining Attendance: " + sisaAbsen);
                    databaseAccess.close();
                }
                // untuk memberhentikan service
                if (stop) {
                    Log.d("STOOP", "STOP MENGIRIM DATA");
                    databaseAccess.open();
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDate = simpleDateFormat.format(date);
                    databaseAccess.deleteLecturerAttendance(kdmk, currentDate);
                    databaseAccess.close();


                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWakeLock.release();
                        finishAffinity();
                        System.exit(0);
                    }
                }, 5000);

                Log.d("cek_knn", "idRuang: "+idRuang+ "label: " +label);

            }


            @Override
            public void onFailure(Call<AbsenDosenResponse> call, Throwable t) {
                Toast.makeText(ScanActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                // untuk memberhentikan service
                if (stop) {
                    Log.d("STOOP", "STOP MENGIRIM DATA");
                }
                mWakeLock.release();
                finish();
            }
        });
    }
}
