package com.example.sockettoturial.screen.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sockettoturial.R
import com.example.sockettoturial.model.SharedPreferences
import com.example.sockettoturial.model.UserInfo
import com.example.sockettoturial.screen.main.MainActivity
import com.example.sockettoturial.socket.ISocket
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var edtId: EditText
    private lateinit var edtAvatar: EditText
    private lateinit var edtName: EditText
    private lateinit var btnLogin: Button

    @Inject
    lateinit var socketIO: ISocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        edtId = findViewById(R.id.edtLoginId)
        edtAvatar = findViewById(R.id.edtLoginAvatar)
        edtName = findViewById(R.id.edtLoginName)
        btnLogin = findViewById(R.id.btnLoginConfirm)

        btnLogin.setOnClickListener {

            socketIO.onLogin(edtName.text.toString().trim()) {
                runOnUiThread {
                    if (it.isNotEmpty()) {
                        SharedPreferences.userInfo = UserInfo(
                            id = edtId.text.toString().trim().toInt(),
                            name = edtName.text.toString().trim(),
                            avatar = edtAvatar.text.toString().trim()
                        )

                        Toast.makeText(this, it, Toast.LENGTH_LONG).show()

                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
            }
        }
    }
}
