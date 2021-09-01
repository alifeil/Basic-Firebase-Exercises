package com.example.firebaseapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var logoutButton: Button
    private lateinit var postButton: Button
    private lateinit var recyclerView : RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logoutButton = findViewById(R.id.logoutButton)
        postButton = findViewById(R.id.mainPostButon)
        recyclerView = findViewById(R.id.mainrecyclerview)
        auth = Firebase.auth
        database = Firebase.database

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

    override fun onStart() {
        super.onStart()

    val reference = database.getReference("posts")
      val options = FirebaseRecyclerOptions.Builder<Post>()
         .setQuery(reference, Post::class.java)
         .build()
        val adapter = object : FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.post_layout, parent, false)
                return PostViewHolder(view)
            }

            override fun onBindViewHolder(holder: PostViewHolder, position:Int, model: Post) {
                holder.setPost(this@MainActivity,model, auth.currentUser!!.uid)

                val postKey = getRef(position).key!!

                holder.deleteButton.setOnClickListener {
                    val reference = database.getReference("posts").child(postKey)
                    reference.removeValue()
                }
            }
        }
        adapter.startListening()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}