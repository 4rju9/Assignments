package app.netlify.dev4rju9.orbolmagazine_clone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONObject;
import app.netlify.dev4rju9.orbolmagazine_clone.databinding.AllArticleSinleItemBinding;

public class AllArticlesAdapter extends RecyclerView.Adapter<AllArticlesAdapter.ViewHolder> {

    JSONArray list;
    Context context;

    public AllArticlesAdapter (Context context, JSONArray list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AllArticleSinleItemBinding binding = AllArticleSinleItemBinding
                .inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.setupView(list.getJSONObject(position));
            holder.readMore.
                    setOnClickListener( v -> SplashScreen.makeToast(context, "Clicked"));
        } catch (Exception ignore) {}
    }

    @Override
    public int getItemCount() {
        return list.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        AppCompatButton readMore;

        public ViewHolder(AllArticleSinleItemBinding binding) {
            super(binding.getRoot());
            imageView = binding.imageView;
            title = binding.titleView;
            readMore = binding.button;
        }

        public void setupView (JSONObject json) {
            try {
                title.setText(json.getString("title"));
                Glide.with(context)
                        .load(json.getString("url"))
                        .centerCrop()
                        .into(imageView);
            } catch (Exception ignore) {}
        }

    }

}
