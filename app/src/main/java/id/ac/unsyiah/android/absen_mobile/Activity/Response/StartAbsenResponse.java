package id.ac.unsyiah.android.absen_mobile.Activity.Response;

import com.google.gson.annotations.SerializedName;

public class StartAbsenResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("id")
    private int id;

    @SerializedName("jumlah_absen")
    private int jumlah_absen;

    @SerializedName("jam_mulai")
    private String jam_mulai;

    @SerializedName("jam_keluar")
    private String jam_keluar;

    @SerializedName("ruang")
    private int idRuang;

    public boolean isSuccess(){
        return message.equals("success");
    }

    public String getMessage(){
        return message;
    }

    public int getId(){
        return id;
    }
    public int getIdRuang() {
        return idRuang;
    }

    public int getJumlahAbsen(){
        return jumlah_absen;
    }

    public String getJamMulai(){
        return jam_mulai;
    }

    public String getJamKeluar(){
        return jam_keluar;
    }
}
