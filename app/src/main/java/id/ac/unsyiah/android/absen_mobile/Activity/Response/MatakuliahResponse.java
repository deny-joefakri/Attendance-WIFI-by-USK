package id.ac.unsyiah.android.absen_mobile.Activity.Response;

import com.google.gson.annotations.SerializedName;

import id.ac.unsyiah.android.absen_mobile.Model.Matakuliah;

public class MatakuliahResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("dataMk")
    private Matakuliah[] dataMk;

    public String getMessage() {
        return message;
    }

    public Matakuliah[] getDataMk() {
        return dataMk;
    }

    public boolean isSuccess(){
        return message.equals("success");
    }


}
