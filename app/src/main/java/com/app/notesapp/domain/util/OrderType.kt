package com.app.notesapp.domain.util

sealed class OrderType {
    data object Ascending: OrderType()
    data object Descending: OrderType()

}