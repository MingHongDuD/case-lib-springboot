package com.damon.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ... 20250702
 */
@Component
@RestController("/websocket")
@ServerEndpoint(value = "/websocket/{username}")
public class UserWebSocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserWebSocketServer.class);

    /**
     * 在线人数
     */
    private static int onlineCount = 0;

    /**
     * 在线用户的Map集合
     * KEY -> 用户名，VALUE -> Session对象
     */
    private static Map<String, Session> sessionMap = new HashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        // 在websocketMap新增上线用户
        sessionMap.put(username, session);
        // 在线人数增加
        UserWebSocketServer.onlineCount++;
        // 通知除了自己之外的所有人
        sendOnlineCount(session, "{'type':'onlineCount','onlineCount':" + UserWebSocketServer.onlineCount + ",username:'" + username + "'}");
    }

    /**
     * 通知除了自己之外的所有人
     */
    private void sendOnlineCount(Session session, String message) {
        for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
            try {
                if (entry.getValue() != session) {
                    entry.getValue().getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                LOGGER.error("[websocket] 异常: ", e);
            }
        }
    }

    /**
     * 服务器接收到客户端消息时调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        try {
            Map<String, Object> hashMap = new ObjectMapper().readValue(message, HashMap.class);

            //消息类型
            String type = (String) hashMap.get("type");

            //来源用户
            Map srcUser = (Map) hashMap.get("srcUser");

            //目标用户
            Map tarUser = (Map) hashMap.get("tarUser");

            if (srcUser.get("username").equals(tarUser.get("username"))) {
                groupChat(session, hashMap);
            } else {
                privateChat(session, tarUser, hashMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        /**
         * 连接关闭调用的方法
         */
        @OnClose
        public void onClose (Session session){
            // 下线用户名
            String logoutUserName = "";
            // 从WebSocketMap删除下线用户
            for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
                if (entry.getValue() == session) {
                    sessionMap.remove(entry.getKey());
                    logoutUserName = entry.getKey();
                    break;
                }
            }
            // 在线人数减少
            UserWebSocketServer.onlineCount--;

            // 通知除了自己之外所有人
            sendOnlineCount(session, "{'type':'onlineCount','onlineCount':" + UserWebSocketServer.onlineCount + ",username:'" + logoutUserName + "'}");
        }


    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}

