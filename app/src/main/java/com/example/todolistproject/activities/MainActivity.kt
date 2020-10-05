package com.example.todolistproject.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistproject.R
import com.example.todolistproject.adapter.UserListAdapter
import com.example.todolistproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    lateinit var databaseReference: DatabaseReference
    var mList: ArrayList<User> = ArrayList()
    var keysList: ArrayList<String> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        //gets an instance of our RTD
        //databaseReference = FirebaseDatabase.getInstance().getReference(User.COLLECTION_NAME)


        //gets an instance of our firebase users
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference((auth.currentUser?.uid.toString()))
        // checking to see if the user is logged in
        val user = auth.currentUser
        //if not we take them to the register screen first
        if (user == null) {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        //otherwise continue and show them the main activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        getData()

        logout_button.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        add_task.setOnClickListener {
            startActivity(Intent(this, InsertActivity::class.java))
        }
    }

    private fun getData(){
        var adapter = UserListAdapter(this@MainActivity, mList)
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mList.clear()
                keysList.clear()
                for(data in dataSnapshot.children){
                    var user = data.getValue(User::class.java)
                    var key = data.key
                    mList.add(user!!)
                    keysList.add(key!!)
                }
                users_RV.adapter = adapter
                users_RV.layoutManager = LinearLayoutManager(this@MainActivity)
                adapter.setData(mList, keysList)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}