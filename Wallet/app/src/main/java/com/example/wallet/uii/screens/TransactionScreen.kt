package com.example.wallet.uii.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wallet.model.CardStore
import com.example.wallet.model.TransactionModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(cardId: Int) {

    val transactions = CardStore.getTransactions(cardId)
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(
                            "Movimientos",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "${transactions.size} transacciones",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF6B7280)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF8F9FE)
    ) { padding ->

        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.ReceiptLong,
                        contentDescription = null,
                        tint = Color(0xFF6B7280),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No hay movimientos",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF6B7280),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        "Las transacciones aparecerán aquí",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF9CA3AF)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(transactions) { tx ->
                    TransactionItem(tx, currencyFormat)
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(tx: TransactionModel, currencyFormat: NumberFormat) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val (icon, iconColor, bgColor) = getTransactionStyle(tx.type)
                
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(bgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
                
                Column {
                    Text(
                        tx.type,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1A1A2E)
                    )
                    Text(
                        tx.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF6B7280)
                    )
                }
            }
            
            Text(
                currencyFormat.format(tx.amount),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (tx.type.startsWith("Pago", ignoreCase = true)) {
                    Color(0xFF6C63FF)
                } else if (tx.type.startsWith("Recarga", ignoreCase = true)) {
                    Color(0xFF10B981)
                } else if (tx.type.equals("Transferencia recibida", ignoreCase = true)) {
                    Color(0xFF10B981)
                } else if (tx.type.equals("Depósito", ignoreCase = true)) {
                    Color(0xFF10B981)
                } else {
                    Color(0xFF6C63FF)
                }
            )
        }
    }
}

private fun getTransactionStyle(type: String): Triple<androidx.compose.ui.graphics.vector.ImageVector, Color, Color> {
    return when {
        type.startsWith("Pago", ignoreCase = true) -> Triple(
            Icons.Default.Receipt,
            Color(0xFF6C63FF),
            Color(0xFF6C63FF).copy(alpha = 0.1f)
        )
        type.startsWith("Recarga", ignoreCase = true) -> Triple(
            Icons.Default.PhoneAndroid,
            Color(0xFF10B981),
            Color(0xFF10B981).copy(alpha = 0.1f)
        )
        type.equals("Depósito", ignoreCase = true) -> Triple(
            Icons.Default.AddCircle,
            Color(0xFF10B981),
            Color(0xFF10B981).copy(alpha = 0.1f)
        )
        type.equals("Transferencia enviada", ignoreCase = true) -> Triple(
            Icons.Default.ArrowUpward,
            Color(0xFFF59E0B),
            Color(0xFFF59E0B).copy(alpha = 0.1f)
        )
        type.equals("Transferencia recibida", ignoreCase = true) -> Triple(
            Icons.Default.ArrowDownward,
            Color(0xFF10B981),
            Color(0xFF10B981).copy(alpha = 0.1f)
        )
        else -> Triple(
            Icons.Default.ReceiptLong,
            Color(0xFF6B7280),
            Color(0xFF6B7280).copy(alpha = 0.1f)
        )
    }
}