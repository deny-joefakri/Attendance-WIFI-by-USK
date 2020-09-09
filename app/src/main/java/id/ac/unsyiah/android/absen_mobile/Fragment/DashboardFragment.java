package id.ac.unsyiah.android.absen_mobile.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.ac.unsyiah.android.absen_mobile.Activity.Response.MatakuliahResponse;
import id.ac.unsyiah.android.absen_mobile.Adapter.MainAdapter;
import id.ac.unsyiah.android.absen_mobile.Model.Matakuliah;
import id.ac.unsyiah.android.absen_mobile.R;
import id.ac.unsyiah.android.absen_mobile.Rest.Api;
import id.ac.unsyiah.android.absen_mobile.Rest.RetrofitClient;
import id.ac.unsyiah.android.absen_mobile.SharedPreferences.DataStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private ImageView bg_main;
    private TextView tvNoMk;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Api apiModel;
    private DataStore dataStore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        bg_main = root.findViewById(R.id.bg_main);
        recyclerView = root.findViewById(R.id.recycler_main);
        swipeRefreshLayout = root.findViewById(R.id.swipe_layout);
        tvNoMk = root.findViewById(R.id.tv_no_mk);
        progressBar = root.findViewById(R.id.pb_mainactivity);

        Glide.with(this).load(R.drawable.background_main).into(bg_main);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        apiModel = RetrofitClient.getInstance().getApi();
        // buat object database
        dataStore= new DataStore(getActivity());

        refreshListMatkul();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListMatkul();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void refreshListMatkul() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dayClient = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        progressBar.setVisibility(View.VISIBLE);
        Log.e("NIP", dataStore.getNip());
        Log.e("dayClient", dayClient);
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
                            mainAdapter = new MainAdapter(mkList, getContext());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mainAdapter);
                        }
                    } else {
                        tvNoMk.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to..", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<MatakuliahResponse> call, Throwable t) {
                Toast.makeText(getContext(), getResources().getString(R.string.failedConnect), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}