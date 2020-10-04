package com.example.todolistproject.model

data class User(
    var name: String? = null,
    var email: String? = null
){
    companion object{
        const val COLLECTION_NAME = "users"
    }
}