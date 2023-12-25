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

import tdtu.report.Model.CardItem2;
import tdtu.report.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<CardItem2> dataList; // Thay YourDataModel bằng lớp dữ liệu thực của bạn

    public HomeAdapter(List<CardItem2> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardItem2 data = dataList.get(position);
        holder.ivItemImage.setImageResource(data.getImageView());
        holder.tvTitle.setText(data.getSong().getTitle());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivItemImage;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            ivItemImage = itemView.findViewById(R.id.ivItemImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}

