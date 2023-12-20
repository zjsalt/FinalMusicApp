package tdtu.report.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import tdtu.report.R;

public class ProfileFragment extends Fragment {

    FrameLayout frameLayout;
    View rootView;
    View fragmentProfileView;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Tìm FrameLayout trong home.xml
        frameLayout = rootView.findViewById(R.id.main_menu);

        // Inflate layout fragment_home.xml và add vào FrameLayout
        fragmentProfileView = inflater.inflate(R.layout.fragment_profile, frameLayout, true);

        return rootView;
    }
}