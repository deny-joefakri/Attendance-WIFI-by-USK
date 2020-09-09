package id.ac.unsyiah.android.absen_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Matakuliah {
    @SerializedName("kd_mk")
    private String kd_mk;

    @SerializedName("nama_mk")
    private String nama_mk;

    @SerializedName("hari")
    private String hari;

    @SerializedName("jam_masuk")
    private String jam_masuk;

    @SerializedName("jam_keluar")
    private String jam_keluar;

    @SerializedName("ruang")
    private String ruang;

    @SerializedName("sks")
    private String sks;

    @SerializedName("jumlah_mhs")
    private String jumlah_mhs;

    public String getKd_mk() {
        return kd_mk;
    }

    public String getNama_mk() {
        return nama_mk;
    }

    public String getHari() {
        return hari;
    }

    public String getJam_masuk() {
        return jam_masuk;
    }

    public String getJam_keluar() {
        return jam_keluar;
    }

    public String getRuang() {
        return ruang;
    }

    public String getSks() {
        return sks;
    }

    public String getJumlah_mhs() {
        return jumlah_mhs;
    }
}
