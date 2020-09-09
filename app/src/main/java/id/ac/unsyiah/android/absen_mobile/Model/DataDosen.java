package id.ac.unsyiah.android.absen_mobile.Model;
import com.google.gson.annotations.SerializedName;


public class DataDosen {

    @SerializedName("nip")
    private String nip;
    @SerializedName("nama")
    private String nama;
    @SerializedName("email")
    private String email;


    public DataDosen(String nip, String nama, String email) {
        this.nip = nip;
        this.nama = nama;
        this.email = email;
    }

    public String getNip() {
        return nip;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

}
