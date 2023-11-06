package tdtu.report.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    private int position;

    public static MyFragment newInstance(int position) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    // TODO: Implement onCreateView to inflate the fragment layout and display the content
}