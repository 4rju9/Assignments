package app.netlify.dev4rju9.myapplication

import androidx.fragment.app.FragmentTransaction
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import app.netlify.dev4rju9.myapplication.adapters.ViewPagerAdapter
import app.netlify.dev4rju9.myapplication.adapters.WorkshopAdapter
import app.netlify.dev4rju9.myapplication.databinding.ActivityMainBinding
import app.netlify.dev4rju9.myapplication.fragments.WorkshopFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dashboardList: MutableList<DocumentSnapshot?>

    companion object {
        lateinit var sharedPreferences: SharedPreferences
        lateinit var dashAdapter: WorkshopAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this@MainActivity.getSharedPreferences("User", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("userLoggedIn", false)) {

            dashAdapter = WorkshopAdapter()
            fetchWorkshops()
            setupViewPager()
            setupTabLayout()

            binding.logout.setOnClickListener {
                val auth = FirebaseAuth.getInstance()
                auth.currentUser.let {
                    auth.signOut()
                    sharedPreferences.edit()
                        .remove("userLoggedIn")
                        .remove("uid")
                        .apply()
                    recreate()
                }
            }
        } else {

            binding.logout.visibility = View.GONE
            binding.tabLayout.visibility = View.GONE
            binding.viewPager.visibility = View.GONE

            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, WorkshopFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }

    }

    private fun setupTabLayout () {

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                binding.viewPager.currentItem = position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    private fun setupViewPager () {

        val adapter = ViewPagerAdapter(this@MainActivity)
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)?.select()
                if (position == 0) fetchWorkshops()
            }
        })

    }

    private fun fetchWorkshops () {
        dashboardList = ArrayList()
        val result = Utility.getCollection()
        result.whereArrayContains("users", sharedPreferences.getString("uid", "")!!)
            .get().addOnCompleteListener { task ->

                if (!task.isSuccessful) return@addOnCompleteListener

                val documents = task.result.documents
                for (doc in documents) {
                    if (doc != null && doc.exists()) {
                        dashboardList.add(doc)
                    }
                }
                dashAdapter.setDocuments(dashboardList)
            }
    }

}