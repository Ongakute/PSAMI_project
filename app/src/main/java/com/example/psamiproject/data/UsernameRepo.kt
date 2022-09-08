package com.example.psamiproject.data

import com.google.firebase.firestore.FirebaseFirestore

object UsernameRepo {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun getUserName(
        userID: String,
        success: (String) -> Unit
    ) {
        val docRef = usernames().document(userID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    if(document.data != null)
                    {
                        success(document.data!!["username"].toString())
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

    fun setUserName(
        userID: String,
        username: String,
        success: (Int) -> Unit
    ){
        usernames().document(userID).set(Username(username)).addOnSuccessListener {
            success(1)
        }.addOnFailureListener {
            error(it.localizedMessage)
        }
    }


    private fun usernames() = db.collection("usernames")
}