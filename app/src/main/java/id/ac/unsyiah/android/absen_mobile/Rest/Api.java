package id.ac.unsyiah.android.absen_mobile.Rest;


import id.ac.unsyiah.android.absen_mobile.Activity.Response.AbsenDosenResponse;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.LoginResponse;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.LogoutResponse;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.MahasiswaResponse;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.MatakuliahResponse;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.StartAbsenResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("dosen_login.php")
    Call<LoginResponse> login(
            @Field("nip") String nip,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("delete_token.php")
    Call<LogoutResponse> logout(
            @Field("token") String token
    );

    @GET("dosen_mk.php/")
    Call<MatakuliahResponse> getTodayCourse(
            @Query("nip") String nip,
            @Query("hari") String dayClient
    );

    @GET("dosen_mk_nottoday.php/")
    Call<MatakuliahResponse> getNotTodayCourse(
            @Query("nip") String nip,
            @Query("hari") String dayClient
    );

    @FormUrlEncoded
    @POST("dosen_listmk.php")
    Call<MatakuliahResponse> getListMk(
            @Field("kd_mk") String kdmk
    );

    @FormUrlEncoded
    @POST("dosen_listmhs.php")
    Call<MahasiswaResponse> getMahasiswa(
            @Field("kd_mk") String kdmk
    );

    @FormUrlEncoded
    @POST("absen.php")
    Call<StartAbsenResponse> startAbsen(
            @Field("dosen") String nip,
            @Field("kd_mk") String kd_mk,
            @Field("jam_mulai") String jam_mulai
    );

    @FormUrlEncoded
    @POST("absen_dosen.php")
    Call<AbsenDosenResponse> dosenAbsen(
            @Field("id") int id,
            @Field("nip") String nip,
            @Field("status") int status
    );

    @FormUrlEncoded
    @POST("update_mhs_attendance.php")
    Call<MahasiswaResponse> updateMahasiswaStatus(
            @Field("npm") String npm,
            @Field("attendance_status") String attendance_status
    );
}
