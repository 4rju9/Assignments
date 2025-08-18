package app.netlify.dev4rju9.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import app.netlify.dev4rju9.myapplication.MainActivity
import app.netlify.dev4rju9.myapplication.R
import app.netlify.dev4rju9.myapplication.Utility
import app.netlify.dev4rju9.myapplication.adapters.WorkshopAdapter
import app.netlify.dev4rju9.myapplication.databinding.FragmentDashboardBinding
import app.netlify.dev4rju9.myapplication.databinding.FragmentWorkshopBinding
import com.google.firebase.firestore.DocumentSnapshot

class WorkshopFragment : Fragment() {

    private lateinit var binding: FragmentWorkshopBinding
    private lateinit var workshopList: MutableList<DocumentSnapshot?>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_workshop, container, false)
        binding = FragmentWorkshopBinding.bind(view)
        fetchWorkshops()
        return view
    }

    private fun setupRecyclerView () {

        binding.workshopRecyclerView.setHasFixedSize(true)
        binding.workshopRecyclerView.setItemViewCacheSize(10)
        binding.workshopRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.workshopRecyclerView.adapter = WorkshopAdapter(workshopList)

    }

    private fun fetchWorkshops () {
        workshopList = ArrayList<DocumentSnapshot?>()
        val result = Utility.getCollection()
        result.get().addOnCompleteListener { task ->

            if (!task.isSuccessful) return@addOnCompleteListener

            val documents = task.result.documents
            for (doc in documents) {
                if (doc != null && doc.exists()) {
                    workshopList.add(doc)
                }
            }
            setupRecyclerView()
        }
    }

}