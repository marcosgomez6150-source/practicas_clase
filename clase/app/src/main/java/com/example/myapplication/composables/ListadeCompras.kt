package com.example.myapplication.composables


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.Data.Articulo
import com.example.myapplication.ui.theme.MyApplicationTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaCompras(){
    val articulos = remember {mutableListOf<Articulo>()}
    var mostrarDialogo by  remember {mutableStateOf(false)}
    var textoTemporal by remember {mutableStateOf("")}


    if(mostrarDialogo){
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Agregar articulo") },
            text = {
                TextField(
                    value = textoTemporal,
                    onValueChange = { textoTemporal =it},
                )
            },
            confirmButton = {
                Button(onClick = {
                    articulos.add(Articulo(nombre = textoTemporal))
                    mostrarDialogo = false
                    textoTemporal = ""
                }){
                    Text("Aceptar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mi lista de Compras") })

        },
        floatingActionButton = {
            FloatingActionButton(onClick = { mostrarDialogo =true}) {
                Icon(Icons.Default.Add, contentDescription = "Agregar articulo")

            }
        }
    ) {padding ->

        LazyColumn(modifier = Modifier.fillMaxSize(),contentPadding = padding) {
            items(articulos.size){articulo->
                TarjetaElemeto(articulos[articulo])
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ListaComprasPreview() {
    MyApplicationTheme {
        ListaCompras()
    }
}