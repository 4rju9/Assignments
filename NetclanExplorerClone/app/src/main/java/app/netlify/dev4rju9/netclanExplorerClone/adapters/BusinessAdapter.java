package app.netlify.dev4rju9.netclanExplorerClone.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import app.netlify.dev4rju9.netclanExplorerClone.databinding.SingleItemBusinessBinding;
import app.netlify.dev4rju9.netclanExplorerClone.models.BusinessModel;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.BusinessHolder> {

    List<BusinessModel> data, original;

    public BusinessAdapter() {
        getDummyData();
    }

    @NonNull
    @Override
    public BusinessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BusinessHolder(SingleItemBusinessBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessHolder holder, int position) {

        holder.setData(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void getDummyData () {

        data = new ArrayList<>();
        data.add(new BusinessModel("Person One", "City One, 8.5 KM",
                "Data Analyst | 0 year of experience", "P1", 30));
        data.add(new BusinessModel("Person Two", "City Two, 26.1 KM",
                "Operation Executive | 4 year of experience", "P2", 45));
        data.add(new BusinessModel("Person Three", "City Three, 33.7 KM",
                "Tutor | 7 year of experience", "P3", 20));
        data.add(new BusinessModel("Person Four", "City Four, 42.6 KM",
                "Student | 0 year of experience", "P4", 25));
        data.add(new BusinessModel("Person Five", "City Five, 62.1 KM",
                "MCA | 0 year of experience", "P5", 15));
        
        original = data;
        
    }

    private void setData (List<BusinessModel> model) {
        Log.d("Watcher", data.size()+"\n"+original.size());
        this.data = model;
        notifyDataSetChanged();
    }

    public void filter(String text) {

        text = text.toLowerCase();

        ArrayList<BusinessModel> filteredlist = new ArrayList<>();


        for (BusinessModel item : original) {

            if (item.getName().toLowerCase().contains(text) || item.getCity().toLowerCase()
                    .contains(text) || item.getProfession().toLowerCase().contains(text)) {
                filteredlist.add(item);
            }
        }
        if (text.isEmpty() || text.equals("")) setData(original);
        else setData(filteredlist);

    }

    public class BusinessHolder extends RecyclerView.ViewHolder {

        TextView name, city, profession, image;
        ProgressBar progress;

        public BusinessHolder(SingleItemBusinessBinding binding) {
            super(binding.getRoot());
            name = binding.item2Name;
            city = binding.item2City;
            profession = binding.item2Profession;
            image = binding.image;
            progress = binding.item2ProgressBar;
        }

        private void setData (BusinessModel model) {
            name.setText(model.getName());
            city.setText(model.getCity());
            profession.setText(model.getProfession());
            image.setText(model.getImage());
            progress.setProgress(model.getProgress());
        }

    }

}
