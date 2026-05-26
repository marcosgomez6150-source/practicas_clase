package com.example.wallet.uii.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wallet.model.*
import com.example.wallet.ui.theme.Primary
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailScreen(cardId: Int, navController: NavController) {

    val card = CardStore.getCardById(cardId) ?: return

    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
    val scrollState = rememberScrollState()
    var showTransferDialog by remember { mutableStateOf(false) }
    var transferAmount by remember { mutableStateOf("") }
    var selectedDestinationId by remember { mutableStateOf<Int?>(null) }
    val otherCards = CardStore.cards.filter { it.id != cardId }

    var showPayDialog by remember { mutableStateOf(false) }
    var payType by remember { mutableStateOf("") }
    var selectedService by remember { mutableStateOf("") }
    var payAmount by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    val services = listOf("Luz", "Agua", "Internet", "Telefonía", "Gas")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(card.bank, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    if (card.isBlocked) {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color(0xFFF59E0B)
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

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clip(RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(card.color, card.color.copy(alpha = 0.7f))
                            )
                        )
                        .padding(28.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                card.bank,
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                Icons.Default.CreditCard,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.6f),
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Text(
                            "•••• ${card.lastDigits}",
                            color = Color.White.copy(alpha = 0.9f),
                            style = MaterialTheme.typography.bodyLarge,
                            letterSpacing = 3.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Column {
                            Text(
                                "Saldo disponible",
                                color = Color.White.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                currencyFormat.format(card.balance),
                                color = Color.White,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Operaciones",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A2E)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    
                    OperationButton(
                        icon = Icons.Default.Payment,
                        title = "Pagar",
                        description = "Servicios y recargas",
                        color = Color(0xFF6C63FF)
                    ) {
                        showPayDialog = true
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Color(0xFFE5E7EB)
                    )

                    OperationButton(
                        icon = Icons.Default.SwapHoriz,
                        title = "Transferencia",
                        description = "Enviar dinero a otra tarjeta",
                        color = Color(0xFFF59E0B)
                    ) {
                        showTransferDialog = true
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Color(0xFFE5E7EB)
                    )

                    OperationButton(
                        icon = if (card.isBlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                        title = if (card.isBlocked) "Desbloquear" else "Bloquear",
                        description = if (card.isBlocked) "Habilitar operaciones" else "Deshabilitar operaciones",
                        color = if (card.isBlocked) Color(0xFF10B981) else Color(0xFFF59E0B)
                    ) {
                        card.isBlocked = !card.isBlocked
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                onClick = {
                    navController.navigate("transactions/$cardId")
                },
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
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF6C63FF).copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ReceiptLong,
                                contentDescription = null,
                                tint = Color(0xFF6C63FF),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                "Ver movimientos",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF1A1A2E)
                            )
                            Text(
                                "Historial de transacciones",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF6B7280)
                            )
                        }
                    }
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Color(0xFF6B7280)
                    )
                }
            }
        }
    }

    if (showTransferDialog) {
        AlertDialog(
            onDismissRequest = {
                showTransferDialog = false
                transferAmount = ""
                selectedDestinationId = null
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = transferAmount.toDoubleOrNull()
                        val destCard = selectedDestinationId?.let { CardStore.getCardById(it) }
                        if (amount != null && amount > 0 && card.balance >= amount && destCard != null) {
                            card.balance -= amount
                            destCard.balance += amount
                            CardStore.addTransaction(
                                cardId,
                                TransactionModel("Transferencia enviada", amount, "Hoy")
                            )
                            CardStore.addTransaction(
                                destCard.id,
                                TransactionModel("Transferencia recibida", amount, "Hoy")
                            )
                            transferAmount = ""
                            selectedDestinationId = null
                            showTransferDialog = false
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("Transferir")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showTransferDialog = false
                    transferAmount = ""
                    selectedDestinationId = null
                }) {
                    Text("Cancelar")
                }
            },
            title = {
                Text(
                    "Transferencia",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        "Selecciona la tarjeta de destino:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280)
                    )

                    otherCards.forEach { destCard ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { selectedDestinationId = destCard.id }
                                .background(
                                    if (selectedDestinationId == destCard.id)
                                        Primary.copy(alpha = 0.1f)
                                    else Color.Transparent
                                )
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            RadioButton(
                                selected = selectedDestinationId == destCard.id,
                                onClick = { selectedDestinationId = destCard.id },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Primary
                                )
                            )
                            Column {
                                Text(
                                    destCard.bank,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF1A1A2E)
                                )
                                Text(
                                    "•••• ${destCard.lastDigits}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF6B7280)
                                )
                            }
                        }
                    }

                    if (selectedDestinationId != null) {
                        OutlinedTextField(
                            value = transferAmount,
                            onValueChange = { transferAmount = it.filter { c -> c.isDigit() || c == '.' } },
                            label = { Text("Cantidad a transferir") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.AttachMoney,
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

                        if (card.balance < (transferAmount.toDoubleOrNull() ?: 0.0)) {
                            Text(
                                "Saldo insuficiente",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    } else {
                        Text(
                            "Selecciona una tarjeta para continuar",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF9CA3AF)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color.White
        )
    }

    if (showPayDialog) {
        AlertDialog(
            onDismissRequest = {
                showPayDialog = false
                payType = ""
                selectedService = ""
                payAmount = ""
                companyName = ""
                phoneNumber = ""
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = payAmount.toDoubleOrNull()
                        val isValid = when (payType) {
                            "Servicio" -> amount != null && amount > 0 && selectedService.isNotBlank() && card.balance >= amount
                            "Recarga" -> amount != null && amount > 0 && companyName.isNotBlank() && phoneNumber.length >= 10 && card.balance >= amount
                            else -> false
                        }
                        if (isValid) {
                            val title = if (payType == "Servicio") "Pago $selectedService" else "Recarga $companyName"
                            card.balance -= amount!!
                            CardStore.addTransaction(cardId, TransactionModel(title, amount, "Hoy"))
                            showPayDialog = false
                            payType = ""
                            selectedService = ""
                            payAmount = ""
                            companyName = ""
                            phoneNumber = ""
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("Pagar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showPayDialog = false
                    payType = ""
                    selectedService = ""
                    payAmount = ""
                    companyName = ""
                    phoneNumber = ""
                }) {
                    Text("Cancelar")
                }
            },
            title = {
                Text(
                    "Realizar pago",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (payType == "") {
                        Text(
                            "Selecciona el tipo de pago:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6B7280)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { payType = "Servicio" }
                                .background(Color(0xFF6C63FF).copy(alpha = 0.1f))
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Home, contentDescription = null, tint = Color(0xFF6C63FF))
                            Text("Servicio", fontWeight = FontWeight.Medium)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { payType = "Recarga" }
                                .background(Color(0xFF10B981).copy(alpha = 0.1f))
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = Color(0xFF10B981))
                            Text("Recarga celular", fontWeight = FontWeight.Medium)
                        }
                    }

                    if (payType == "Servicio") {
                        Text(
                            "Selecciona el servicio:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6B7280)
                        )
                        services.forEach { service ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { selectedService = service }
                                    .background(
                                        if (selectedService == service) Primary.copy(alpha = 0.1f)
                                        else Color.Transparent
                                    )
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                RadioButton(
                                    selected = selectedService == service,
                                    onClick = { selectedService = service },
                                    colors = RadioButtonDefaults.colors(selectedColor = Primary)
                                )
                                Text(service, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                        if (selectedService.isNotBlank()) {
                            OutlinedTextField(
                                value = payAmount,
                                onValueChange = { payAmount = it.filter { c -> c.isDigit() || c == '.' } },
                                label = { Text("Monto a pagar") },
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

                    if (payType == "Recarga") {
                        OutlinedTextField(
                            value = companyName,
                            onValueChange = { companyName = it },
                            label = { Text("Compañía (Telcel, Movistar, AT&T...)") },
                            leadingIcon = { Icon(Icons.Default.Business, contentDescription = null, tint = Color(0xFF6B7280)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it.filter { c -> c.isDigit() }.take(10) },
                            label = { Text("Número de teléfono") },
                            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF6B7280)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone
                            )
                        )
                        OutlinedTextField(
                            value = payAmount,
                            onValueChange = { payAmount = it.filter { c -> c.isDigit() || c == '.' } },
                            label = { Text("Monto a recargar") },
                            leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null, tint = Color(0xFF6B7280)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                            )
                        )
                    }

                    val currentAmount = payAmount.toDoubleOrNull() ?: 0.0
                    if (currentAmount > 0 && card.balance < currentAmount) {
                        Text("Saldo insuficiente", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }
                }
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color.White
        )
    }
}

@Composable
private fun OperationButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1A1A2E)
                )
                Text(
                    description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280)
                )
            }
        }
        Icon(
            Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF6B7280)
        )
    }
}