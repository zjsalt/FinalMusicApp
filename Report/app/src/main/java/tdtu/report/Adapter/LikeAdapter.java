package tdtu.report.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tdtu.report.Model.Song;
import tdtu.report.R;
public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder> {

    List<Song> dataList;
    public LikeAdapter(List<Song> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_song_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song data = dataList.get(position);
//        holder.ivSongImage.setImageResource(data.getImage());
        String songTitle = data.getTitle();

        holder.titleTextView.setText(data.getTitle());
//        holder.subtitleTextView.setText(data.getArtist().getId());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        ImageView ivSongImage;
        private TextView titleTextView;
        private TextView subtitleTextView;
        Button btMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
//            ivSongImage = itemView.findViewById(R.id.ivSongImage);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
        }

    }
}