package com.example.myapplication.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun Contadorcafe(){
    var contador by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        Text("Te has tomado $contador cafes hoy")
        Button(onClick = { contador++}) {
            Text("Tomar Café")
        }
    }
}