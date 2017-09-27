package com.bsu.vinfinit.accelerometerserver;

import android.util.Log;
import java.util.List;
import java.util.ArrayList;

import com.bsu.vinfinit.accelerometerserver.utils.Observer;
import com.koushikdutta.async.AsyncNetworkSocket;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncServerSocket;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.ListenCallback;

import static com.koushikdutta.async.AsyncServer.LOGTAG;


/**
 * Created by uladzimir on 9/25/17.
 */

public class SocketServer implements Observer {
    public static final String LOGTAG = SocketServer.class.getName();
    public static final int SERVER_PORT = 6000;

    private AsyncServer asyncServer;
    private List<AsyncNetworkSocket> asyncClients = new ArrayList<AsyncNetworkSocket>();

    public SocketServer() {
        asyncServer = new AsyncServer();
        asyncServer.listen(null, SERVER_PORT, listenCallback);
    }

    private ListenCallback listenCallback = new ListenCallback() {
        @Override
        public void onCompleted(Exception ex) {

        }

        @Override
        public void onAccepted(AsyncSocket socket) {
            final AsyncNetworkSocket asyncClient = (AsyncNetworkSocket) socket;
            asyncClient.setDataCallback(new DataCallback() {
                @Override
                public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                    Log.i(LOGTAG, "Data received: " + bb.readString());
                }
            });
            asyncClient.setClosedCallback(new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    asyncClients.remove(asyncClient);
                    Log.i(LOGTAG, "Client socket closed");
                }
            });
            asyncClients.add(asyncClient);
            Log.i(LOGTAG, "Client socket connected");
        }

        @Override
        public void onListening(AsyncServerSocket socket) {
            Log.i(LOGTAG, "Server listening on port " + socket.getLocalPort());
        }
    };

    @Override
    public void update(float x, float y, float z) {
        for (AsyncNetworkSocket socket : asyncClients)
            socket.write(new ByteBufferList(String.format("%f %f %f", x, y, z).getBytes()));
    }
}
