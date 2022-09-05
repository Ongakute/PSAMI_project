package com.example.psamiproject.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object UserRepo {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun addUser(
        user: User, error: (Throwable) -> Unit = {
            Log.d("UserRepo", "addUser error $it")
        }, success: () -> Unit
    ) {
        users().document(user.id).set(user).addOnCompleteListener {
            if (it.isSuccessful) {
                success()
            } else {
                error(it.exception!!)
            }
        }
    }

    fun userEmail() = FirebaseAuth.getInstance().currentUser!!.email

    fun userId() = FirebaseAuth.getInstance().currentUser!!.uid

    private fun users() = db.collection("users")
}