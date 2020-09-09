package id.ac.unsyiah.android.absen_mobile.Activity.Response;

import com.google.gson.annotations.SerializedName;

import id.ac.unsyiah.android.absen_mobile.Model.DataDosen;

public class LoginResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("token")
    private String token;

    @SerializedName("data")
    private DataDosen data;

    public LoginResponse(String message, DataDosen dataDosen) {
        this.message = message;
        this.data = dataDosen;
    }

    public String getMessage() {
        return message;
    }

    public DataDosen getDataDosen() {
        return data;
    }

    public String getToken() {
        return token;
    }

    public boolean isSuccess(){
        return message.equals("success");
    }
}
