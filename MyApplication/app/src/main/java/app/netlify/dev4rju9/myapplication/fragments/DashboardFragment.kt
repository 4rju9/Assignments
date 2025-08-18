package app.netlify.dev4rju9.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import app.netlify.dev4rju9.myapplication.MainActivity
import app.netlify.dev4rju9.myapplication.R
import app.netlify.dev4rju9.myapplication.Utility
import app.netlify.dev4rju9.myapplication.WorkshopModel
import app.netlify.dev4rju9.myapplication.adapters.WorkshopAdapter
import app.netlify.dev4rju9.myapplication.databinding.FragmentDashboardBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.getField


class DashboardFragment : Fragment() {

    private lateinit var dashboardList: MutableList<DocumentSnapshot?>
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        binding = FragmentDashboardBinding.bind(view)
        setupRecyclerView()
        return view
    }

    private fun setupRecyclerView () {
        binding.dashboardRecyclerView.setHasFixedSize(true)
        binding.dashboardRecyclerView.setItemViewCacheSize(10)
        binding.dashboardRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.dashboardRecyclerView.adapter = MainActivity.dashAdapter
    }

}