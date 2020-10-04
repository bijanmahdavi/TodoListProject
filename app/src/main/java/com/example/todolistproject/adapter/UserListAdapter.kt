package com.example.todolistproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistproject.R
import com.example.todolistproject.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.user_list.view.*

class UserListAdapter(var mContext: Context, var mList: ArrayList<User>) : RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {

    lateinit var databaseReference: DatabaseReference
    lateinit var keysList: ArrayList<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.MyViewHolder {
        databaseReference = FirebaseDatabase.getInstance().getReference(User.COLLECTION_NAME)
        var view = LayoutInflater.from(mContext).inflate(R.layout.user_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListAdapter.MyViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(lst: ArrayList<User>, kLst: ArrayList<String>) {
        mList = lst
        keysList = kLst
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: User, position: Int) {
            Log.d("Data", data.toString())
            itemView.username_text_view.setText(data.name)
            itemView.email_text_view.setText(data.email)


            itemView.button_to_delete.setOnClickListener {
                var databaseReference = FirebaseDatabase.getInstance().getReference("users")

                Log.d("DBR", keysList[position])
                databaseReference.child(keysList[position]).setValue(null)
            }

            itemView.button_to_update.setOnClickListener {
                val user = User(itemView.username_text_view.text.toString(),itemView.email_text_view.text.toString())
                var databaseReference = FirebaseDatabase.getInstance().getReference("users")
                var item = databaseReference.child(keysList[position])
                item.setValue(user)
            }
        }
    }
}