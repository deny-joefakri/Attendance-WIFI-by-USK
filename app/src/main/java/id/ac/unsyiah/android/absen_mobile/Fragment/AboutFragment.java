package id.ac.unsyiah.android.absen_mobile.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import id.ac.unsyiah.android.absen_mobile.R;


public class AboutFragment extends Fragment {

    private ImageView bg_main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_about, container, false);
        bg_main = root.findViewById(R.id.bg_main);
        final TextView textView = root.findViewById(R.id.text_home);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Glide.with(this).load(R.drawable.background_main).into(bg_main);
    }
}