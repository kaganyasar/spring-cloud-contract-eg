package spring.boot.websocket.server.controller;


import data.WebSocketResponseSimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import spring.boot.websocket.server.service.WebSocketServerMessageSenderServiceImpl;

import java.util.logging.Logger;

@Controller
public class WebSocketServerController {

    private final Logger logger = Logger.getLogger(WebSocketServerController.class.getName());

    @Autowired
    private WebSocketServerMessageSenderServiceImpl webSocketServerMessageSenderService;

    @MessageMapping("/registerEndpointName")
    @SendToUser("/topic1/examplePath")
    public WebSocketResponseSimpleMessage processMessageFromClient(String message) {

        WebSocketResponseSimpleMessage webSocketResponseSimpleMessage = null;
        webSocketServerMessageSenderService.sendWebSocketResponseTestMessage(message);
        logger.info("Received message: " + message);

        return webSocketResponseSimpleMessage;
    }

//    @MessageMapping("/registerEndpointName")
//    @SendToUser("/topic2/examplePath2")
//    public WebSocketSimpleMessage processMessageFromClient2(String message){
//        logger.info("Received message: "+ message);
//        return new WebSocketSimpleMessage("it is example message2",new Person(2L,"exName2","exSurname2"));
//    }
}
