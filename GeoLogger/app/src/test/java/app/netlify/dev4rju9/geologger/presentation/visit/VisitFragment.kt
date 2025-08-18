package app.netlify.dev4rju9.geologger.presentation.visit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.netlify.dev4rju9.geologger.R
import app.netlify.dev4rju9.geologger.databinding.FragmentVisitBinding
import app.netlify.dev4rju9.geologger.presentation.adapter.GeofenceVisitAdapter
import app.netlify.dev4rju9.geologger.presentation.geofence.GeofenceViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VisitFragment : Fragment(R.layout.fragment_visit) {

    private lateinit var binding: FragmentVisitBinding
    private val geofenceViewModel: GeofenceViewModel by viewModels()
    private lateinit var adapter: GeofenceVisitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVisitBinding.inflate(inflater, container, false)

        geofenceViewModel.allGeofenceVisits.observe(viewLifecycleOwner, { visits ->
            if (visits.size > 0) {
                binding.recyclerView.isVisible = true
                binding.starterText.isVisible = false
                adapter = GeofenceVisitAdapter(visits)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = adapter
            } else {
                binding.recyclerView.isVisible = false
                binding.starterText.isVisible = true
            }
        })

        geofenceViewModel.getAllGeofenceVisits()
        return binding.root
    }
}
