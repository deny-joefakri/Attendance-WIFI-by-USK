package id.ac.unsyiah.android.absen_mobile.Adapter;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.ac.unsyiah.android.absen_mobile.Model.Mahasiswa;

import id.ac.unsyiah.android.absen_mobile.R;


public class ListMahasiswaAdapter extends RecyclerView.Adapter<ListMahasiswaAdapter.MahasiswaViewHolder>{
    private ArrayList<Mahasiswa> mhsList;
    private Context context;
    private Listener listener;

    public ListMahasiswaAdapter(ArrayList<Mahasiswa> mhsList, Context context, Listener listener){
        this.mhsList = mhsList;
        this.context = context;
        this.listener = listener;
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

        if (mhsList.get(i).getAttendance_status().equals("0")){
            mahasiswaViewHolder.view_button.setVisibility(View.VISIBLE);
            mahasiswaViewHolder.img.setVisibility(View.GONE);
        } else {
            mahasiswaViewHolder.view_button.setVisibility(View.GONE);
            mahasiswaViewHolder.img.setVisibility(View.VISIBLE);
        }

        if (mhsList.get(i).getAttendance_status().equals("2")){
            Glide.with(context).load(R.drawable.ic_close).into(mahasiswaViewHolder.img);
        } else Glide.with(context).load(R.drawable.ic_correct).into(mahasiswaViewHolder.img);

        mahasiswaViewHolder.btn_hadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick("1", mhsList.get(i).getNpm());
            }
        });

        mahasiswaViewHolder.btn_no_hadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick("2", mhsList.get(i).getNpm());
            }
        });

//        if (mhsList.get(i).getAttendance_status().equals("0")){
//            mahasiswaViewHolder.view_button.setVisibility(View.GONE);
//            Glide.with(context).load(R.drawable.ic_close).into(mahasiswaViewHolder.img);
//        } else Glide.with(context).load(R.drawable.ic_correct).into(mahasiswaViewHolder.img);

        Log.d("puscantik", mhsList.get(i).getNama());
    }

    @Override
    public int getItemCount() {
        return (mhsList != null)? mhsList.size(): 0;
    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        private Button btn_hadir, btn_no_hadir;
        private TextView tvNpmMhs, tvNamaMhs, tvzNomor;
        private ImageView img;
        private LinearLayout view_button;

        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_hadir = itemView.findViewById(R.id.btn_hadir);
            btn_no_hadir = itemView.findViewById(R.id.btn_no_hadir);
            tvNpmMhs = itemView.findViewById(R.id.tv_npm_mhs);
            tvNamaMhs = itemView.findViewById(R.id.tv_nama_mhs);
            tvzNomor = itemView.findViewById(R.id.tv_no);
            view_button = itemView.findViewById(R.id.view_button);
            img = itemView.findViewById(R.id.img);

        }
    }

    public interface Listener {
        void onItemClick(String value, String npm);
    }
}

