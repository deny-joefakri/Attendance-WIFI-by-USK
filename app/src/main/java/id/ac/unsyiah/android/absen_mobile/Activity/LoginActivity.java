package id.ac.unsyiah.android.absen_mobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.ac.unsyiah.android.absen_mobile.SharedPreferences.DataStore;
import id.ac.unsyiah.android.absen_mobile.Activity.Response.LoginResponse;
import id.ac.unsyiah.android.absen_mobile.Rest.RetrofitClient;
import id.ac.unsyiah.android.absen_mobile.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtNip, edtPass;
    TextView btnLogin;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login5);

        edtNip = findViewById(R.id.edt_nip);
        edtPass = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        pb = findViewById(R.id.pb);

        ImageView bg_main = findViewById(R.id.bg_main);
        Glide.with(this).load(R.drawable.img_login).into(bg_main);
    }

    public void btnLogin(View view) {
        userLogin();
    }

    private void userLogin() {
        String nip = edtNip.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();

        if(nip.isEmpty()){
            edtNip.setError("Required!");
            edtNip.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            edtPass.setError("Required!");
            edtPass.requestFocus();
            return;
        }

        pb.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.INVISIBLE);

       Call <LoginResponse> call = RetrofitClient.getInstance().getApi()
               .login(nip, pass);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                pb.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);

                if(loginResponse.isSuccess()){
                    DataStore ts = new DataStore(LoginActivity.this);
//                    Log.d("puscantik", loginResponse.getDataDosen().getNama());
                    ts.setToken(loginResponse.getToken());
                    ts.setEmail(loginResponse.getDataDosen().getEmail());
                    ts.setNama(loginResponse.getDataDosen().getNama());
                    ts.setNip(loginResponse.getDataDosen().getNip());

                    Toast.makeText(LoginActivity.this, "You have signed in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainDrawerActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
                else{
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.d("apa", "errror");

                    edtNip.setText("");
                    edtPass.setText("");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
