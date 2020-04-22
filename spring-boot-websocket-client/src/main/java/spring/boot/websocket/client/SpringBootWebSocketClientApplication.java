package spring.boot.websocket.client;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import spring.boot.websocket.client.event.WebSocketClientEventHandler;

import java.util.Scanner;

@SpringBootApplication
public class SpringBootWebSocketClientApplication {
    public static void main(String[] args) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        String url = "ws://127.0.0.1:8080/registerEndpointName";
        StompSessionHandler sessionHandler = new WebSocketClientEventHandler();
        stompClient.connect(url, sessionHandler);

        new Scanner(System.in).nextLine();
    }
}
