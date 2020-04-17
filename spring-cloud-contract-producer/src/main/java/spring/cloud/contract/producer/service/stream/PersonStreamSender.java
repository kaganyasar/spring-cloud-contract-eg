package spring.cloud.contract.producer.service.stream;

import data.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class PersonStreamSender {

    @Autowired
    PersonStream personStream;

    public void sendPerson(final Person person) {
        MessageChannel messageChannel = personStream.outboundPerson();
        messageChannel.send(MessageBuilder
                .withPayload(person)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }
}
