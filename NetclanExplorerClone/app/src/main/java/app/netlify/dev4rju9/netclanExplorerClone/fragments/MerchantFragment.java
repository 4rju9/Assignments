package app.netlify.dev4rju9.netclanExplorerClone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import app.netlify.dev4rju9.netclanExplorerClone.ExpandableFloatingButton;
import app.netlify.dev4rju9.netclanExplorerClone.R;
import app.netlify.dev4rju9.netclanExplorerClone.databinding.FragmentMerchantBinding;

public class MerchantFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant, container, false);

        FragmentMerchantBinding binding = FragmentMerchantBinding.bind(view);

        ExpandableFloatingButton expandableFloatingButton = new ExpandableFloatingButton(requireContext(), binding.mFab);
        expandableFloatingButton.init();

        return view;
    }

}