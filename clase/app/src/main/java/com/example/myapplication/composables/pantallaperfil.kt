package com.example.myapplication.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
@Composable
fun pantallaperfil(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.Center

        ){
            Image(
                painter = painterResource(id =R.mipmap.ic_launcher),
                contentDescription = "Imagen de Perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clip(CircleShape)

            )
        }

        Box(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Nombre: Marcos Cuvalles",
                style = MaterialTheme.typography.titleLarge

            )
        }
    }

}
@Preview
@Composable
fun pantallaperfilpreview(){
    pantallaperfil()
}