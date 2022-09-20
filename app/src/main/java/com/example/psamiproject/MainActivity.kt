package com.example.psamiproject

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.psamiproject.data.User
import com.example.psamiproject.data.UserRepo
import com.example.psamiproject.data.UsernameRepo
import com.example.psamiproject.history.ActivitiesHistory
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private val REQ_ONE_TAP = 2

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_main)

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("557834777803-5lemrnbs5tipv36rrejpcfq80l4aid4j.apps.googleusercontent.com")
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

        val tekst = findViewById<TextView>(R.id.tekst)
        findViewById<com.google.android.gms.common.SignInButton>(R.id.googleLoginButton)
            .setOnClickListener {
            tekst.text = "Zaloguj"
            startLoginByGoogle()
        }
    }

    private fun startLoginByGoogle() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null, 0, 0, 0, null
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("TS", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(this) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d("TS", e.localizedMessage)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with Firebase.
                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TS", "signInWithCredential:success")
                                        val user = auth.currentUser
                                        UserRepo.addUser(User(user!!.uid, user.email!!)) {
                                            UsernameRepo.getUserName(user.uid) {
                                                if(it == "-1")
                                                {
                                                    var username = "username"
                                                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                                                    builder.setTitle("Podaj nazwę użytkownika")

                                                    val input = EditText(this)
                                                    input.inputType = InputType.TYPE_CLASS_TEXT
                                                    builder.setView(input)
                                                    builder.setPositiveButton("OK"
                                                    ) { _, _ ->
                                                        username = input.text.toString()
                                                        UsernameRepo.setUserName(
                                                            user.uid,
                                                            username
                                                        ) {
                                                            val intent = Intent(this, ActivitiesHistory::class.java)
                                                            startActivity(intent)
                                                        }
                                                    }
                                                    builder.show()
                                                }
                                                else
                                                {
                                                    val intent = Intent(this, ActivitiesHistory::class.java)
                                                    startActivity(intent)
                                                }
                                            }
                                        }

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TS", "signInWithCredential:failure", task.exception)
                                    }
                                }
                        }
                        else -> {
                            // Shouldn't happen.
                            Log.d("TEST", "No ID token!")
                        }
                    }
                } catch (e: ApiException) { }
            }
        }
    }
}