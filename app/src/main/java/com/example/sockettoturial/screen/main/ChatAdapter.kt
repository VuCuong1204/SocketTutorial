package com.example.sockettoturial.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sockettoturial.R
import com.example.sockettoturial.model.SharedPreferences
import com.example.sockettoturial.model.UserInfo

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val SEND_TYPE = 1
        const val RECEIVER_TYPE = 2
    }

    var dataList = listOf<UserInfo>()

    override fun getItemViewType(position: Int): Int {
        val item = dataList[position]
        return if (item.id == SharedPreferences.userInfo.id) {
            SEND_TYPE
        } else {
            RECEIVER_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == SEND_TYPE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.user_send_item, parent, false)
            MessageSendVH(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.user_receiver_item, parent, false)
            MessageReceiverVH(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        if (item.id == SharedPreferences.userInfo.id) {
            (holder as MessageSendVH).onBind(item)
        } else {
            (holder as MessageReceiverVH).onBind(item)
        }
    }

    override fun getItemCount(): Int = dataList.size


    inner class MessageReceiverVH(private val view: View) : RecyclerView.ViewHolder(view) {

        private var ivAvatar: ImageView = view.findViewById(R.id.ivUserReceiverAvatar)
        private var tvName: TextView = view.findViewById(R.id.tvUserReceiverName)
        private var tvContent: TextView = view.findViewById(R.id.tvUserReceiverContent)

        fun onBind(data: UserInfo) {
            if (data.avatar?.isNotEmpty() == true) {
                Glide.with(view.context).load(data.avatar).into(ivAvatar)
            }

            tvName.text = data.name
            tvContent.text = data.content
        }
    }

    inner class MessageSendVH(private val view: View) : RecyclerView.ViewHolder(view) {
        private var ivAvatar: ImageView = view.findViewById(R.id.ivUserSendAvatar)
        private var tvContent: TextView = view.findViewById(R.id.tvUserSendContent)

        fun onBind(data: UserInfo) {
            if (data.avatar?.isNotEmpty() == true) {
                Glide.with(view.context).load(data.avatar).into(ivAvatar)
            }

            tvContent.text = data.content
        }
    }
}
