package id.ac.unsyiah.android.absen_mobile.Model;

import com.google.gson.annotations.SerializedName;


public class Mahasiswa {


    @SerializedName("npm")
    private String npm;
    @SerializedName("nama")
    private String nama;
    @SerializedName("kd_mk")
    private String kd_mk;


    public Mahasiswa(String npm, String nama, String kd_mk) {
        this.npm = npm;
        this.nama = nama;
        this.kd_mk = kd_mk;

    }

    public String getNpm() {
        return npm;
    }

    public String getNama() {
        return nama;
    }

    public String getKode_mk() {
        return kd_mk;
    }
}
