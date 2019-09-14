package com.example.network

sealed class RequestState
object PendingState: RequestState()
class ErrorState(val error: String): RequestState()
class SuccessState<T>(val payload: T): RequestState()