package app.netlify.dev4rju9.orbolmagazine_clone;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import org.json.JSONArray;
import app.netlify.dev4rju9.orbolmagazine_clone.fragments.TodaySpecialArtical;

public class TodaySpecialViewPagerAdaper extends FragmentStateAdapter {

    public static JSONArray DATA;
    public static int POS = 0;

    public TodaySpecialViewPagerAdaper(@NonNull FragmentActivity fragmentActivity, JSONArray data) {
        super(fragmentActivity);
        this.DATA = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        POS = position;
        TodaySpecialArtical fragment = new TodaySpecialArtical();
        return fragment;
    }

    @Override
    public int getItemCount() {
        return DATA.length();
    }
}