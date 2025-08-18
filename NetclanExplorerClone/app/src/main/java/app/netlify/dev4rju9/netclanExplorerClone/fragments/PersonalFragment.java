package app.netlify.dev4rju9.netclanExplorerClone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import app.netlify.dev4rju9.netclanExplorerClone.ExpandableFloatingButton;
import app.netlify.dev4rju9.netclanExplorerClone.R;
import app.netlify.dev4rju9.netclanExplorerClone.adapters.PersonalAdapter;
import app.netlify.dev4rju9.netclanExplorerClone.databinding.FragmentPersonalBinding;

public class PersonalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        FragmentPersonalBinding binding = FragmentPersonalBinding.bind(view);

        binding.personalRecyclerView.setHasFixedSize(true);
        binding.personalRecyclerView.setItemViewCacheSize(10);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        binding.personalRecyclerView.setLayoutManager(manager);
        PersonalAdapter adapter = new PersonalAdapter();
        binding.personalRecyclerView.setAdapter(adapter);

        binding.pSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.filter(binding.pSeach.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        ExpandableFloatingButton expandableFloatingButton = new ExpandableFloatingButton(requireContext(), binding.pFab);
        expandableFloatingButton.init();

        final int[] prevPos = {0};
        binding.personalRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pos = manager.findFirstCompletelyVisibleItemPosition();
                if (pos < prevPos[0] && binding.pFab.fabMain.isShown()) {
                    expandableFloatingButton.hide();
                } else if (pos > prevPos[0] && !binding.pFab.fabMain.isShown()) {
                    expandableFloatingButton.show();
                }
                prevPos[0] = pos;
            }
        });

        return view;
    }

}