package com.example.wallet.model

object UserStore {

    private val users = mutableMapOf<String, String>()

    init {
        // usuario por defecto
        users["admin"] = "1234"
    }

    fun register(user: String, pass: String): Boolean {
        return if (users.containsKey(user)) {
            false
        } else {
            users[user] = pass
            true
        }
    }

    fun login(user: String, pass: String): Boolean {
        return users[user] == pass
    }
}