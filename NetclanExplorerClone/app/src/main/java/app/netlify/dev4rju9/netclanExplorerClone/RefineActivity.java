package app.netlify.dev4rju9.netclanExplorerClone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import app.netlify.dev4rju9.netclanExplorerClone.databinding.ActivityRefineBinding;

public class RefineActivity extends AppCompatActivity {

    private ActivityRefineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRefineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.refineToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.availability_array, R.layout.simple_list_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.refineSpinner.setAdapter(adapter);

        update_counter();

        binding.refineStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int count, int deleted, int added) {
                update_counter();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        
        binding.purpose1.setOnClickListener( v -> {
            if (binding.purpose1.getCurrentTextColor() == -1) {
                binding.purpose1.setTextColor(getResources().getColor(R.color.netclan));
                binding.purpose1.setBackgroundResource(R.drawable.round_stroked);
            } else {
                binding.purpose1.setTextColor(getResources().getColor(R.color.white));
                binding.purpose1.setBackgroundResource(R.drawable.round_filled);
            }

        });

        binding.purpose2.setOnClickListener( v -> {
            if (binding.purpose2.getCurrentTextColor() == -1) {
                binding.purpose2.setTextColor(getResources().getColor(R.color.netclan));
                binding.purpose2.setBackgroundResource(R.drawable.round_stroked);
            } else {
                binding.purpose2.setTextColor(getResources().getColor(R.color.white));
                binding.purpose2.setBackgroundResource(R.drawable.round_filled);
            }
        });

        binding.purpose3.setOnClickListener( v -> {
            
            if (binding.purpose3.getCurrentTextColor() == -1) {
                binding.purpose3.setTextColor(getResources().getColor(R.color.netclan));
                binding.purpose3.setBackgroundResource(R.drawable.round_stroked);
            } else {
                binding.purpose3.setTextColor(getResources().getColor(R.color.white));
                binding.purpose3.setBackgroundResource(R.drawable.round_filled);
            }

        });

        binding.purpose4.setOnClickListener( v -> {
            if (binding.purpose4.getCurrentTextColor() == -1) {
                binding.purpose4.setTextColor(getResources().getColor(R.color.netclan));
                binding.purpose4.setBackgroundResource(R.drawable.round_stroked);
            } else {
                binding.purpose4.setTextColor(getResources().getColor(R.color.white));
                binding.purpose4.setBackgroundResource(R.drawable.round_filled);
            }
        });

        binding.purpose5.setOnClickListener( v -> {
            if (binding.purpose5.getCurrentTextColor() == -1) {
                binding.purpose5.setTextColor(getResources().getColor(R.color.netclan));
                binding.purpose5.setBackgroundResource(R.drawable.round_stroked);
            } else {
                binding.purpose5.setTextColor(getResources().getColor(R.color.white));
                binding.purpose5.setBackgroundResource(R.drawable.round_filled);
            }
        });

        binding.purpose6.setOnClickListener( v -> {
            if (binding.purpose6.getCurrentTextColor() == -1) {
                binding.purpose6.setTextColor(getResources().getColor(R.color.netclan));
                binding.purpose6.setBackgroundResource(R.drawable.round_stroked);
            } else {
                binding.purpose6.setTextColor(getResources().getColor(R.color.white));
                binding.purpose6.setBackgroundResource(R.drawable.round_filled);
            }
        });

        binding.purpose7.setOnClickListener( v -> {
            if (binding.purpose7.getCurrentTextColor() == -1) {
                binding.purpose7.setTextColor(getResources().getColor(R.color.netclan));
                binding.purpose7.setBackgroundResource(R.drawable.round_stroked);
            } else {
                binding.purpose7.setTextColor(getResources().getColor(R.color.white));
                binding.purpose7.setBackgroundResource(R.drawable.round_filled);
            }
        });

        binding.purpose8.setOnClickListener( v -> {
            if (binding.purpose8.getCurrentTextColor() == -1) {
                binding.purpose8.setTextColor(getResources().getColor(R.color.netclan));
                binding.purpose8.setBackgroundResource(R.drawable.round_stroked);
            } else {
                binding.purpose8.setTextColor(getResources().getColor(R.color.white));
                binding.purpose8.setBackgroundResource(R.drawable.round_filled);
            }
        });

        binding.refineButton.setOnClickListener( v -> finish());

    }

    private void update_counter () {
        binding.refineCharacterCount.setText(String.valueOf(binding.refineStatus.getText().length()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) finish();

        return super.onOptionsItemSelected(item);
    }
}