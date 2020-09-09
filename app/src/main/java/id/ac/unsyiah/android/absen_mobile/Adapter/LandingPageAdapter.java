package id.ac.unsyiah.android.absen_mobile.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.ac.unsyiah.android.absen_mobile.R;

public class LandingPageAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public LandingPageAdapter(Context context){
        this.context = context;
    }

    public int[] slideImg = {
      R.drawable.slider1,
      R.drawable.slider3,
      R.drawable.slider2,
      R.drawable.slider4
    };

    public String[] slideHeading = {
            "Please follow the steps below:",
            "1. Log In",
            "2. Turn on Bluetooth",
            "3. Wait until your attendance proccess is completed"
    };

    public String[] slideDescription = {
            "This application only work if you are in the Informatics buildings.",
            "Input your Employee ID and password according to your SIMPEG Unsyiah account.",
            "Turn on your device's Bluetooth.",
            "Do not close the application while attendance is running."
    };

    @Override
    public int getCount(){
        return slideHeading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.iv_landing_page);
        TextView slideHeadingView = ( TextView) view.findViewById(R.id.heading_landing_page);
        TextView slideDescriptionView = ( TextView) view.findViewById(R.id.slideDescription);

        slideImageView.setImageResource(slideImg[position]);
        slideHeadingView.setText(slideHeading[position]);
        slideDescriptionView.setText(slideDescription[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout) object);
    }

}
