package edu.re.estate.presenters.main.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.re.estate.R;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {

    private final List<String> data;
    private int selectedPosition = 0;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String value);
    }

    public OptionAdapter(List<String> data, int defaultPos, OnItemClickListener listener) {
        this.data = data;
        this.selectedPosition = defaultPos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bottomsheet_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTitle.setText(data.get(position));
        holder.imgCheck.setVisibility(
                position == selectedPosition ? View.VISIBLE : View.GONE
        );

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            listener.onItemClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imgCheck;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgCheck = itemView.findViewById(R.id.imgCheck);
        }
    }
}
