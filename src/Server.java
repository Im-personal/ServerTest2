import netscape.javascript.JSObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server extends WebSocketServer {


    public Server(int port){
        super(new InetSocketAddress(port));
    }

    ArrayList<WebSocket> usersOnline = new ArrayList<>();

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("User is connected");
        usersOnline.add(webSocket);
        for(String s: chat){
            webSocket.send(s);
        }
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

        usersOnline.remove(webSocket);
        System.out.println("User is disconnected. Users online: "+usersOnline.size());
    }

    ArrayList<String> chat = new ArrayList<>();

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println(s);
        JSONObject json = new JSONObject(s);
        chat.add(s);
        for(WebSocket ws: usersOnline){
            ws.send(s);
        }

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {

    }




}


