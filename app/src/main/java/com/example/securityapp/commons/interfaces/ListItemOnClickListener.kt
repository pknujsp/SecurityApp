package com.example.securityapp.commons.interfaces

fun interface ListItemOnClickListener<T> {
    fun onClicked(item: T, position: Int)
}