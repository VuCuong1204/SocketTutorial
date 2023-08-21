package com.example.sockettoturial.screen.connect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sockettoturial.R
import com.example.sockettoturial.screen.login.LoginActivity
import com.example.sockettoturial.socket.IFlowResultState
import com.example.sockettoturial.socket.ISocket
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ConnectActivity : AppCompatActivity() {

    private lateinit var btnConnect: Button

    @Inject
    lateinit var socketIO: ISocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connect_activity)

        btnConnect = findViewById(R.id.btnConnectAction)

        btnConnect.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                socketIO.onConnect(object : IFlowResultState {
                    override fun onSuccess() {
                        runOnUiThread {
                            Toast.makeText(this@ConnectActivity, "connect success", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@ConnectActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onError() {
                        runOnUiThread {
                            Toast.makeText(this@ConnectActivity, "error", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onDisconnect() {
                        runOnUiThread {
                            Toast.makeText(this@ConnectActivity, "disconnect", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }
    }
}
