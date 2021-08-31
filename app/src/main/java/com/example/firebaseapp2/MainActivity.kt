package com.example.firebaseapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var logoutButton: Button
    private lateinit var postButton: Button
    private lateinit var recyclerView : RecyclerView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logoutButton = findViewById(R.id.logoutButton)
        postButton = findViewById(R.id.mainPostButon)
        recyclerView = findViewById(R.id.mainrecyclerview)
        auth = Firebase.auth

        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
         postButton.setOnClickListener {
             val intent = Intent(this, CreatePostActivity::class.java)
             startActivity(intent)
             finish()
         }
    }
}