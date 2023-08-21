package com.example.sockettoturial.socket

interface IFlowResultState {
    fun onSuccess()
    fun onError()
    fun onDisconnect()
}
