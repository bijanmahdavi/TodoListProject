package com.example.todolistproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolistproject.R
import com.example.todolistproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_insert.*

class InsertActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        init()
    }

    private fun init() {
        button_insert.setOnClickListener {
            var email = edit_text_email.text.toString()
            var name = edit_text_name.text.toString()
            var user = User(name, email)

//            var firebaseDatabase = FirebaseDatabase.getInstance()
//            var databaseReference = firebaseDatabase.getReference("users")

            var databaseReference = FirebaseDatabase.getInstance().getReference(auth.currentUser?.uid.toString())

            // insert blank record to generate unique id and save it in local varaible
            var userId = databaseReference.push().key

            databaseReference.child(userId!!).setValue(user)
            Toast.makeText(applicationContext, "inserted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))

        }
    }
}