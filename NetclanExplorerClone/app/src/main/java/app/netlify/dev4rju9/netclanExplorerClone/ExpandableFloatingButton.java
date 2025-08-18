package app.netlify.dev4rju9.netclanExplorerClone;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import app.netlify.dev4rju9.netclanExplorerClone.databinding.FloatingButtonsBinding;

public class ExpandableFloatingButton {

    private Context context;
    private FloatingButtonsBinding binding;
    private boolean isExpended = false;
    private Animation fromBottomFab, toBottomFab, rotateClockWise, rotateAntiClockWise;

    public ExpandableFloatingButton(Context context, FloatingButtonsBinding binding) {
        this.binding = binding;
        this.context = context;
        fromBottomFab = AnimationUtils.loadAnimation(context, R.anim.from_bottom_fab);
        toBottomFab = AnimationUtils.loadAnimation(context, R.anim.to_bottom_fab);
        rotateClockWise = AnimationUtils.loadAnimation(context, R.anim.rotate_clock_wise);
        rotateAntiClockWise = AnimationUtils.loadAnimation(context, R.anim.rotate_anti_clock_wise);
    }

    public void init () {

        binding.fabMain.setOnClickListener(v -> {
            if (!isExpended) shrink();
            else expand();
        });

    }

    private void shrink () {

        binding.bg.setVisibility(View.VISIBLE);

        binding.fabMain.startAnimation(rotateClockWise);
        binding.fabNotes.startAnimation(toBottomFab);
        binding.fabJobs.startAnimation(toBottomFab);
        binding.fabGroup.startAnimation(toBottomFab);
        binding.fabBCards.startAnimation(toBottomFab);
        binding.fabBuySellRent.startAnimation(toBottomFab);
        binding.fabMatrimony.startAnimation(toBottomFab);
        binding.fabDating.startAnimation(toBottomFab);
        binding.notesTv.startAnimation(toBottomFab);
        binding.jobsTv.startAnimation(toBottomFab);
        binding.groupTv.startAnimation(toBottomFab);
        binding.bCardsTv.startAnimation(toBottomFab);
        binding.buySellRentTv.startAnimation(toBottomFab);
        binding.matrimonyTv.startAnimation(toBottomFab);
        binding.datingTv.startAnimation(toBottomFab);
        isExpended = !isExpended;
    }

    private void expand () {

        binding.bg.setVisibility(View.GONE);

        binding.fabMain.startAnimation(rotateAntiClockWise);
        binding.fabNotes.startAnimation(fromBottomFab);
        binding.fabJobs.startAnimation(fromBottomFab);
        binding.fabGroup.startAnimation(fromBottomFab);
        binding.fabBCards.startAnimation(fromBottomFab);
        binding.fabBuySellRent.startAnimation(fromBottomFab);
        binding.fabMatrimony.startAnimation(fromBottomFab);
        binding.fabDating.startAnimation(fromBottomFab);
        binding.notesTv.startAnimation(fromBottomFab);
        binding.jobsTv.startAnimation(fromBottomFab);
        binding.groupTv.startAnimation(fromBottomFab);
        binding.bCardsTv.startAnimation(fromBottomFab);
        binding.buySellRentTv.startAnimation(fromBottomFab);
        binding.matrimonyTv.startAnimation(fromBottomFab);
        binding.datingTv.startAnimation(fromBottomFab);
        isExpended = !isExpended;
    }

    public void hide () {
        binding.fabMain.setVisibility(View.INVISIBLE);
        binding.fabMain.startAnimation(toBottomFab);
    }

    public void show () {
        binding.fabMain.startAnimation(fromBottomFab);
        binding.fabMain.setVisibility(View.VISIBLE);
    }
    
}
