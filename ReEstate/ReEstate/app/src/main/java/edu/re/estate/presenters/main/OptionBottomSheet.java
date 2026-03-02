package edu.re.estate.presenters.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import edu.re.estate.R;
import edu.re.estate.presenters.main.adapter.OptionAdapter;

public class OptionBottomSheet extends BottomSheetDialog {

    public interface OnSelectListener {
        void onSelected(String value);
    }

    public OptionBottomSheet(@NonNull Context context,
                             List<String> options,
                             String defaultValue,
                             OnSelectListener listener) {
        super(context);

        @SuppressLint("InflateParams") View view = LayoutInflater.from(context)
                .inflate(R.layout.bottomsheet_option, null);
        setContentView(view);

        RecyclerView rv = view.findViewById(R.id.rvOptions);
        rv.setLayoutManager(new LinearLayoutManager(context));

        int defaultPos = options.indexOf(defaultValue);
        if (defaultPos == -1) defaultPos = 0;

        OptionAdapter adapter = new OptionAdapter(
                options,
                defaultPos,
                value -> {
                    listener.onSelected(value);
                    dismiss();
                }
        );

        rv.setAdapter(adapter);
    }
}
