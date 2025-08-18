package app.netlify.dev4rju9.netclanExplorerClone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import app.netlify.dev4rju9.netclanExplorerClone.adapters.ViewPagerAdapter;
import app.netlify.dev4rju9.netclanExplorerClone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setCheckedItem(R.id.nav_network);

        binding.toolbar.options.setOnClickListener( v -> {
            if(binding.getRoot().isDrawerOpen(GravityCompat.START))
                binding.getRoot().closeDrawer(GravityCompat.START);
            else
                binding.getRoot().openDrawer(GravityCompat.START);
        });

        binding.toolbar.refineIcon.setOnClickListener( v -> {
            startActivity(new Intent(MainActivity.this, RefineActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        binding.navView.setNavigationItemSelectedListener( item -> {
            Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
            binding.getRoot().closeDrawer(GravityCompat.START);
            return true;
        });

        binding.navView.getHeaderView(0).findViewById(R.id.header_image_view)
                .setOnClickListener( v -> {
                    Toast.makeText(this, "Go to profile section",
                            Toast.LENGTH_SHORT).show();
                });

        binding.navView.getHeaderView(0).findViewById(R.id.header_settings)
                .setOnClickListener( v -> {
                    Toast.makeText(this, "Go to settings",
                            Toast.LENGTH_SHORT).show();
                });

        binding.navView.getHeaderView(0).findViewById(R.id.header_name)
                .setOnClickListener( v -> {
                    Toast.makeText(this, "Go to profile section",
                            Toast.LENGTH_SHORT).show();
                });

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { binding.viewPager.setCurrentItem(tab.getPosition()); }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tabLayout.getTabAt(position).select();
            }
        });
    }
}