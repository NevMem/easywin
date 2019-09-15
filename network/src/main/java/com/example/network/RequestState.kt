package com.example.network

sealed class RequestState<T>
class PendingState<T>: RequestState<T>()
class ErrorState<T>(val error: String): RequestState<T>()
class SuccessState<T>(val payload: T): RequestState<T>()