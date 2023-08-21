package com.example.sockettoturial.socket

import com.example.sockettoturial.model.UserInfo
import io.socket.client.Socket
import org.json.JSONObject

interface ISocket {
    fun getServerUrl(): String
    fun getSocketIO(): Socket
    fun onConnect(listener: IFlowResultState)
    fun onLogin(user: String, action: (String) -> Unit)
    fun onSendMessage(content: String)
    fun onReceiveMessage(action: (UserInfo) -> Unit)
    fun onDisconnect()
}
