package id.ac.unsyiah.android.absen_mobile.Activity.Response;

import com.google.gson.annotations.SerializedName;

public class AbsenDosenResponse {
    @SerializedName("message")
    private String message;

    public boolean isSuccess(){
        return message.equals("success");
    }

    public String getMessage(){
        return message;
    }
}
