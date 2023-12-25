package tdtu.report.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import tdtu.report.Adapter.HomeAdapter;
import tdtu.report.Model.CardItem2;
import tdtu.report.R;
import tdtu.report.Utils.HomeUtil;

public class HomeFragment extends Fragment {

    private FrameLayout frameLayout;
    private View rootView;
    private View fragmentHomeView;
    private RecyclerView rvRecently;
    private RecyclerView rvPlaylist;
    private RecyclerView rvAlbum;
    private RecyclerView rvArtist;
    private HomeAdapter adapter;
    private LinearLayout BlockLike;
    private HomeUtil homeUtil;
    private LikeFragment likeFragment;
    private FrameLayout fragmentContainer;
    private List<CardItem2> dataList;

    public HomeFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Tìm FrameLayout trong home.xml
        frameLayout = rootView.findViewById(R.id.main_menu);

        // Inflate layout fragment_home.xml và add vào FrameLayout
        fragmentHomeView = inflater.inflate(R.layout.fragment_home, frameLayout, true);

        rvRecently = rootView.findViewById(R.id.rvRecently);
        rvPlaylist = rootView.findViewById(R.id.rvPlaylist);
        rvAlbum = rootView.findViewById(R.id.rvAlbum);
        rvArtist = rootView.findViewById(R.id.rvArtist);

        rvRecently.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeAdapter(dataList);
        rvRecently.setAdapter(adapter);
        BlockLike = rootView.findViewById(R.id.BlockLike);

        likeFragment = new LikeFragment();
        // Khởi tạo HomeUtil
        homeUtil = new HomeUtil(requireContext(), likeFragment, fragmentContainer);

        // Thiết lập fragmentContainer cho HomeFragment
        homeUtil.setFragmentContainer(fragmentContainer);
        CardView cvLikedSong = rootView.findViewById(R.id.cvLikedSong);
        BlockLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang LikeFragment khi click vào BlockLike
                if (homeUtil != null) {
                    homeUtil.showLikeFragment();
                }
            }
        });

        return rootView;
    }
}
