package com.example.network

interface RequestStateListener<T> {
    fun stateUpdated(state: T)
}