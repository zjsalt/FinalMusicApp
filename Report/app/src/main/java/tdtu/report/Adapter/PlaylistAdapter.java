package tdtu.report.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tdtu.report.Model.Playlist;
import tdtu.report.R;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    private List<Playlist> playlistList;

    public PlaylistAdapter(List<Playlist> playlistList) {
        this.playlistList = playlistList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = playlistList.get(position);
        holder.titleTextView.setText(playlist.getName());
        // Các công việc khác như hiển thị hình ảnh của Playlist có thể được thêm vào đây.
    }

    @Override
    public int getItemCount() {
        return playlistList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView titleTextView;
        private TextView subtitleTextView;
        Button btMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            // Đối với danh sách Playlist, bạn có thể thêm ImageView để hiển thị hình ảnh của Playlist (tương tự như hiển thị hình ảnh của Song).
        }
    }
}
