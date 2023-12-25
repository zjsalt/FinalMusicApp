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

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.ViewHolder> {

    List<Song> dataList;
    public SuggestAdapter(List<Song> dataList) {
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
        Song song = dataList.get(position);
        // Get the Song object for the current position

        // Set the title for the item

//        holder.ivSongImage.setImageResource(data.getImage());
        holder.titleTextView.setText(song.getTitle());
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
    public Song getSongAtPosition(int position) {
        if (dataList != null && position >= 0 && position < dataList.size()) {
            return dataList.get(position);
        }
        return null;
    }
}
