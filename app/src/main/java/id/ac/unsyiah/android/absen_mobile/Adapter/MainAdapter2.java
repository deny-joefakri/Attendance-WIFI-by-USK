package id.ac.unsyiah.android.absen_mobile.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import id.ac.unsyiah.android.absen_mobile.Activity.KonfigurasiAbsenActivity;
import id.ac.unsyiah.android.absen_mobile.Activity.KonfigurasiAbsenNotTodayActivity;
import id.ac.unsyiah.android.absen_mobile.Model.Matakuliah;
import id.ac.unsyiah.android.absen_mobile.R;


public class MainAdapter2 extends RecyclerView.Adapter<MainAdapter2.MatkulViewHolder>{
    private ArrayList<Matakuliah> mkList;
    private Context context;


    public MainAdapter2(ArrayList<Matakuliah> mkList, Context context){
        this.mkList = mkList;
        this.context = context;
    }

    @NonNull
    @Override
    public MatkulViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_list2, viewGroup, false);
        return new MatkulViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MatkulViewHolder matkulViewHolder, final int i) {
        matkulViewHolder.tvKdmk.setText(mkList.get(i).getKd_mk());
        matkulViewHolder.tvMk.setText(mkList.get(i).getNama_mk());
        matkulViewHolder.tvJadwal.setText(mkList.get(i).getHari()+", "+mkList.get(i).getJam_masuk()+ " - " +mkList.get(i).getJam_keluar());
        matkulViewHolder.tvRuang.setText(mkList.get(i).getRuang());

        String jamMulai = mkList.get(i).getJam_masuk(); // mengambil jam mulai matakuliah di server.
        String splitTime[] = jamMulai.split(":"); // split format jam mulai
        int hour = Integer.parseInt(splitTime[0]); // ambil jam
        int minute = Integer.parseInt(splitTime[1]); // ambil menit

//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, hour);
//        cal.set(Calendar.MINUTE, minute);
//        cal.set(Calendar.SECOND, 0);
//
//        long millisJamMasuk = cal.getTimeInMillis();
//
//        long now = System.currentTimeMillis();
//
//        long diff = millisJamMasuk - now;
//
//        if (diff <= 3600000 && diff >= -3600000) {
//            matkulViewHolder.sideColorLayout.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
//        }

//        Log.d("puscantik", mkList.get(i).getKd_mk());

        matkulViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), KonfigurasiAbsenNotTodayActivity.class);
                intent.putExtra("kd_mk", mkList.get(i).getKd_mk());
                v.getContext().startActivity(intent);
            }
        });



        matkulViewHolder.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), KonfigurasiAbsenNotTodayActivity.class);
                intent.putExtra("kd_mk", mkList.get(i).getKd_mk());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (mkList != null)? mkList.size(): 0;
    }

    public class MatkulViewHolder extends RecyclerView.ViewHolder {
        private TextView tvKdmk, tvMk, tvJadwal, tvRuang;
        private LinearLayout linearLayout;
        private ImageView imgNext;
        private LinearLayout sideColorLayout;

        public MatkulViewHolder(@NonNull View itemView) {
            super(itemView);

            tvKdmk = itemView.findViewById(R.id.tv_kdmk);
            tvMk = itemView.findViewById(R.id.tv_mk);
            tvJadwal = itemView.findViewById(R.id.tv_jadwal);
            tvRuang = itemView.findViewById(R.id.tv_ruang);
            linearLayout = itemView.findViewById(R.id.linear_listmk);
            imgNext = itemView.findViewById(R.id.iv_next);
            sideColorLayout = itemView.findViewById(R.id.sideColor);


        }
    }
}

