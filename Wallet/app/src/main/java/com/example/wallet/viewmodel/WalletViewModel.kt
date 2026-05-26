package com.example.wallet.viewmodel

import androidx.compose.runtime.*
import com.example.wallet.model.CardStore
import com.example.wallet.model.CardModel

class WalletViewModel {

    var cards = CardStore.cards

    fun addCard(bank: String, cardNumber: String, expiryDate: String, initialBalance: Double = 0.0) {
        val digits = cardNumber.filter { it.isDigit() }
        val last4 = if (digits.length >= 4) digits.takeLast(4) else digits
        val newCard = CardModel(
            id = CardStore.nextId(),
            bank = bank,
            lastDigits = last4,
            cardNumber = cardNumber,
            expiryDate = expiryDate,
            balance = initialBalance,
            color = listOf(
                androidx.compose.ui.graphics.Color(0xFF1565C0),
                androidx.compose.ui.graphics.Color(0xFFD32F2F),
                androidx.compose.ui.graphics.Color(0xFF8E24AA),
                androidx.compose.ui.graphics.Color(0xFF2E7D32),
                androidx.compose.ui.graphics.Color(0xFFF57C00),
                androidx.compose.ui.graphics.Color(0xFF00695C)
            ).random()
        )
        cards.add(newCard)
    }

    fun deleteCard(card: CardModel) {
        cards.remove(card)
    }
}