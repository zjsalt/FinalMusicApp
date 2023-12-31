//package tdtu.report.ClickListener;
//
//import android.content.Context;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import tdtu.report.Adapter.SuggestAdapter;
//import tdtu.report.Model.Song;
//
////public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
////
////    private OnItemClickListener mListener;
////    private GestureDetector mGestureDetector;
////
////    public interface OnItemClickListener {
////        void onItemClick(View view, int position, Song song);
////        void onLongItemClick(View view, int position);
////    }
////
////    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
////        mListener = listener;
////        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
////            @Override
////            public boolean onSingleTapUp(MotionEvent e) {
////                return false;
////            }
////
////            @Override
////            public void onLongPress(MotionEvent e) {
////                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
////                if (child != null && mListener != null) {
////                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
////                }
////            }
////        });
////    }
////
////    @Override
////    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent e) {
////        View childView = view.findChildViewUnder(e.getX(), e.getY());
////        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
////            int position = view.getChildAdapterPosition(childView);
////            Song selectedSong = ((SuggestAdapter) view.getAdapter()).getSongAtPosition(position);
////            mListener.onItemClick(childView, position, selectedSong);
////            return true;
////        }
////        return false;
////    }
////
////    @Override
////    public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {
////        // Không cần thực hiện gì cả trong phương thức này
////    }
////
////    @Override
////    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
////        // Không cần thực hiện gì cả trong phương thức này
////    }
////}
//public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
//
//    private OnItemClickListener mListener;
//    private GestureDetector mGestureDetector;
//
//    public interface OnItemClickListener {
//        void onItemClick(int position, Song song);
//        void onLongItemClick(int position);
//    }
//
//    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
//        mListener = listener;
//        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public void onLongPress(MotionEvent e) {
//                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                if (child != null && mListener != null) {
//                    mListener.onLongItemClick(recyclerView.getChildAdapterPosition(child));
//                }
//            }
//        });
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent e) {
//        View childView = view.findChildViewUnder(e.getX(), e.getY());
//        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
//            int position = view.getChildAdapterPosition(childView);
//            Song selectedSong = ((SuggestAdapter) view.getAdapter()).getSongAtPosition(position);
//            mListener.onItemClick(position, selectedSong);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {
//        // Do nothing in this method
//    }
//
//    @Override
//    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//        // Do nothing in this method
//    }
//}
//
//
package tdtu.report.ClickListener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            int position = view.getChildAdapterPosition(childView);
            mListener.onItemClick(childView, position);
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {
        // Không cần thực hiện gì cả trong phương thức này
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // Không cần thực hiện gì cả trong phương thức này
    }
}

