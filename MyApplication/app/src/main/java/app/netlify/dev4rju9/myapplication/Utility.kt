package app.netlify.dev4rju9.myapplication

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class Utility {

    companion object {

        public fun getUser () : FirebaseUser? {
            val auth = FirebaseAuth.getInstance()
            return auth.currentUser
        }

        public fun updateDocument (document: String, model: WorkshopModel) {
            val db = FirebaseFirestore.getInstance().collection("workshops")
            db.document(document).set(model)
        }

        public fun getCollection (): CollectionReference {
            return FirebaseFirestore.getInstance().collection("workshops")
        }

    }

}