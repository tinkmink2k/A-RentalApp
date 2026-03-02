package edu.re.estate.presenters.tutorial;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

import edu.re.estate.presenters.main.MainActivity;
import edu.re.estate.R;
import edu.re.estate.presenters.auth.AuthActivity;
import edu.re.estate.databinding.ActivityTutorialBinding;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityTutorialBinding binding = ActivityTutorialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<Integer> images = Arrays.asList(
                R.drawable.tutorial_01,
                R.drawable.tutorial_02,
                R.drawable.tutorial_03
        );

        ImagePagerAdapter adapter = new ImagePagerAdapter(this, images);
        binding.viewPager.setAdapter(adapter);

        binding.layoutSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        });
        binding.tvGetStarted.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
