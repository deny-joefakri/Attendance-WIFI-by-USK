package id.ac.unsyiah.android.absen_mobile.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.ac.unsyiah.android.absen_mobile.Adapter.ListMahasiswaAdapter;
import id.ac.unsyiah.android.absen_mobile.Model.Mahasiswa;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.MahasiswaResponse;
import id.ac.unsyiah.android.absen_mobile.Rest.Api;
import id.ac.unsyiah.android.absen_mobile.Rest.RetrofitClient;
import id.ac.unsyiah.android.absen_mobile.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMahasiswaActivity extends AppCompatActivity {

    private Api apiModel;
    private RecyclerView recyclerView;
    private ListMahasiswaAdapter listMahasiswaAdapter;
    private TextView tvNamaMhs, tvNpmMhs, tvNomor, tvNodata;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);

        apiModel = RetrofitClient.getInstance().getApi();
        recyclerView = findViewById(R.id.recycler_view_mhs);

        tvNamaMhs = findViewById(R.id.tv_nama_mhs);
        tvNpmMhs = findViewById(R.id.tv_npm_mhs);
        tvNomor = findViewById(R.id.tv_no);
        tvNodata = findViewById(R.id.tv_nomhs);
        progressBar = findViewById(R.id.pb_listmhs);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // panggil method untuk menampilkan semua data mahasiswa
        refreshListMahasiswa();
    }


    public void refreshListMahasiswa() {
        Intent pindah = getIntent();
        final String kode_mk = pindah.getStringExtra("kode_mk");
//        Log.d("testestes", dataStore.getNip());
        Call<MahasiswaResponse> call = apiModel.getMahasiswa(kode_mk);
        call.enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                if (response.body() != null && response.body().isSuccess()) {

                    Mahasiswa[] mhsArray = response.body().getMahasiswa();
                    if (mhsArray.length == 0) {
                        recyclerView.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        ArrayList<Mahasiswa> mhsList = new ArrayList<>();
                        for (Mahasiswa dataMhs : mhsArray) {
                            progressBar.setVisibility(View.GONE);
                            mhsList.add(dataMhs);
                        }
                        listMahasiswaAdapter = new ListMahasiswaAdapter(mhsList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListMahasiswaActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(listMahasiswaAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedConnect), Toast.LENGTH_LONG).show();
            }
        });
    }

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
