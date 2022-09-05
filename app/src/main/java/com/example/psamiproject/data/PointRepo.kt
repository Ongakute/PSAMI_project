package com.example.psamiproject.data

import com.google.firebase.firestore.FirebaseFirestore

object PointRepo {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun getUserPoints(
        userID: String,
        success: (String) -> Unit
    ) {
        val docRef = points().document(userID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    if(document.data != null)
                    {
                        success(document.data!!["value"].toString())
                    }
                    else {
                        success("-1")
                    }
                } else {
                    success("-1")
                }
            }
            .addOnFailureListener {
                success("-1")
            }
    }



    fun addUserPoint(
        point: Point, userID: String,
        success: (String) -> Unit
    ) {
        getUserPoints(userID) {
            if(it != "-1")
            {
                val sum = point.value + it.toInt()
                val result = hashMapOf(
                    "value" to sum
                )
                points().document(userID).set(result).addOnSuccessListener { success(sum.toString()) }
            }
            else
            {
                val result = hashMapOf(
                    "value" to point.value
                )
                points().document(userID).set(result).addOnSuccessListener { success(point.value.toString()) }
            }
        }
    }

    private fun points() = db.collection("points")
}