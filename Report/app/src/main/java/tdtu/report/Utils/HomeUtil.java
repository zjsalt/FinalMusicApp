package tdtu.report.Utils;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import tdtu.report.Fragment.LikeFragment;

public class HomeUtil {

    private Context context;
    private LikeFragment likeFragment;
    private FrameLayout fragmentContainer;

    public HomeUtil(Context context, LikeFragment likeFragment, FrameLayout fragmentContainer) {
        this.context = context;
        this.likeFragment = likeFragment;
        this.fragmentContainer = fragmentContainer;
    }

    public void showLikeFragment() {
        // Thêm LikeFragment vào FragmentContainer
        if (likeFragment != null && fragmentContainer != null) {
            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(fragmentContainer.getId(), likeFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void setFragmentContainer(FrameLayout fragmentContainer) {
        this.fragmentContainer = fragmentContainer;
    }
}
