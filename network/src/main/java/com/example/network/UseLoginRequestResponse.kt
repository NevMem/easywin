package com.example.network

data class UseLoginRequestResponse(
    var type: String?,
    var error: String?,
    var payload: UserData?
)