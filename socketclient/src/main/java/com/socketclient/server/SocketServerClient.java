package com.socketclient.server;


import android.support.annotation.NonNull;

import com.socketclient.SocketClient;
import com.socketclient.helper.SocketClientAddress;
import com.socketclient.helper.SocketConfigure;

import java.net.Socket;

/**
 * SocketServerClient
 * AndroidSocketClient <com.vilyever.socketclient.server>
 * Created by vilyever on 2016/3/23.
 * Feature:
 */
public class SocketServerClient extends SocketClient {
    final SocketServerClient self = this;


    /* Constructors */
    public SocketServerClient(@NonNull Socket socket, SocketConfigure configure) {
        super(new SocketClientAddress(socket.getLocalAddress().toString().substring(1), "" + socket.getLocalPort()));

        setRunningSocket(socket);
        getSocketConfigure().setCharsetName(configure.getCharsetName()).setAddress(getAddress()).setHeartBeatHelper(configure.getHeartBeatHelper()).setSocketPacketHelper(configure.getSocketPacketHelper());

        internalOnConnected();
    }

    /* Public Methods */


    /* Properties */


    /* Overrides */


    /* Delegates */
     
     
    /* Private Methods */
    
}