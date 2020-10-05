package com.example.todolistproject.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistproject.R
import com.example.todolistproject.adapter.TaskListAdapter
import com.example.todolistproject.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    lateinit var databaseReference: DatabaseReference
    var mList: ArrayList<Task> = ArrayList()
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

    override fun onRestart() {
        getData()
        super.onRestart()
    }

    override fun onResume() {
        getData()
        super.onResume()
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
        var adapter = TaskListAdapter(this@MainActivity, mList)
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mList.clear()
                keysList.clear()
                for(data in dataSnapshot.children){
                    var user = data.getValue(Task::class.java)
                    var key = data.key
                    mList.add(user!!)
                    keysList.add(key!!)
                }
                users_RV.adapter = adapter
                users_RV.layoutManager = LinearLayoutManager(this@MainActivity)
                adapter.setData(mList, keysList)
                progress_bar_main.visibility = View.GONE
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}