package id.ac.unsyiah.android.absen_mobile.SharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class DataStore {
    private SharedPreferences pref; //file dalam hape (sharedReference --> session di android)

    public DataStore(Activity act) { //method konstraktor
        pref = act.getSharedPreferences("DataStore", Context.MODE_PRIVATE);
    }

    public String getToken(){
        String token = pref.getString("token", "");
        return token;
    }

    public void setToken(String token){
        SharedPreferences.Editor edit = pref.edit(); //buka akses utk edit preferences token yg disimpan
        edit.putString("token", token);
        edit.apply();
    }

    public void setNama(String nama){
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("nama", nama);
        edit.apply();
    }

    public void setNip(String nip){
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("nip", nip);
        edit.apply();
    }

    public void setEmail(String email){
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("email", email);
        edit.apply();
    }

    public void setKdmk (String kdmk){
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("kdmk", kdmk);
        edit.apply();
    }

    public String getKdmk(){
        return pref.getString("kdmk", "");
    }


    public String getNama(){
        return pref.getString("nama", "");
    }


    public String getNip(){
        return pref.getString("nip", "");
    }

    public String getEmail(){
        return pref.getString("email", "");
    }

    public boolean hasToken() {
        return !getToken().equals("");
    }

    public void clear() {
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
        edit.apply();
    }
}
