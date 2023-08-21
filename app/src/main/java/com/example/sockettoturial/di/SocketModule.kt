package com.example.sockettoturial.di

import com.example.sockettoturial.socket.ISocket
import com.example.sockettoturial.socket.SocketImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SocketModule {

    @Binds
    abstract fun getSocket(socketImpl: SocketImpl): ISocket
}
