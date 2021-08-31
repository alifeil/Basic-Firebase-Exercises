package com.example.firebaseapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer

class CreatePostActivity : AppCompatActivity() {
    private  lateinit var editText: EditText
    private lateinit var postButton: Button
    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var post : Post
    private var type = "text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        editText = findViewById(R.id.createPostEditText)
        postButton = findViewById(R.id.createPostButtonText)
        auth = Firebase.auth
        database = Firebase.database
        post = Post()

        postButton.setOnClickListener {
            val currentUser = auth.currentUser
            currentUser?.let {
                val uid = it.uid
                val today = Calendar.getInstance().time
                val formatter = SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss")
                val time = formatter.format(today)

                post.text = editText.text.toString()
                post.type = type
                post.time = time
                post.uid = uid
                post.postUrl = ""

                val reference = database.getReference("posts")
                val postId = reference.push().key!!
                reference.child(postId).setValue(post).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this,"Posted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}