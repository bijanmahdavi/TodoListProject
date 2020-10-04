package com.example.todolistproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.todolistproject.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        to_register_screen_text_button.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        button_login.setOnClickListener {
            if(edit_text_email_login != null && edit_text_password_login != null) {
                var email = edit_text_email_login.text.toString()
                var password = edit_text_password_login.text.toString()


                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?> {
                        override fun onComplete(task: Task<AuthResult?>) {
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val user: FirebaseUser?= auth.currentUser
                                Log.d("user", user?.email)
                                Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()

                                startActivity(Intent(applicationContext, MainActivity::class.java))

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
                            }

                            // ...
                        }
                    })
            }

        }
    }
}