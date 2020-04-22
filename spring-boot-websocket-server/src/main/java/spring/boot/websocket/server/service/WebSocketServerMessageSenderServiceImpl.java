package spring.boot.websocket.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.Person;
import data.WebSocketRequestSimpleMessage;
import data.WebSocketResponseSimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServerMessageSenderServiceImpl implements WebSocketServerMessageSenderService{

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Override
    public void sendWebSocketResponseTestMessage(String message) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();

            WebSocketRequestSimpleMessage webSocketSimpleMessage = objectMapper.readValue(message, WebSocketRequestSimpleMessage.class);
            Person person = new Person(2L, "resName", "resSurname");
            WebSocketResponseSimpleMessage webSocketResponseSimpleMessage = new WebSocketResponseSimpleMessage("response message", person, webSocketSimpleMessage.getMessagedByPerson());

            simpMessagingTemplate.convertAndSend("/topic1/examplePath",webSocketResponseSimpleMessage);
        }catch (Exception ex){
            ex.printStackTrace();
            simpMessagingTemplate.convertAndSend("/topic1/examplePath","error occured");
        }
    }
}
