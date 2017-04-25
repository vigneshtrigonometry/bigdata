/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Singleton;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Vignesh
 */
@ServerEndpoint("/listen")
@Singleton
public class SocketListener implements Serializable {

    private static final Long serialVersionUID = 1L;

    private static List<Session> sessions = new ArrayList(); // we don't anticipate a large number of dashboard users

    @OnOpen
    public void open(Session session) {
        System.out.println(">>> new session: " + session.getId());
        sessions.add(session);
    }

    @OnClose
    public void close(Session session) {
        System.out.println(">>>> session closed: " + session.getId());
        sessions.remove(session);
    }

    @OnMessage
    public void message(String msg, Session session) {
//        System.out.println(">>> message: " + msg);
//        System.out.println(">>> in thread");
//        final JsonObject message = Json.createObjectBuilder()
//                .add("message", msg)
//                .add("timestamp", (new Date()).toString())
//                .build();
//
//        for (Session s : session.getOpenSessions()) {
//            try {
//                System.out.println(">>> send to all" + message.toString());
//                s.getBasicRemote().sendText(message.toString());
//            } catch (IOException ex) {
//                try {
//                    s.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        System.out.println(">>> exiting message");
    }

    public void message(String msg) {
        System.out.println(">>> message: " + msg);
        System.out.println(">>> in thread");
        final JsonObject message = Json.createObjectBuilder()
                .add("message", msg)
                .add("timestamp", (new Date()).toString())
                .build();

        for (Session s : sessions) {
            try {
                System.out.println(">>> send to all" + message.toString());
                s.getBasicRemote().sendText(message.toString());
            } catch (IOException ex) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(">>> exiting message");
    }
}
