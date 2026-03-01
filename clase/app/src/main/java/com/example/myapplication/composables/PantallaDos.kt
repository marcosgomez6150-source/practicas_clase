package com.example.myapplication.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun PantallaDos() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        HeaderProfileDos("MATHCLASS", "5° grado")

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Fecha limite muy cerca!",
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Aprendiendo",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LearningCard("cursos de verano", "Inscribete", Color(0xFFEA5086))

        Spacer(modifier = Modifier.height(16.dp))

        LearningCard("Pruebalo 7 dias", "Comienza ya", Color(0xFF8FEACA))
    }
}

@Composable
fun HeaderProfileDos(name: String, grade: String) {
    Column {
        Text(text = name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(text = grade, color = Color.Gray)
    }
}

@Composable
fun LearningCard(title: String, subtitle: String, backgroundColor: Color) {

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = subtitle,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {}) {
                Text("Comenzar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaDosPreview() {
    MyApplicationTheme {
        PantallaDos()
    }
}