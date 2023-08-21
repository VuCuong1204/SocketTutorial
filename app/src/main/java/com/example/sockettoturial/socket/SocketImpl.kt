package com.example.sockettoturial.socket

import com.example.sockettoturial.model.UserInfo
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketImpl @Inject constructor() : ISocket {

    private val socketIO = getSocketIO()

    override fun getServerUrl() = "http://192.168.5.182:3000"

    override fun getSocketIO(): Socket {
        return IO.socket(getServerUrl())
    }

    override fun onConnect(listener: IFlowResultState) {
        socketIO.on(Socket.EVENT_CONNECT) {
            listener.onSuccess()
        }.on(Socket.EVENT_CONNECT_ERROR) {
            listener.onError()
        }.on(Socket.EVENT_DISCONNECT) {
            listener.onDisconnect()
        }
        socketIO.connect()
    }

    override fun onLogin(user: String, action: (String) -> Unit) {
        socketIO.emit(EventRequest.USER_LOGIN_REQUEST, user)
        socketIO.on(EventResponse.LOGIN_SUCCESS_RESPONSE) { args ->
            val message = args[0] as String
            action.invoke(message)
        }
    }

    override fun onSendMessage(content: String) {
        socketIO.emit(EventRequest.SEND_MESSAGE_REQUEST, content)
    }

    override fun onReceiveMessage(action: (UserInfo) -> Unit) {
        socketIO.on(EventResponse.RECEIVER_MESSAGE_RESPONSE) { args ->
            val userInfo = Gson().fromJson(args[0].toString(), UserInfo::class.java)
            action.invoke(userInfo)
        }
    }

    override fun onDisconnect() {
        socketIO.disconnect()
    }
}
