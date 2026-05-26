package com.example.wallet.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color

object CardStore {

    val cards = mutableStateListOf(
        CardModel(
            id = 1,
            bank = "BBVA",
            lastDigits = "1234",
            cardNumber = "4000 0000 0000 1234",
            expiryDate = "12/28",
            balance = 12500.0,
            color = Color(0xFF1565C0)
        ),
        CardModel(
            id = 2,
            bank = "Santander",
            lastDigits = "5678",
            cardNumber = "5000 0000 0000 5678",
            expiryDate = "08/26",
            balance = 8300.0,
            color = Color(0xFFD32F2F)
        ),
        CardModel(
            id = 3,
            bank = "Nu",
            lastDigits = "9012",
            cardNumber = "6000 0000 0000 9012",
            expiryDate = "03/27",
            balance = 5600.0,
            color = Color(0xFF8E24AA)
        )
    )

    val transactions = mutableMapOf<Int, MutableList<TransactionModel>>()

    fun nextId(): Int = (cards.maxOfOrNull { it.id } ?: 0) + 1

    fun getCardById(id: Int) = cards.find { it.id == id }

    fun addTransaction(cardId: Int, transaction: TransactionModel) {
        val list = transactions.getOrPut(cardId) { mutableListOf() }
        list.add(transaction)
    }

    fun getTransactions(cardId: Int): List<TransactionModel> {
        return transactions[cardId] ?: emptyList()
    }
}