package cn.lichenyi.springbootwebsocket.controller;

import cn.lichenyi.springbootwebsocket.websocket.WebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @GetMapping("/getContent")
    public String hello() throws Exception {
        String result = WebsocketServer.readFile();
        log.info(result);
        return result;
    }
}
