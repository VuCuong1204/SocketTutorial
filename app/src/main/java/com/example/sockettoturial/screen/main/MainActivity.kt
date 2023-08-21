package com.example.sockettoturial.screen.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sockettoturial.R
import com.example.sockettoturial.model.SharedPreferences
import com.example.sockettoturial.model.UserInfo
import com.example.sockettoturial.screen.connect.ConnectActivity
import com.example.sockettoturial.socket.ISocket
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var ivExit: ImageView
    private lateinit var rvUser: RecyclerView
    private lateinit var edtChat: EditText
    private lateinit var ivSend: ImageView

    private val adapter by lazy {
        ChatAdapter()
    }

    private val dataList = mutableListOf<UserInfo>()

    @Inject
    lateinit var socketIO: ISocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        ivExit = findViewById(R.id.ivMainExit)
        rvUser = findViewById(R.id.rvMainChat)
        edtChat = findViewById(R.id.edtMainContent)
        ivSend = findViewById(R.id.ivMainSend)

        setAdapter()

        edtChat.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isEmpty() == true) {
                    ivSend.setImageResource(R.drawable.send_disable)
                } else {
                    ivSend.setImageResource(R.drawable.send_enable)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
//                TODO("Not yet implemented")
            }
        })

        ivSend.setOnClickListener {
            val text = edtChat.text.toString().trim()

            if (text.isNotEmpty()) {
                val myObject = JSONObject()
                myObject.put("id", SharedPreferences.userInfo.id)
                myObject.put("name", SharedPreferences.userInfo.name)
                myObject.put("content", text)
                myObject.put("avatar", SharedPreferences.userInfo.avatar)

                socketIO.onSendMessage(myObject.toString())
                hideKeyboard()
                edtChat.setText("")
            }
        }

        socketIO.onReceiveMessage {
            runOnUiThread {
                dataList.add(it)
                adapter.notifyItemChanged(dataList.lastIndex)
                rvUser.smoothScrollToPosition(dataList.lastIndex)
            }
        }

        ivExit.setOnClickListener {
            socketIO.onDisconnect()
            val intent = Intent(this, ConnectActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAdapter() {
        adapter.dataList = dataList
        rvUser.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        socketIO.onDisconnect()
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
