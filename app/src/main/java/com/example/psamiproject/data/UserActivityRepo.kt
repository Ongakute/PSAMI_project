package com.example.psamiproject.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object UserActivityRepo {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun addUserActivity(
        activity: UserActivity, error: (Throwable) -> Unit = {
            Log.d("UserRepo", "addUser error $it")
        }, success: () -> Unit
    ) {
        val docRef = activities().document()

        docRef.set(activity.copy(id = docRef.id)).addOnCompleteListener {
            if (it.isSuccessful) {
                success()
            } else {
                error(it.exception!!)
            }
        }
    }

    fun getUserActivities(
        userId: String, error: (Throwable) -> Unit = {
            Log.d("UserRepo", "addUser error $it")
        }, success: (List<UserActivity>) -> Unit
    ) {
        activities()
            .whereEqualTo("userId", userId).addSnapshotListener { value, error ->
                if (error != null) {
                    error(error)
                } else {
                    val items = value!!.toObjects(UserActivity::class.java)
                    items.sortByDescending { it.date }
                    success(items)
                }
            }
    }

    private fun activities() = db.collection("activities")
}