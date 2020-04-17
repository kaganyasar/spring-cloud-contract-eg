package spring.cloud.contract.consumer.service.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PersonConsumerStream {
    String INPUT = "person-in";

    @Input(INPUT)
    SubscribableChannel personInput();
}
