package com.example.wallet.uii.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wallet.uii.components.*
import com.example.wallet.viewmodel.WalletViewModel
import com.example.wallet.ui.theme.Primary
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val vm = remember { WalletViewModel() }

    var showOptions by remember { mutableStateOf(false) }
    var showAddCardDialog by remember { mutableStateOf(false) }
    var showDepositDialog by remember { mutableStateOf(false) }
    var bank by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var initialBalance by remember { mutableStateOf("") }
    var depositAmount by remember { mutableStateOf("") }
    var selectedCardId by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showOptions = true },
                containerColor = Primary,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Opciones",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        containerColor = Color(0xFFF8F9FE)
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Primary, Color(0xFF3B82F6))
                        )
                    )
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Mi Wallet",
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Gestiona tus tarjetas",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBalanceWallet,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    val totalBalance = vm.cards.sumOf { it.balance }
                    BalanceSection(totalBalance = totalBalance)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Mis Tarjetas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A2E)
                )
                Text(
                    "${vm.cards.size} tarjetas",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(vm.cards, key = { it.id }) { card ->
                    CardItem(
                        card = card,
                        onClick = {
                            navController.navigate("detail/${card.id}")
                        },
                        onDelete = {
                            vm.deleteCard(card)
                        }
                    )
                }
            }
        }

        if (showOptions) {
            AlertDialog(
                onDismissRequest = { showOptions = false },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { showOptions = false }) {
                        Text("Cancelar")
                    }
                },
                title = {
                    Text(
                        "Opciones",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    showOptions = false
                                    showAddCardDialog = true
                                }
                                .background(Color(0xFF6C63FF).copy(alpha = 0.1f))
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.AddCard, contentDescription = null, tint = Color(0xFF6C63FF))
                            Column {
                                Text("Agregar tarjeta", fontWeight = FontWeight.Medium)
                                Text("Registrar una nueva tarjeta", style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    showOptions = false
                                    showDepositDialog = true
                                }
                                .background(Color(0xFF10B981).copy(alpha = 0.1f))
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.AddCircle, contentDescription = null, tint = Color(0xFF10B981))
                            Column {
                                Text("Depositar", fontWeight = FontWeight.Medium)
                                Text("Agregar saldo a una tarjeta", style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(24.dp),
                containerColor = Color.White
            )
        }

        if (showAddCardDialog) {
            AlertDialog(
                onDismissRequest = { showAddCardDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            val balance = initialBalance.toDoubleOrNull() ?: 0.0
                            if (bank.isNotBlank() && cardNumber.isNotBlank() && expiryDate.isNotBlank()) {
                                vm.addCard(bank, cardNumber, expiryDate, balance)
                                bank = ""
                                cardNumber = ""
                                expiryDate = ""
                                initialBalance = ""
                                showAddCardDialog = false
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text("Agregar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        bank = ""
                        cardNumber = ""
                        expiryDate = ""
                        initialBalance = ""
                        showAddCardDialog = false
                    }) {
                        Text("Cancelar")
                    }
                },
                title = { 
                    Text(
                        "Nueva tarjeta",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    ) 
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = bank,
                            onValueChange = { bank = it },
                            label = { Text("Banco") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AccountBalance,
                                    contentDescription = null,
                                    tint = Color(0xFF6B7280)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = cardNumber,
                            onValueChange = { 
                                val digits = it.filter { c -> c.isDigit() }.take(16)
                                cardNumber = digits.chunked(4).joinToString(" ")
                            },
                            label = { Text("Número de tarjeta") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CreditCard,
                                    contentDescription = null,
                                    tint = Color(0xFF6B7280)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                            )
                        )
                        OutlinedTextField(
                            value = expiryDate,
                            onValueChange = {
                                val clean = it.filter { c -> c.isDigit() }.take(4)
                                if (clean.length <= 2) {
                                    expiryDate = clean
                                } else {
                                    expiryDate = "${clean.take(2)}/${clean.drop(2)}"
                                }
                            },
                            label = { Text("Fecha de caducidad (MM/AA)") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = Color(0xFF6B7280)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = initialBalance,
                            onValueChange = { initialBalance = it.filter { c -> c.isDigit() || c == '.' } },
                            label = { Text("Saldo inicial") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AttachMoney,
                                    contentDescription = null,
                                    tint = Color(0xFF6B7280)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                            )
                        )
                    }
                },
                shape = RoundedCornerShape(24.dp),
                containerColor = Color.White
            )
        }

        if (showDepositDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDepositDialog = false
                    selectedCardId = null
                    depositAmount = ""
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val amount = depositAmount.toDoubleOrNull()
                            val card = selectedCardId?.let { vm.cards.find { c -> c.id == it } }
                            if (amount != null && amount > 0 && card != null) {
                                val idx = com.example.wallet.model.CardStore.cards.indexOf(card)
                                if (idx >= 0) {
                                    com.example.wallet.model.CardStore.cards[idx] = card.copy(balance = card.balance + amount)
                                }
                                com.example.wallet.model.CardStore.addTransaction(
                                    card.id,
                                    com.example.wallet.model.TransactionModel("Depósito", amount, "Hoy")
                                )
                                selectedCardId = null
                                depositAmount = ""
                                showDepositDialog = false
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text("Depositar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDepositDialog = false
                        selectedCardId = null
                        depositAmount = ""
                    }) {
                        Text("Cancelar")
                    }
                },
                title = {
                    Text(
                        "Depositar",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            "Selecciona la tarjeta:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6B7280)
                        )
                        vm.cards.forEach { card ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { selectedCardId = card.id }
                                    .background(
                                        if (selectedCardId == card.id) Primary.copy(alpha = 0.1f)
                                        else Color.Transparent
                                    )
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                RadioButton(
                                    selected = selectedCardId == card.id,
                                    onClick = { selectedCardId = card.id },
                                    colors = RadioButtonDefaults.colors(selectedColor = Primary)
                                )
                                Column {
                                    Text(card.bank, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                                    Text("•••• ${card.lastDigits} - Saldo: ${NumberFormat.getCurrencyInstance(Locale.US).format(card.balance)}",
                                        style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
                                }
                            }
                        }
                        if (selectedCardId != null) {
                            OutlinedTextField(
                                value = depositAmount,
                                onValueChange = { depositAmount = it.filter { c -> c.isDigit() || c == '.' } },
                                label = { Text("Cantidad a depositar") },
                                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null, tint = Color(0xFF6B7280)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                                )
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(24.dp),
                containerColor = Color.White
            )
        }
    }
}