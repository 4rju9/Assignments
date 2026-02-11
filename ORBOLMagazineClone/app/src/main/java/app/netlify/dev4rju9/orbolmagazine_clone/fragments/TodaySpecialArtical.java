package app.netlify.dev4rju9.orbolmagazine_clone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import org.json.JSONObject;
import app.netlify.dev4rju9.orbolmagazine_clone.R;
import app.netlify.dev4rju9.orbolmagazine_clone.SplashScreen;
import app.netlify.dev4rju9.orbolmagazine_clone.TodaySpecialViewPagerAdaper;
import app.netlify.dev4rju9.orbolmagazine_clone.databinding.FragmentTodaySpecialArticalBinding;

public class TodaySpecialArtical extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_today_special_artical, container, false);
        FragmentTodaySpecialArticalBinding binding = FragmentTodaySpecialArticalBinding.bind(view);

        try {
            JSONObject json = SplashScreen.fetchedData
                    .getJSONArray("special")
                    .getJSONObject(TodaySpecialViewPagerAdaper.POS);

            binding.specialTitle.setText(json.getString("title"));
            binding.specialSubText.setText(json.getString("subtext"));
            binding.specialText.setText(json.getString("text"));

            Glide.with(requireContext())
                    .load(json.getString("url"))
                    .centerCrop()
                    .into(binding.specialImageView);

        } catch (Exception ingore) {}

        return view;
    }

}