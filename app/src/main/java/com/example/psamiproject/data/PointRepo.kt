package com.example.psamiproject.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


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
                    "username" to point.username,
                    "value" to sum
                )
                points().document(userID).set(result).addOnSuccessListener { success(sum.toString()) }
            }
            else
            {
                val result = hashMapOf(
                    "username" to point.username,
                    "value" to point.value
                )
                points().document(userID).set(result).addOnSuccessListener { success(point.value.toString()) }
            }
        }
    }

    fun getAllUsersPoint(
        success: (List<Point>) -> Unit
    ) {

        points().orderBy("value", Query.Direction.DESCENDING).get().addOnSuccessListener {
            var items : MutableList<Point> = mutableListOf()
            for(document in it.documents)
            {
                val test = document.data
                items.add(Point(test!!["username"] as String, (test["value"] as Long).toInt()))
            }
            success(items as List<Point>)
        }
    }

    private fun points() = db.collection("points")
}