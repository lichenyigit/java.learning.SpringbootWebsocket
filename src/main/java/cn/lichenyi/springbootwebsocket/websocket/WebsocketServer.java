package cn.lichenyi.springbootwebsocket.websocket;


import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@ServerEndpoint(("/ws/{uid}"))
public class WebsocketServer {
    static String str = null;
    static {
        try {
            str = readFile();
        } catch (Exception e) {
            log.error("读取文件出错");
            throw new RuntimeException(e);
        }

    }

    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) throws Exception {
        log.info(uid + " 链接成功");
        session.getBasicRemote().sendText(str);
    }

    @OnClose
    public void onClose() {
        log.info("链接关闭");
        System.out.println("connection is closed");
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("uid") String uid) throws Exception {
        log.info(uid + " 发送的消息：" + message);
        session.getBasicRemote().sendText("canGetContent");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("is error");
        log.error("发生错误");
        throwable.printStackTrace();
    }

    public static String readFile() throws Exception {
        StringBuilder  stringBuilder = new StringBuilder();
        FileInputStream inputStream = new FileInputStream(new File("D:\\download\\QQ\\1097704842\\FileRecv\\2.txt"));
        FileChannel fileChannel = inputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 以下代码也几乎是Buffer和Channle的标准读写操作。
        while (true) {
            buffer.clear();
            int result = fileChannel.read(buffer);
            buffer.flip();
            if (result == -1) {
                break;
            }
            //log.info(new String(buffer.array(), 0, result, "UTF-8"));
            //log.info("---");
            stringBuilder.append(new String(buffer.array(), 0, result, "UTF-8"));
        }
        inputStream.close();
        return stringBuilder.toString();
    }
}
