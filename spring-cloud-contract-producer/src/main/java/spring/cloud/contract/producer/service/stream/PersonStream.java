package spring.cloud.contract.producer.service.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PersonStream {
    String OUTPUT = "person-out";

    @Output(OUTPUT)
    MessageChannel outboundPerson();
}
