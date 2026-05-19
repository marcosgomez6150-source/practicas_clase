package com.example.burgerverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContent {

            MaterialTheme {

                CharacterScreen()
            }
        }
    }
}

@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel = viewModel()
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {

        items(viewModel.characterList) {

                character ->

            CharacterCard(character)
        }
    }
}

@Composable
fun CharacterCard(character: Character) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            AsyncImage(
                model = character.image,
                contentDescription = character.name,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),

                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = character.name,
                style = MaterialTheme
                    .typography
                    .headlineSmall,

                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Sexo: ${
                    character.gender
                        ?: "Desconocido"
                }"
            )

            Text(
                text = "Primer episodio: ${
                    character.firstEpisode
                        ?: "Desconocido"
                }"
            )
        }
    }
}