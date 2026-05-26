package com.example.wallet.model

import androidx.compose.ui.graphics.Color

data class CardModel(
    val id: Int,
    val bank: String,
    val lastDigits: String,
    val cardNumber: String,
    val expiryDate: String,
    var balance: Double,
    val color: Color,
    var isBlocked: Boolean = false
)