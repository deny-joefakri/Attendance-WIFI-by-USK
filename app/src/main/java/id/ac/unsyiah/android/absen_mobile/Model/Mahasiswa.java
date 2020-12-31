package id.ac.unsyiah.android.absen_mobile.Model;

import com.google.gson.annotations.SerializedName;


public class Mahasiswa {


    @SerializedName("npm")
    private String npm;
    @SerializedName("nama")
    private String nama;
    @SerializedName("kd_mk")
    private String kd_mk;
    @SerializedName("attendance_status")
    private String attendance_status;


    public String getNpm() {
        return npm;
    }

    public String getNama() {
        return nama;
    }

    public String getKode_mk() {
        return kd_mk;
    }

    public Mahasiswa(String npm, String nama, String kd_mk, String attendance_status) {
        this.npm = npm;
        this.nama = nama;
        this.kd_mk = kd_mk;
        this.attendance_status = attendance_status;
    }

    public String getAttendance_status() {
        return attendance_status;
    }

    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status;
    }
}
