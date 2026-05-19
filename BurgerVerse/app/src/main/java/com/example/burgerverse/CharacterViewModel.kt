package com.example.burgerverse

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {

    var characterList =
        mutableStateListOf<Character>()

    init {
        getCharacters()
    }

    private fun getCharacters() {

        viewModelScope.launch {

            try {

                val response =
                    RetrofitInstance
                        .api
                        .getCharacters()

                characterList.addAll(response)

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}