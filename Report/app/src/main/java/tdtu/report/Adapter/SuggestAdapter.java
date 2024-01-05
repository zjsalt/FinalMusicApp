//package tdtu.report.Adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import tdtu.report.Model.Song;
//import tdtu.report.R;
//
//public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.ViewHolder> {
//
//    private List<Song> dataList;
//    private OnItemClickListener mListener;
//
//    public interface OnItemClickListener {
//        void onItemClick(int position, Song song);
//        void onItemClickedForPlayMusic(Song song);
//    }
//
//    public SuggestAdapter(List<Song> dataList, OnItemClickListener listener) {
//        this.dataList = dataList;
//        this.mListener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_song_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.bindData(dataList.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private CardView cardView;
//        private ImageView ivSongImage;
//        private TextView titleTextView;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            cardView = itemView.findViewById(R.id.cardView);
//            ivSongImage = itemView.findViewById(R.id.ivSongImage);
//            titleTextView = itemView.findViewById(R.id.titleTextView);
//        }
//
//        public void bindData(Song song) {
//            titleTextView.setText(song.getTitle());
//
//            // Set click listener for itemView
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int adapterPosition = getAdapterPosition();
//                    if (adapterPosition != RecyclerView.NO_POSITION && mListener != null) {
//                        mListener.onItemClick(adapterPosition, dataList.get(adapterPosition));
//                        mListener.onItemClickedForPlayMusic(dataList.get(adapterPosition));
//                    }
//                }
//            });
//        }
//    }
//
//    // Update data for the Adapter
//    public void setDataList(List<Song> newDataList) {
//        dataList = newDataList;
//        notifyDataSetChanged();
//    }
//}

package tdtu.report.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tdtu.report.Model.Song;
import tdtu.report.R;

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.ViewHolder> {

    private List<Song> dataList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position, Song song);
        void onItemClickedForPlayMusic(Song song);
    }

    public SuggestAdapter(List<Song> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.mListener = listener;
    }
    public void setFilteredList(List<Song> filteredList){
        this.dataList =filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_song_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView ivSongImage;
        private TextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            ivSongImage = itemView.findViewById(R.id.ivSongImage);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }

        public void bindData(Song song) {
            titleTextView.setText(song.getTitle());

            // Set click listener for itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION && mListener != null) {
                        mListener.onItemClick(adapterPosition, dataList.get(adapterPosition));
                        mListener.onItemClickedForPlayMusic(dataList.get(adapterPosition));
                    }
                }
            });
        }
    }

    // Update data for the Adapter
    public void setDataList(List<Song> newDataList) {
        dataList = newDataList;
        notifyDataSetChanged();
    }
}
