package app.netlify.dev4rju9.netclanExplorerClone.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import app.netlify.dev4rju9.netclanExplorerClone.fragments.BusinessFragment;
import app.netlify.dev4rju9.netclanExplorerClone.fragments.MerchantFragment;
import app.netlify.dev4rju9.netclanExplorerClone.fragments.PersonalFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: {
                return new BusinessFragment();
            } case 2: {
                return new MerchantFragment();
            } default: {
                return new PersonalFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
