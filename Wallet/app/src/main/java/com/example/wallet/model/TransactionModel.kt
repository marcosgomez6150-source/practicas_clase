package com.example.wallet.model

data class TransactionModel(
    val type: String, // Pago, Depósito
    val amount: Double,
    val date: String
)