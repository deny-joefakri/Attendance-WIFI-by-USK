package id.ac.unsyiah.android.absen_mobile.Activity.Response;

import com.google.gson.annotations.SerializedName;

import id.ac.unsyiah.android.absen_mobile.Model.Mahasiswa;

public class MahasiswaResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Mahasiswa[] data;

    public String getMessage() {
        return message;
    }

    public Mahasiswa[] getMahasiswa() {
        return data;
    }

    public boolean isSuccess(){
        return message.equals("success");
    }
}