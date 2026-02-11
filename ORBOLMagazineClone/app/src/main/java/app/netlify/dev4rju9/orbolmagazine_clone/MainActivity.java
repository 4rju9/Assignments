package app.netlify.dev4rju9.orbolmagazine_clone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.WindowManager;
import com.bumptech.glide.Glide;
import org.json.JSONObject;
import app.netlify.dev4rju9.orbolmagazine_clone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sectionOne();
        sectionTwo();
        sectionThree();
        sectionFour();

    }

    private void sectionOne () {

        try {
            TodaySpecialViewPagerAdaper adapter = new TodaySpecialViewPagerAdaper(this, SplashScreen.fetchedData.getJSONArray("special"));
            binding.todaySpecial.todaySpecialViewpager.setAdapter(adapter);

            binding.todaySpecial.indicatorView
                    .setPageSize(adapter.getItemCount())
                    .setSliderWidth(130)
                    .setSliderHeight(8)
                    .setSliderGap(20)
                    .setupWithViewPager(binding.todaySpecial.todaySpecialViewpager);
        } catch (Exception ignore) {}

    }

    private void sectionTwo () {

        try {

            JSONObject json = SplashScreen.fetchedData.getJSONObject("featured");

            Glide.with(this)
                    .load(json.getString("url"))
                    .centerCrop()
                    .into(binding.featuredAd.featuredImageView);

            binding.featuredAd.featuredTitle.setText(json.getString("title"));
            binding.featuredAd.featuredText.setText(json.getString("text"));
            binding.featuredAd.buttonFeatured
                    .setOnClickListener( v -> SplashScreen.makeToast(this, "Clicked"));

        } catch (Exception ignore) {}

    }

    private void sectionThree () {

        try {

            JSONObject json = SplashScreen.fetchedData.getJSONObject("normal");

            Glide.with(this)
                    .load(json.getString("url"))
                    .centerCrop()
                    .into(binding.normalAd.normalBg);

            binding.normalAd.normalTitle.setText(json.getString("title"));
            binding.normalAd.normalText.setText(json.getString("text"));
            binding.normalAd.normalSubText.setText(json.getString("subtext"));
            binding.normalAd.buttonNormal
                    .setOnClickListener( v -> SplashScreen.makeToast(this, "Clicked"));

        } catch (Exception ignore) {}

    }

    private void sectionFour () {

        try {
            AllArticlesAdapter adapter = new AllArticlesAdapter(this, SplashScreen.fetchedData.getJSONArray("articles"));
            binding.allArticles.dateView.setText(SplashScreen.fetchedData.getString("date"));
            binding.allArticles.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.allArticles.recyclerView.setAdapter(adapter);
            binding.allArticles.buttonAllArticles
                    .setOnClickListener( v -> SplashScreen.makeToast(this, "Clicked"));
        } catch (Exception ignore) {}

    }

}