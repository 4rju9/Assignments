package app.netlify.dev4rju9.myapplication.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import app.netlify.dev4rju9.myapplication.MainActivity
import app.netlify.dev4rju9.myapplication.Utility
import app.netlify.dev4rju9.myapplication.WorkshopModel
import app.netlify.dev4rju9.myapplication.authentication.LoginActivity
import app.netlify.dev4rju9.myapplication.databinding.SingleItemBinding
import com.google.firebase.firestore.DocumentSnapshot

class WorkshopAdapter (private var documents: MutableList<DocumentSnapshot?> = mutableListOf()) :
    RecyclerView.Adapter<WorkshopAdapter.ViewHolder>() {

    inner class ViewHolder (binding: SingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.title
        val applyButton: Button = binding.applyButton
        private val appliedButton: Button = binding.appliedButton

        fun changeApplyButton (isApplied: Boolean = false) {
            if (isApplied) {
                applyButton.visibility = View.GONE
                appliedButton.visibility = View.VISIBLE
            } else {
                applyButton.visibility = View.VISIBLE
                appliedButton.visibility = View.GONE
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SingleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val uid: String? = MainActivity.sharedPreferences.getString("uid", null)
        val document = documents[position]
        val model = document?.toObject(WorkshopModel::class.java)

        holder.title.text = model?.title
        if (uid != null && model?.users!!.contains(uid)) {
            holder.changeApplyButton(true)
        } else {
            holder.changeApplyButton()
        }

        holder.applyButton.setOnClickListener {
            if (!MainActivity.sharedPreferences.getBoolean("userLoggedIn", false)) {
                ContextCompat.startActivity(
                    holder.title.context,
                    Intent(holder.title.context, LoginActivity::class.java),
                    null
                )
                return@setOnClickListener
            }

            if (uid != null) {
                model!!.users!!.add(uid)
                Utility.updateDocument(document.id, model)
                Toast.makeText(holder.title.context, "Applied!", Toast.LENGTH_SHORT).show()
                holder.changeApplyButton(true)
            }
        }

    }

    override fun getItemCount(): Int {
        return documents.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDocuments (docs: MutableList<DocumentSnapshot?>) {
        documents.clear()
        documents.addAll(docs)
        notifyDataSetChanged()
    }

}