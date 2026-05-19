package com.example.burgerverse

import retrofit2.http.GET

interface ApiService {

    @GET("characters/?limit=20")
    suspend fun getCharacters():
            List<Character>
}