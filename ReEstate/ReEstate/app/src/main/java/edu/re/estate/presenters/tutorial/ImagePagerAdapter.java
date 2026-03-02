package edu.re.estate.presenters.tutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.re.estate.R;

public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ViewHolder> {

    private final Context context;
    private final List<Integer> imageResIds;

    public ImagePagerAdapter(Context context, List<Integer> imageResIds) {
        this.context = context;
        this.imageResIds = imageResIds;
    }

    @NonNull
    @Override
    public ImagePagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagePagerAdapter.ViewHolder holder, int position) {
        int resId = imageResIds.get(position);
        holder.imageView.setImageResource(resId);
    }

    @Override
    public int getItemCount() {
        return imageResIds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
