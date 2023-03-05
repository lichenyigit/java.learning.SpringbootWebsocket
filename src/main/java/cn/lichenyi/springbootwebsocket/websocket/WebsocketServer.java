package cn.lichenyi.springbootwebsocket.websocket;


import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@ServerEndpoint(("/ws/{uid}"))
public class WebsocketServer {
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) throws IOException {
        log.info(uid + " 链接成功");
        //连接成功
        session.getBasicRemote().sendText("你好，欢迎【" + uid + "】链接websocket");
    }

    @OnClose
    public void onClose() {
        log.info("链接关闭");
        System.out.println(this + "链接关闭");
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("uid") String uid) throws IOException {
        session.getBasicRemote().sendText(uid + " 发送的消息：" + message);
        log.info(uid + " 发送的消息：" + message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        log.error("发生错误");
        throwable.printStackTrace();
    }
}
