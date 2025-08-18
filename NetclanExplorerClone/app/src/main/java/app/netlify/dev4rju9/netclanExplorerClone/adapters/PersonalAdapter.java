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
import app.netlify.dev4rju9.netclanExplorerClone.databinding.SingleItemFragmentBinding;
import app.netlify.dev4rju9.netclanExplorerClone.models.PersonalModel;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.PersonalHolder> {

    List<PersonalModel> data, original;

    public PersonalAdapter() {
        getDummyData();
    }

    @NonNull
    @Override
    public PersonalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonalHolder(SingleItemFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalHolder holder, int position) {

        holder.setData(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void getDummyData () {

        data = new ArrayList<>();
        data.add(new PersonalModel("Person One", "City One | Student",
                "1.6", "Coffee | Business | Friendship", "P1", 33));
        data.add(new PersonalModel("Person Two", "City Two | UI and Ux Designer",
                "3.0", "Coffee | Business | Friendship", "P2", 60));
        data.add(new PersonalModel("Person Three", "City Three | Digital Marketer",
                "2.2", "Coffee | Business | Friendship", "P3", 30));
        data.add(new PersonalModel("Person Four", "City Four | Android Application Developer",
                "4.5", "Coffee | Business | Friendship", "P4", 50));
        data.add(new PersonalModel("Person Five", "City Five | Data Scientist",
                "10.3", "Coffee | Business | Friendship", "P5", 70));

        original = data;
    }

    private void setData (List<PersonalModel> model) {
        Log.d("Watcher", data.size()+"\n"+original.size());
        this.data = model;
        notifyDataSetChanged();
    }

    public void filter(String text) {

        text = text.toLowerCase();

        ArrayList<PersonalModel> filteredlist = new ArrayList<>();


        for (PersonalModel item : original) {

            if (item.getName().toLowerCase().contains(text) || item.getCity().toLowerCase().contains(text)) {
                filteredlist.add(item);
            }
        }
        if (text.isEmpty() || text.equals("")) setData(original);
        else setData(filteredlist);

    }

    public class PersonalHolder extends RecyclerView.ViewHolder {

        TextView name, city, distance, purpose, image;
        ProgressBar progress;

        public PersonalHolder(SingleItemFragmentBinding binding) {
            super(binding.getRoot());
            name = binding.itemName;
            city = binding.itemCity;
            distance = binding.itemDistance;
            purpose = binding.itemPurpose;
            image = binding.image;
            progress = binding.itemProgressBar;
        }

        private void setData (PersonalModel model) {
            name.setText(model.getName());
            city.setText(model.getCity());
            String distanceText = "Within " + model.getDistance() + " KM";
            distance.setText(distanceText);
            purpose.setText(model.getPurpose());
            image.setText(model.getImage());
            progress.setProgress(model.getProgress());
        }

    }

}
