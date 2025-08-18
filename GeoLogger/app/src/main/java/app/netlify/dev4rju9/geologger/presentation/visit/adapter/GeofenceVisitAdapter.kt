package app.netlify.dev4rju9.geologger.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.netlify.dev4rju9.geologger.data.local.entity.GeofenceVisit
import app.netlify.dev4rju9.geologger.databinding.ItemGeofenceVisitBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GeofenceVisitAdapter(private val geofenceVisits: List<GeofenceVisit>) :
    RecyclerView.Adapter<GeofenceVisitAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGeofenceVisitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val geofenceVisit = geofenceVisits[position]
        holder.bind(geofenceVisit)
    }

    override fun getItemCount(): Int = geofenceVisits.size

    inner class ViewHolder(private val binding: ItemGeofenceVisitBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(geofenceVisit: GeofenceVisit) {
            binding.tvLocationName.text = geofenceVisit.locationName
            val entry = "Entry Time: ${formatCurrentTimeMillis(geofenceVisit.entryTime)}"
            val exit = "Exit Time: ${formatCurrentTimeMillis(geofenceVisit.exitTime)}"
            val duration = "Duration: ${formatDuration(geofenceVisit.duration)}"
            binding.tvEntryTime.text = entry
            binding.tvExitTime.text = exit
            binding.tvDuration.text = duration
        }
    }

    fun formatDuration(duration: Long): String {
        val seconds = (duration / 1000) % 60
        val minutes = (duration / (1000 * 60)) % 60
        val hours = (duration / (1000 * 60 * 60)) % 24

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun formatCurrentTimeMillis(currentTimeMillis: Long): String {
        val dateFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val date = Date(currentTimeMillis)
        return dateFormat.format(date)
    }

}
