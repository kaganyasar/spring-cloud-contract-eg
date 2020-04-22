package spring.boot.websocket.client.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.Person;
import data.WebSocketRequestSimpleMessage;
import data.WebSocketResponseSimpleMessage;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.logging.Logger;

@Component
public class WebSocketClientEventHandler extends StompSessionHandlerAdapter {

    private final Logger logger = Logger.getLogger(WebSocketClientEventHandler.class.getName());


    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Person person = new Person(1L, "reqName", "reqSurname");
            WebSocketRequestSimpleMessage webSocketSimpleMessage = new WebSocketRequestSimpleMessage("send me something", person);


            String message = objectMapper.writeValueAsString(webSocketSimpleMessage);
            session.subscribe("/topic1/examplePath", this);
            session.send("/prefixExample/registerEndpointName", message);

            logger.info("New session: " + session.getSessionId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return WebSocketResponseSimpleMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        WebSocketResponseSimpleMessage webSocketResponseSimpleMessage = (WebSocketResponseSimpleMessage) payload;
        logger.info("Received: " + webSocketResponseSimpleMessage.getMessage()+"\n"+
                "Sender Id: "+ webSocketResponseSimpleMessage.getMessagedByPerson().getId()+"\n"+
                "Sender Name: "+ webSocketResponseSimpleMessage.getMessagedByPerson().getName()+"\n"+
                "Sender Surname: "+ webSocketResponseSimpleMessage.getMessagedByPerson().getSurname());
    }
}
