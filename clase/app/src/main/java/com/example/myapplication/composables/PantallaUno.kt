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
fun PantallaUno() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        HeaderProfile("MATHCLASS", "5° Grado")

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Bienvenidos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF4CAF50)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "Numeros",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Hola matematicos tiernos!",
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {}) {
                    Text("Comenzar")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Cursos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        CourseCard("Pruebalo 7 dias", "Comienza el 1ro de marzo", "$200")

        Spacer(modifier = Modifier.height(12.dp))

        CourseCard("Cursos de verano", "Comienza el 1ro de abril", "$1000")
    }
}

@Composable
fun HeaderProfile(name: String, grade: String) {
    Column {
        Text(text = name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(text = grade, color = Color.Blue)
    }
}

@Composable
fun CourseCard(title: String, subtitle: String, price: String) {

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = subtitle, color = Color.Gray)
            }

            Text(
                text = price,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PantallaUnoPreview() {
    MyApplicationTheme {
        PantallaUno()
    }
}