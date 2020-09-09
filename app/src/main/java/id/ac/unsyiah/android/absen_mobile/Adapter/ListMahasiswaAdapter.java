package id.ac.unsyiah.android.absen_mobile.Adapter;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import id.ac.unsyiah.android.absen_mobile.Model.Mahasiswa;

import id.ac.unsyiah.android.absen_mobile.R;


public class ListMahasiswaAdapter extends RecyclerView.Adapter<ListMahasiswaAdapter.MahasiswaViewHolder>{
    private ArrayList<Mahasiswa> mhsList;


    public ListMahasiswaAdapter(ArrayList<Mahasiswa> mhsList){
        this.mhsList = mhsList;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_mhs, viewGroup, false);
        return new MahasiswaViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder mahasiswaViewHolder, final int i) {
        mahasiswaViewHolder.tvNpmMhs.setText(mhsList.get(i).getNpm());
        mahasiswaViewHolder.tvNamaMhs.setText(mhsList.get(i).getNama());
        mahasiswaViewHolder.tvzNomor.setText((i+1)+".");


        Log.d("puscantik", mhsList.get(i).getNama());
    }

    @Override
    public int getItemCount() {
        return (mhsList != null)? mhsList.size(): 0;
    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNpmMhs, tvNamaMhs, tvzNomor;

        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNpmMhs = itemView.findViewById(R.id.tv_npm_mhs);
            tvNamaMhs = itemView.findViewById(R.id.tv_nama_mhs);
            tvzNomor = itemView.findViewById(R.id.tv_no);



        }
    }
}

