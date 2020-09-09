package id.ac.unsyiah.android.absen_mobile.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.ac.unsyiah.android.absen_mobile.Adapter.LandingPageAdapter;
import id.ac.unsyiah.android.absen_mobile.R;


public class LandingPageActivity extends AppCompatActivity implements LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    private ViewPager vpSlider;
    private LinearLayout layoutLandingpage;
    private TextView[] tvDots;
    private Button btnBack, btnNext;

    private LandingPageAdapter landingPageAdapter;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        vpSlider = findViewById(R.id.vpLandingPage);
        layoutLandingpage = findViewById(R.id.linear_landing_page);
        btnBack = findViewById(R.id.btn_back_landing_page);
        btnNext = findViewById(R.id.btn_next_landing_page);

        landingPageAdapter = new LandingPageAdapter(this);

        vpSlider.setAdapter(landingPageAdapter);
        addTextViewIndicator(0);
        vpSlider.addOnPageChangeListener(viewListener);

        //ON CLICK LISTENER
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpSlider.setCurrentItem(currentPage + 1);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpSlider.setCurrentItem(currentPage - 1);
            }
        });

        // Aplikasi meminta izin untuk menghidupkan lokasi pada perangkat.
        if(ContextCompat.checkSelfPermission(LandingPageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(LandingPageActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

//                new AlertDialog.Builder(this)
//                        .setTitle(R.string.title_location_permission)
//                        .setMessage(R.string.text_location_permission)
//                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).create().show();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    public void addTextViewIndicator(int position){

        tvDots = new TextView[4];
        layoutLandingpage.removeAllViews();

        for(int i = 0; i< tvDots.length; i++){

            tvDots[i] = new TextView(this);
            tvDots[i].setText(Html.fromHtml("&#8226;"));
            tvDots[i].setTextSize(35);
            tvDots[i].setTextColor(getResources().getColor(R.color.colorLanding));

            layoutLandingpage.addView(tvDots[i]);
        }

        if(tvDots.length > 0 ){
             tvDots[position].setTextColor(getResources().getColor(R.color.colorDotsLanding));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
                addTextViewIndicator(i);

                currentPage = i;

                if(i == 0){
                    btnNext.setEnabled(true);
                    btnBack.setEnabled(false);
                    btnBack.setVisibility(View.INVISIBLE);

                    btnNext.setText("Next");
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vpSlider.setCurrentItem(currentPage + 1);
                        }
                    });
                    btnBack.setText("");
                }else if(i == tvDots.length - 1){
                    btnNext.setEnabled(true);
                    btnBack.setEnabled(true);
                    btnBack.setVisibility(View.VISIBLE);

                    btnNext.setText("Finish");
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(LandingPageActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    btnBack.setText("Back");
                }else{
                    btnNext.setEnabled(true);
                    btnBack.setEnabled(true);
                    btnBack.setVisibility(View.VISIBLE);

                    btnNext.setText("Next");
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vpSlider.setCurrentItem(currentPage + 1);
                        }
                    });
                    btnBack.setText("Back");
                }
            }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };

    /** Method-method abstract yang dibutuhkan oleh kelas LocationListener. */
    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}


