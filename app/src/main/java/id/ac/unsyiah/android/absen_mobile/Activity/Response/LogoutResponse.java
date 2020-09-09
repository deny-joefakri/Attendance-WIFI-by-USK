package id.ac.unsyiah.android.absen_mobile.Activity.Response;

import com.google.gson.annotations.SerializedName;

public class LogoutResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private String value;

    public LogoutResponse(String message, String value) {
        this.message = message;
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public String getValue() {
        return value;
    }
}
